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


#include "Wifi.h"
#include "Arduino.h"

const long Wifi::ESP8266_BAUD_RATE = 9600;


Wifi::Wifi(int rxPin, int txPin, String wifiNetwork, String wifiPassword) {
  Serial.println("ESP8266 RX: pin " + String(rxPin) + " TX: pin " + String(txPin));
  this->esp8266 = new SoftwareSerial(rxPin, txPin);
  Serial.println("ESP8266 baud rate is " + String(Wifi::ESP8266_BAUD_RATE));
  this->esp8266->begin(Wifi::ESP8266_BAUD_RATE);

  this->Command("AT+RST\r\n", 2000); // Reset Wifi
  this->Command("AT+CWMODE=1\r\n",1000); // Set as Wifi Client
  this->Command("AT+CIPMUX=0\r\n",1000); // Only allow a single client at a time
  this->Command("AT+CWJAP=\"" + wifiNetwork + "\",\"" + wifiPassword + "\"\r\n", 6000); // Connect to Wifi Network
  this->Command("AT+CIFSR\r\n",2000); // get ip address // I DON'T THINK I NEED THIS...
}

void Wifi::Command(String command, const int timeout)
{
    //Serial.println(command.c_str());
    this->esp8266->print(command); // send the read character to the esp8266
    long int time = millis();

    while( (time+timeout) > millis())
    {
      while(this->esp8266->available())
      {
        // The esp has data so display its output to the serial window 
        Serial.write(this->esp8266->read());
      }  
    }
}

void Wifi::SendData(String server, String data) {
  int dataLength = data.length();
  this->Command("AT+CIPSTART=\"TCP\",\"" + server + "\",80\r\n", 1000);
  this->Command("AT+CIPSEND=" + String(dataLength + 9) + "\r\n", 500);
  this->Command(data + " HTTP/1.1\r\n", 500);

//
//  int readDataSize = this->esp8266->available();
//  if (readDataSize > 0) {
//    char* readData = new char[readDataSize];
//    for (int i = 0; i < readDataSize; i++) {
//      readData[i] = this->esp8266->read();
//    }
//
//    
//  } 
//
//  this->esp8266->find("+IPD,");
  this->Command("AT+CIPCLOSE", 1000);
}
