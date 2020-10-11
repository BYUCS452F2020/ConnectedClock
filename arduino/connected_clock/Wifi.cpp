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

const long Wifi::ESP8266_BAUD_RATE = 9600;


Wifi::Wifi(int rxPin, int txPin, String wifiNetwork, String wifiPassword) {
  Serial.println("ESP8266 RX: pin " + String(rxPin) + " TX: pin " + String(txPin));
  this->esp8266 = new SoftwareSerial(rxPin, txPin);
  Serial.println("ESP8266 baud rate is " + String(Wifi::ESP8266_BAUD_RATE));
  this->esp8266->begin(Wifi::ESP8266_BAUD_RATE);

  WiFi.init(this->esp8266);
  this->EnsureWifiShieldPresent();
  this->ConnectToNetwork(wifiNetwork, wifiPassword);
}

void Wifi::EnsureWifiShieldPresent() {
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue
    while (true);
  }  
}

void Wifi::ConnectToNetwork(String wifiNetwork, String wifiPassword) {
  char* ssid = this->ConvertStringToCharArray(wifiNetwork, nullptr);
  char* pass = this->ConvertStringToCharArray(wifiPassword, nullptr);

  int status = WL_IDLE_STATUS;
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(wifiNetwork);
    status = WiFi.begin(ssid, pass);
  }
  delete ssid;
  delete pass;
  Serial.println("You're connected to the network");
}

char* Wifi::ConvertStringToCharArray(String string, int* stringSize) {
  int sSize = string.length() + 1;
  if (stringSize != nullptr) {
    (*stringSize) = sSize;
  }
  char* charArray = new char[sSize];
  strcpy(charArray, string.c_str());
  return charArray;
}


char* Wifi::SendNetworkRequest(String server, String requestType, String request, String requestBody) {
  char* ip = this->ConvertStringToCharArray(server, nullptr);  
  char* response = nullptr;

  if (client.connect(ip, 80)) {
    this->SendRequest(ip, requestType, request, requestBody); 
    response = this->GetResponse();
  }
  
  delete ip;
  return response;
}

void Wifi::SendRequest(char* ip, String requestType, String request, String requestBody) {
    int requestSize;
    char* rqstBody = this->ConvertStringToCharArray(requestBody, requestSize);
    char* rqst = this->ConvertStringToCharArray(requestType + String(" ") + request + String(" HTTP/1.1"), nullptr);
    Serial.println("Connected to server");
    client.println(rqst);
    client.print("Host: ");
    client.println(ip);
    client.println("Connection: close");
    client.println("Content-Type: application/json");
    client.print("Content-Length: ");
    client.println(requestSize);
    client.println();
    client.println();
    client.println(requestBody);
    client.println();
    delete rqst;
    delete rqstBody;
}

char* Wifi::GetResponse()
{
  const int READ_SIZE = 32;
  char* readBuffer = new char[Wifi::BUFFER_SIZE];
  int responseSize = 0;
  while (client.connected()) {
    while(client.available() && responseSize + READ_SIZE < BUFFER_SIZE) {
      responseSize += client.read(readBuffer + responseSize, READ_SIZE);
      Serial.println("Read " + String(responseSize) + " bytes");
    }
  }
  client.stop();
  Serial.println();

  char* responseBody = this->ExtractBodyFromResponse(readBuffer, responseSize);
  delete readBuffer;
  return responseBody;
}

char* Wifi::ExtractBodyFromResponse(char* response, int responseSize) {
  char* body = nullptr;
  char* bodyStartPtr = strstr(&(response[0]), "\r\n\r\n");
  if (bodyStartPtr > 0) {
    int bodyStartIndex = (bodyStartPtr + 5) - &(response[0]);
    int bodySize = responseSize - bodyStartIndex;
    body = new char[bodySize + 1] {0};
    memcpy(body, bodyStartPtr, bodySize); 
  }
  Serial.write(body);
  return body;
}
