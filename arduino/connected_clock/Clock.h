#ifndef CLOCK_H
#define CLOCK_H

#include "Wifi.h"
#include "Whereabout.h"
#include "ClockHands.h"

class Clock {
private:
  Wifi* wifi;
  ClockHands* clockHands;
  unsigned char statusCount;
  String authToken;
  const unsigned char RX_PIN = 2;
  const unsigned char TX_PIN = 4;
  static const unsigned char CLOCK_HAND_COUNT = 2;
  const unsigned int DELAY_BETWEEN_MOVING_HANDS_MS = 3000;
  const unsigned char CLOCK_HAND_PINS[Clock::CLOCK_HAND_COUNT] = {5, 6};

  void InitServerConnection();
  int StatusIDToClockHandAngle(long statusID);
public:
  Clock();
  void Update();
};

#endif
