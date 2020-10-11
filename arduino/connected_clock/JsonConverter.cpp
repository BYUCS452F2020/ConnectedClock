#include "JsonConverter.h"

JsonObject JsonConverter::GetJsonObject(char* input) {
  StaticJsonDocument<JSON_BUFFER_SIZE> doc;
  deserializeJson(doc, input);
  JsonObject jsonObject = doc.to<JsonObject>();
  return jsonObject;
}

Whereabout* JsonConverter::JsonToWhereabouts(char* input, int& whereaboutCount) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  whereaboutCount = jsonObject["whereabouts"].size();
  Whereabout* whereabouts = new Whereabout[whereaboutCount];
  for (int i = 0; i < whereaboutCount; i++) {
    strcpy(whereabouts[i].UserID, jsonObject["whereabouts"][i]["userID"]);
    strcpy(whereabouts[i].Username, jsonObject["whereabouts"][i]["username"]);
    whereabouts[i].ClockHandIndex = jsonObject["whereabouts"][i]["clockHandIndex"];
    strcpy(whereabouts[i].CurrentStatusID, jsonObject["whereabouts"][i]["currentStatusID"]);
  }
//  JsonConverter::doc.clear();

  return whereabouts;
}


Status* JsonConverter::JsonToStatuses(char* input, int& statusCount) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  statusCount = jsonObject["statuses"].size();
  Status* statuses = new Status[statusCount];
  for (int i = 0; i < statusCount; i++) {
    strcpy(statuses[i].StatusID, jsonObject["statuses"][i]["statusID"]);
    statuses[i].ClockHandAngle = jsonObject["statuses"][i]["clockHandAngle"];
    strcpy(statuses[i].StatusName, jsonObject["statuses"][i]["statusName"]);
  }
//  JsonConverter::doc.clear();

  return statuses;
}

String JsonConverter::JsonToAuthToken(char* input) {
  JsonObject jsonObject = JsonConverter::GetJsonObject(input);
  char* authTokenArray = new char[36];
  strcpy(authTokenArray, jsonObject["authToken"]);
  String authToken(authTokenArray);
//  JsonConverter::doc.clear();
  return authToken;
}

String JsonConverter::GroupNameGroupPasswordToJson(String groupName, String groupPassword) {
  String loginJson = String("{ \"groupName\": \"") + groupName + String("\", \"groupPassword\": \"") + groupPassword + String("\" }");
  return loginJson;
}

String JsonConverter::AuthTokenToJson(String authToken) {
  String authTokenJson = String("{ \"authToken\": \"") + authToken + String("\" }");
  return authTokenJson;
}
