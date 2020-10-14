#include "JsonConverter.h"

//StaticJsonDocument<JsonConverter::JSON_BUFFER_SIZE> JsonConverter::GetJsonDoc(String input) {
//  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
//  Serial.println("inpt");
//  Serial.println(input);
//  DeserializationError error = deserializeJson(doc, input);
//  if (error) {
//    Serial.print(F("jsonparseerror "));
//    Serial.println(error.c_str());
//  }
//  return doc;
//}

Whereabout* JsonConverter::JsonToWhereabouts(String input, unsigned char& whereaboutCount) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  DeserializationError error = deserializeJson(doc, input);
  if (error) {
    Serial.print(F("jsonparseerror "));
    Serial.println(error.c_str());
  }
  JsonObject jsonObject = doc.as<JsonObject>();
//  StaticJsonDocument<JSON_BUFFER_SIZE> doc = JsonConverter::GetJsonDoc(input);

  JsonArray jsonWhereabouts = jsonObject[F("w")];
  whereaboutCount = jsonWhereabouts.size();
  Serial.print("cnt ");
  Serial.println(whereaboutCount);
  if (whereaboutCount > 0) {
   Whereabout* whereabouts = new Whereabout[whereaboutCount];
   int i = 0;
    for (JsonObject whereaboutJson : jsonWhereabouts) {
      Serial.println((long)whereaboutJson[F("sid")]);
      Serial.println((int)whereaboutJson[F("chi")]);
      whereabouts[i].CurrentStatusID = whereaboutJson[F("sid")];
      whereabouts[i].ClockHandIndex = whereaboutJson[F("chi")];
    }
    
    doc.clear();
    return whereabouts;
  }
  else
  {
    doc.clear();
    return nullptr;
  }
}


Status* JsonConverter::JsonToStatuses(String input, unsigned char& statusCount) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  Serial.println("inpt");
  Serial.println(input);
  DeserializationError error = deserializeJson(doc, input);
  if (error) {
    Serial.print(F("parseerror "));
    Serial.println(error.c_str());
  }
  JsonObject jsonObject = doc.as<JsonObject>();
  JsonArray jsonStatuses = jsonObject[F("s")];
  statusCount = jsonStatuses.size();

  if (statusCount > 0) {
    Status* statuses = new Status[statusCount];
    int i = 0;
    for (JsonObject statusJson : jsonStatuses) {
      statuses[i].StatusID = statusJson[F("sid")];
      statuses[i].ClockHandAngle = statusJson[F("cha")];
      i++;
    }
    doc.clear();
  
    return statuses;
  }
  else
  {
    doc.clear();
    return nullptr;
  }
}

String JsonConverter::JsonToAuthToken(String input) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  Serial.println("inpt");
  Serial.println(input);
  DeserializationError error = deserializeJson(doc, input);
  if (error) {
    Serial.print(F("parseerror "));
    Serial.println(error.c_str());
  }
  JsonObject jsonObject = doc.to<JsonObject>();

  
  String authToken = String((char*)jsonObject[F("authToken")]);
  doc.clear();
  return authToken;
}

String JsonConverter::GroupNameGroupPasswordToJson(String groupName, String groupPassword) {
  String loginJson = String(F("{ \"groupName\": \"")) + groupName + String(F("\", \"groupPassword\": \"")) + groupPassword + String(F("\" }"));
  return loginJson;
}

String JsonConverter::AuthTokenToJson(String authToken) {
  String authTokenJson = String(F("{ \"authToken\": \"")) + authToken + String(F("\" }"));
  return authTokenJson;
}
