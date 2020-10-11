#include "Clock.h"
#include "Secrets.h"
#include "JsonConverter.h"

Clock::Clock() {
  this->wifi = new Wifi(
    this->RX_PIN, 
    this->TX_PIN, 
    Secrets::GetWifiNetworkName(), 
    Secrets::GetWifiNetworkPassword());

  this->clockHands = new ClockHands(this->CLOCK_HAND_COUNT, this->CLOCK_HAND_PINS);
}

void Clock::InitServerConnection() {
//  String loginJson = JsonConverter::GroupNameGroupPasswordToJson(Secrets::GetClockGroupName(), Secrets::GetClockGroupPassword());
//  char* responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), String("POST"), String("/LoginGroup/"), loginJson);
//  this->authToken = JsonConverter::JsonToAuthToken(responseBody);
  this->authToken = Secrets::GetAuthToken();
//  delete responseBody;
  
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  char* responseBody2 = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), String("POST"), String("/GetStatuses/"), authTokenJson);
  this->statuses = JsonConverter::JsonToStatuses(responseBody2, this->statusCount);
  delete responseBody2;
}

void Clock::Update() {
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  char* responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), String("GET"), String("/GetWhereabouts/"), authTokenJson);
  int whereaboutCount = 0;
  Whereabout* whereabouts = JsonConverter::JsonToWhereabouts(responseBody, whereaboutCount);
  delete responseBody;

  for (int i = 0; i < whereaboutCount; i++) {
    double clockHandAngle = this->StatusIDToClockHandAngle(whereabouts[i].CurrentStatusID);
    this->clockHands->SetHandAngle(whereabouts[i].ClockHandIndex, clockHandAngle);
  }
}

double Clock::StatusIDToClockHandAngle(char* statusID) {
  for (int i = 0; i < this->statusCount; i++) {
    if (strcmp(this->statuses[i].StatusID, statusID) == 0) {
      return this->statuses[i].ClockHandAngle;
    }
  }
  return 0;
}
