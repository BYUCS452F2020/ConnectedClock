#include "JsonConverter.h"

Whereabout* JsonConverter::JsonToWhereabouts(String input, unsigned char& whereaboutCount) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  DeserializationError error = deserializeJson(doc, input);
  if (error) {
    Serial.print(F("jsonparseerror "));
    Serial.println(error.c_str());
  }
  JsonObject jsonObject = doc.as<JsonObject>();

  JsonArray jsonWhereabouts = jsonObject[F("w")];
  whereaboutCount = jsonWhereabouts.size();
  if (whereaboutCount > 0) {
   Whereabout* whereabouts = new Whereabout[whereaboutCount];
   int i = 0;
    for (JsonObject whereaboutJson : jsonWhereabouts) {
      whereabouts[i].ClockHandAngle = whereaboutJson[F("cha")];
      whereabouts[i].ClockHandIndex = (unsigned char)whereaboutJson[F("chi")];
      Serial.println(whereabouts[i].ClockHandAngle);
      Serial.println(whereabouts[i].ClockHandIndex);
      i++;
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
