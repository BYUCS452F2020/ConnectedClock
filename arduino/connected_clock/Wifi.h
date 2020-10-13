#ifndef WIFI_H
#define WIFI_H

#include <SoftwareSerial.h>
#include <WiFiEsp.h>

class Wifi {
private:
  static const long ESP8266_BAUD_RATE;
  SoftwareSerial* esp8266;
  WiFiEspClient client;
  
  char* ConvertStringToCharArray(String string, int* stringSize);
  void EnsureWifiShieldPresent();
  void ConnectToNetwork(String wifiNetwork, String wifiPassword);
  void SendRequest(char* ip, String requestType, String request, String requestBody);
  char* GetResponse();
  char* ExtractBodyFromResponse(char* response, int responseSize);
  
public: 
  static const unsigned int BUFFER_SIZE = 200;
  static const unsigned int PORT = 80;
  
  Wifi(unsigned char rxPin, unsigned char txPin, String wifiNetwork, String wifiPassword);
  char* SendNetworkRequest(String server, String requestType, String request, String requestBody);
};

#endif
