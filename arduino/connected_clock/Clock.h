#ifndef CLOCK_H
#define CLOCK_H

#include "Wifi.h"
#include "Whereabout.h"
#include "Status.h"
#include "ClockHands.h"

class Clock {
private:
  Wifi* wifi;
  ClockHands* clockHands;
  Status* statuses;
  int statusCount;
  String authToken;
  const int RX_PIN = 2;
  const int TX_PIN = 3;
  static const int CLOCK_HAND_COUNT = 2;
  const int CLOCK_HAND_PINS[Clock::CLOCK_HAND_COUNT] = {10, 11};

  void InitServerConnection();
  Whereabout* ExtractWhereaboutsFromResponse(char* response, int& whereaboutCount);
  double StatusIDToClockHandAngle(char* statusID);
public:
  Clock();
  void Update();
};

#endif
