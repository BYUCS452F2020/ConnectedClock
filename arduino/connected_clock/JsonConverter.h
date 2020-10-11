#ifndef JSONCONVERTER_H
#define JSONCONVERTER_H

#include <Arduino.h>
#include <ArduinoJson.h>
#include "Whereabout.h"
#include "Status.h"

class JsonConverter {
private:
  static const int JSON_BUFFER_SIZE = 500;
  static JsonObject GetJsonObject(char* input);
  
public:
  
  static Whereabout* JsonToWhereabouts(char* response, int& whereaboutCount);
  static Status* JsonToStatuses(char* input, int& statusCount);
  static String JsonToAuthToken(char* input);
  static String GroupNameGroupPasswordToJson(String groupName, String groupPassword);
  static String AuthTokenToJson(String authToken);
};

#endif JSONCONVERTER_H
