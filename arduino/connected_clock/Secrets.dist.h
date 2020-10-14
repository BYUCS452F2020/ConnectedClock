#ifndef SECRETS_H
#define SECRETS_H

// For your own use: 
// 1. Copy this file and rename it "WifiSecrets.h".
// 2. Fill in the values below.
class Secrets {
public:
  static String GetWifiNetworkName() { return "<YOUR_WIFI_SSID_HERE>"; }
  static String GetWifiNetworkPassword() { return "<YOUR_WIFI_PASSWORD_HERE>"; }
  static String GetServerIPAddress() { return "<YOUR_SERVER_IP_ADDRESS_HERE>"; }
  static String GetClockGroupName() { return "<YOUR_CLOCK_GROUP_NAME>"; }
  static String GetClockGroupPassword() { return "<YOUR_CLOCK_GROUP_PASSWORD>"; }
};


#endif
