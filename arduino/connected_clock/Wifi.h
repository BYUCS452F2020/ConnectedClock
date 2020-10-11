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
  static const int BUFFER_SIZE = 500;
  
  Wifi(int rxPin, int txPin, String wifiNetwork, String wifiPassword);
  char* SendNetworkRequest(String server, String requestType, String request, String requestBody);
};

#endif
