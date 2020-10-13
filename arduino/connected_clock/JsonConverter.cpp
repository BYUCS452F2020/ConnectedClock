#include "JsonConverter.h"

JsonObject JsonConverter::GetJsonObject(char* input) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  deserializeJson(doc, input);
  JsonObject jsonObject = doc.to<JsonObject>();
  return jsonObject;
}

Whereabout* JsonConverter::JsonToWhereabouts(char* input, unsigned char& whereaboutCount) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  whereaboutCount = jsonObject[F("whereabouts")].size();
  Whereabout* whereabouts = new Whereabout[whereaboutCount];
  for (unsigned char i = 0; i < whereaboutCount; i++) {
//    strcpy(whereabouts[i].UserID, jsonObject[F("whereabouts")][i][F("userID")]);
//    strcpy(whereabouts[i].Username, jsonObject[F("whereabouts")][i][F("username")]);
    whereabouts[i].ClockHandIndex = jsonObject[F("whereabouts")][i][F("clockHandIndex")];
    strcpy(whereabouts[i].CurrentStatusID, jsonObject[F("whereabouts")][i][F("currentStatusID")]);
  }
//  JsonConverter::doc.clear();

  return whereabouts;
}


Status* JsonConverter::JsonToStatuses(char* input, unsigned char& statusCount) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  statusCount = jsonObject[F("statuses")].size();
  Status* statuses = new Status[statusCount];
  for (unsigned char i = 0; i < statusCount; i++) {
    strcpy(statuses[i].StatusID, jsonObject[F("statuses")][i][F("statusID")]);
    statuses[i].ClockHandAngle = jsonObject[F("statuses")][i][F("clockHandAngle")];
  }
//  JsonConverter::doc.clear();

  return statuses;
}

String JsonConverter::JsonToAuthToken(char* input) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  String authToken = String((char*)jsonObject[F("authToken")]);
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
