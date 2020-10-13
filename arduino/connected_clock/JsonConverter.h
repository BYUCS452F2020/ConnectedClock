#ifndef JSONCONVERTER_H
#define JSONCONVERTER_H

#include <Arduino.h>
#include <ArduinoJson.h>
#include "Whereabout.h"
#include "Status.h"

class JsonConverter {
private:
  static const int JSON_BUFFER_SIZE = 300;
  
public:
  
  static Whereabout* JsonToWhereabouts(String input, unsigned char& whereaboutCount);
  static Status* JsonToStatuses(String input, unsigned char& statusCount);
  static String JsonToAuthToken(String input);
  static String GroupNameGroupPasswordToJson(String groupName, String groupPassword);
  static String AuthTokenToJson(String authToken);
};

#endif JSONCONVERTER_H
