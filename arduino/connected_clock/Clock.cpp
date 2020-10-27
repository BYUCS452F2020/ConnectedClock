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
  this->clockHands = new ClockHands(this->CLOCK_HAND_COUNT, this->CLOCK_HAND_PINS);
}

void Clock::InitServerConnection() {
  //  String loginJson = JsonConverter::GroupNameGroupPasswordToJson(Secrets::GetClockGroupName(), Secrets::GetClockGroupPassword());
  //  char* responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), String("POST"), String("/LoginGroup/"), loginJson);
  //  this->authToken = JsonConverter::JsonToAuthToken(responseBody);
  this->authToken = Secrets::GetAuthToken();
  //  delete responseBody;

  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  String statusBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), F("POST"), F("/GetSmallStatuses/"), authTokenJson);
  this->statuses = JsonConverter::JsonToStatuses(statusBody, this->statusCount);
}

void Clock::Update() {
  // We must detach then reattach the servos any time we use the network because for some reason using the ESP8266 interferes with PWM on the servo pins, causing them to go haywire.
  this->clockHands->DetachAll();
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  String responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), F("POST"), F("/GetSmallWhereabouts/"), authTokenJson);
  this->clockHands->AttachAll();

  unsigned char whereaboutCount = 0;
  Whereabout* whereabouts = JsonConverter::JsonToWhereabouts(responseBody, whereaboutCount);
  for (int i = 0; i < 2; i++) {
    Serial.println(whereabouts[i].ClockHandIndex);
  }

  for (unsigned char i = 0; i < whereaboutCount; i++) {
    int clockHandAngle = this->StatusIDToClockHandAngle(whereabouts[i].CurrentStatusID);
    this->clockHands->TransitionHandTo(whereabouts[i].ClockHandIndex, clockHandAngle);
    delay(DELAY_BETWEEN_MOVING_HANDS_MS);
  }
  delete whereabouts;

}

int Clock::StatusIDToClockHandAngle(long statusID) {
  for (unsigned char i = 0; i < this->statusCount; i++) {
    if (this->statuses[i].StatusID == statusID) {
      return this->statuses[i].ClockHandAngle;
    }
  }
  return 0;
}
