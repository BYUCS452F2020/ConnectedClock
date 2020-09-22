#include <SoftwareSerial.h>

class Wifi {
private:
  static const long ESP8266_BAUD_RATE;
  SoftwareSerial* esp8266;
public: 
  Wifi(int rxPin, int txPin, String wifiNetwork, String wifiPassword);
  void Command(String command, const int timeout);
  void SendData(String server, String data);
};
