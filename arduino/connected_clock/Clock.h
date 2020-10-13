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
  unsigned char statusCount;
  String authToken;
  const unsigned char RX_PIN = 2;
  const unsigned char TX_PIN = 3;
  static const unsigned char CLOCK_HAND_COUNT = 2;
  const unsigned char CLOCK_HAND_PINS[Clock::CLOCK_HAND_COUNT] = {10, 11};

  void InitServerConnection();
  int StatusIDToClockHandAngle(long statusID);
public:
  Clock();
  void Update();
};

#endif
