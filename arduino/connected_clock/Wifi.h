#ifndef WIFI_H
#define WIFI_H

#include <SoftwareSerial.h>
#include <WiFiEsp.h>

class Wifi {
private:
  static const int ESP8266_BAUD_RATE;
  SoftwareSerial* esp8266;
  WiFiEspClient client;
  
  void EnsureWifiShieldPresent();
  void ConnectToNetwork(String wifiNetwork, String wifiPassword);
  void SendRequest(String host, String requestType, String request, String requestBody);
  char* GetResponse();
  int ReadContentLength();
  String ReadUntil(char minLength, String subString);
  void ReadUntilBody();

  bool WaitForResponse();
  
public: 
  static const unsigned int BUFFER_SIZE = 200;
  static const unsigned int PORT = 80;
  static const unsigned long WAIT_TIMEOUT_MS = 5000;
  static const unsigned long READ_TIMEOUT_MS = 5000;
  
  
  Wifi(unsigned char rxPin, unsigned char txPin, String wifiNetwork, String wifiPassword);
  char* SendNetworkRequest(String server, String requestType, String request, String requestBody);
};

#endif
