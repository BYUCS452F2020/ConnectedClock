// Connect ESP8266 to arduino using following configuration:
// See this link for diagram: https://www.tinkercad.com/things/0oQMS0nq33i-turn-on-led-with-esp8266-arduino
//                
// Arduino GND                  ->     ESP8266 GND
// Arduino 3.3V                 ->     ESP8266 3.3V
// Arduino 3.3V                 ->     ESP8266 EN
// Arduino TX/Arduino pin 3     ->     ESP8266 TX

// Arduino RX/Arduino pin 2     ->     1K resistor -> (*)        // (*) is a joint/node where 3 wires meet.
// Arduino GND                  ->     1K resistor -> (*)
// (*)                          ->     ESP8266 RX
//
//
// Steps to test ESP8266:
// 1. Connect it to Arduino using above configuration. Connect to Arduino TX/RX, not pin 8/9. TX/RX bypass the arduino, so we talk directly to ESP8266.
// 2. In Arduino IDE open BareMinimum new file and upload it to ESP8266.
// 3. Open Serial Monitor and change baud rate to 115200.
// 4. Send "AT+GMR" and look at result in monitor. It should be the firmware version of ESP8266. If garbage, try another baud rate.
//
//
// Hardware Serial accepts baud rate up to 115200, but SoftwareSerial cannot exceed 9600 due to arduino limitations.
// Once you are sure the ESP8266 is working, you must set it up to default to 9600 baud rate. To do that follow these steps:
// 1. Do steps 1-3 above.
// 2. Send "AT+UART_DEF=9600,8,1,0,0". This should default the baud rate of ESP8266 to 9600. Here is the explanation of command: AT+UART_DEF=<baudrate>, <databits>, <stopbits>, <parity>, <flow control>
// 3. Change serial monitor's baud rate to 9600.
// 4. Send "AT+GMR" and check result. If you see the firmware version, baud rate was changed successfully. 
//
// https://rootpower.com/?p=45
// https://arduino.stackexchange.com/questions/24156/how-to-change-baudrate-of-esp8266-12e-permanently
//
//
// Once the ESP8266 is set to default to 9600 baud rate, you can plug ESP8266 RX and TX into Arduino pins 8 and 9.
//
// ESP8266 AT command reference:
// https://room-15.github.io/blog/2015/03/26/esp8266-at-command-reference/
//
//
// Since we're using the WifiEsp library, it actually simplifies a lot of the wifi interaction. See the following link for more info:
// http://yaab-arduino.blogspot.com/p/wifiesp.html


#include "Wifi.h"
#include "Arduino.h"

const int Wifi::ESP8266_BAUD_RATE = 9600;


Wifi::Wifi(unsigned char rxPin, unsigned char txPin, String wifiNetwork, String wifiPassword) {
  Serial.print(F("RX "));
  Serial.print(rxPin);
  Serial.print(F(" TX "));
  Serial.println(txPin);
  this->esp8266 = new SoftwareSerial(rxPin, txPin);
  Serial.print(F("ESP Baud "));
  Serial.println(Wifi::ESP8266_BAUD_RATE);
  this->esp8266->begin(Wifi::ESP8266_BAUD_RATE);

  client.setTimeout(Wifi::READ_TIMEOUT_MS);
  WiFi.init(this->esp8266);
  this->EnsureWifiShieldPresent();
  this->ConnectToNetwork(wifiNetwork, wifiPassword);
}

void Wifi::EnsureWifiShieldPresent() {
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println(F("Wifi missing"));
    // don't continue
    while (true);
  }  
}

void Wifi::ConnectToNetwork(String wifiNetwork, String wifiPassword) {
  int status = WL_IDLE_STATUS;
  while ( status != WL_CONNECTED) {
    Serial.print(F("Connecting to Wifi "));
    Serial.println(wifiNetwork);
    status = WiFi.begin(wifiNetwork.c_str(), wifiPassword.c_str());
  }
  Serial.println(F("Wifi connected"));
}


char* Wifi::SendNetworkRequest(String server, String requestType, String request, String requestBody) {
  char* response = nullptr;
  if (client.connect(server.c_str(), Wifi::PORT)) {
    Serial.println(F("Connected to server"));
    this->SendRequest(server, requestType, request, requestBody); 
    response = this->GetResponse();
  }
  else
  {

    Serial.println(F("Server connect failed"));
  }
  
  return response;
}

void Wifi::SendRequest(String host, String requestType, String request, String requestBody) {
    String httpRequest = 
            requestType + F(" ") + request + F(" HTTP/1.0") +
            F("\r\nHost: ") + host + 
            F("\r\nConnection: keep-alive") + 
            F("\r\nContent-Type: application/json") + 
            F("\r\nContent-Length: ") + String(requestBody.length()) + 
            F("\r\n\r\n") + 
            requestBody + 
            F("\r\n");
    client.println(httpRequest);
    Serial.println(F("REQUEST"));
    Serial.println(httpRequest);
    Serial.println();
}

bool Wifi::WaitForResponse() {
  unsigned long start_time = millis();
  while (client.available() == 0) {
    if (millis() - start_time > Wifi::WAIT_TIMEOUT_MS) {
      return false;
    }
  }

  return true;
}

int Wifi::ReadContentLength() {
  String contentLengthLine = this->ReadUntil(16, F("Content-Length:"));
  if (contentLengthLine.length() > 0) {
    String contentLengthValue = contentLengthLine.substring(16);
    int contentLength = contentLengthValue.toInt();
    return contentLength;
  }
  return -1;
}

void Wifi::ReadUntilBody() {
  Serial.println(F("Reading till body"));
  String preBodyLine = this->ReadUntil(1, F("\r"));
}

String Wifi::ReadUntil(char minLength, String subString) {
  bool subStringFound = false;
  while (client.available() && !subStringFound) {
    String line = client.readStringUntil('\n');
    Serial.println(line);
    if (line.length() >= minLength && line.substring(0, subString.length()) == subString) {
      return line;
    }
  }
  return F("");
}

char* Wifi::GetResponse()
{
  bool didReceiveResponse = this->WaitForResponse();
  if (didReceiveResponse) {
    int contentLength = this->ReadContentLength();
    this->ReadUntilBody();
    if (contentLength > 0) {
      Serial.println(contentLength);
      char* bodyReadBuffer = new char[contentLength];
      Serial.println(F("Readbody"));
      for (unsigned int i = 0; i < contentLength; i++) {
        Serial.println(i  );
        bodyReadBuffer[i] = client.read();
      }
      Serial.println(F("Buffer"));
      Serial.println(bodyReadBuffer);
      String body = String(bodyReadBuffer);
      Serial.println(body);
      delete bodyReadBuffer;
    }
  }
  else {
    Serial.println(F("Response Timeout!"));
  }

  client.stop();
  return nullptr;
}
