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
}

void Clock::Update() {
  // We must detach then reattach the servos any time we use the network because for some reason using the ESP8266 interferes with PWM on the servo pins, causing them to go haywire.
  this->clockHands->DetachAll();
  String authTokenJson = JsonConverter::AuthTokenToJson(this->authToken);
  String responseBody = this->wifi->SendNetworkRequest(Secrets::GetServerIPAddress(), F("POST"), F("/GetSmallWhereabouts/"), authTokenJson);
  this->clockHands->AttachAll();

  unsigned char whereaboutCount = 0;
  Whereabout* whereabouts = JsonConverter::JsonToWhereabouts(responseBody, whereaboutCount);

  for (unsigned char i = 0; i < whereaboutCount; i++) {
    Serial.println(whereabouts[i].ClockHandIndex);
    this->clockHands->TransitionHandTo(whereabouts[i].ClockHandIndex, whereabouts[i].ClockHandAngle);
    delay(DELAY_BETWEEN_MOVING_HANDS_MS);
  }
  delete whereabouts;

}
