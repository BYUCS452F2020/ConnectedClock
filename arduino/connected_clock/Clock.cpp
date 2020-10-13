#include "Clock.h"
#include "Secrets.h"
#include "JsonConverter.h"

Clock::Clock() {
  Serial.println(F("Init Clock"));
  this->wifi = new Wifi(
    this->RX_PIN, 
    this->TX_PIN, 
    Secrets::GetWifiNetworkName(), 
    Secrets::GetWifiNetworkPassword());

    this->InitServerConnection();
//  this->clockHands = new ClockHands(this->CLOCK_HAND_COUNT, this->CLOCK_HAND_PINS);
}

void Clock::InitServerConnection() {
//  String loginJson = JsonConverter::GroupNameGroupPasswordToJson(Secrets::GetClockGroupName(), Secrets::GetClockGroupPassword());
//  char* responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), String("POST"), String("/LoginGroup/"), loginJson);
//  this->authToken = JsonConverter::JsonToAuthToken(responseBody);
  this->authToken = Secrets::GetAuthToken();
//  delete responseBody;
  
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  char* responseBody2 = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), F("POST"), F("/GetStatuses/"), authTokenJson);
  Serial.println(responseBody2);
  this->statuses = JsonConverter::JsonToStatuses(responseBody2, this->statusCount);
  delete responseBody2;
}

void Clock::Update() {
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  char* responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), F("POST"), F("/GetWhereabouts/"), authTokenJson);
  Serial.println(responseBody);
  unsigned char whereaboutCount = 0;
  Whereabout* whereabouts = JsonConverter::JsonToWhereabouts(responseBody, whereaboutCount);
  delete responseBody;

  for (unsigned char i = 0; i < whereaboutCount; i++) {
    float clockHandAngle = this->StatusIDToClockHandAngle(whereabouts[i].CurrentStatusID);
    //this->clockHands->SetHandAngle(whereabouts[i].ClockHandIndex, clockHandAngle);
    Serial.print(F("Servo "));
    Serial.print(String(whereabouts[i].ClockHandIndex));
    Serial.print(F(" to "));
    Serial.println(String(clockHandAngle));
  }
}

float Clock::StatusIDToClockHandAngle(char* statusID) {
  for (unsigned char i = 0; i < this->statusCount; i++) {
    if (strcmp(this->statuses[i].StatusID, statusID) == 0) {
      return this->statuses[i].ClockHandAngle;
    }
  }
  return 0;
}
