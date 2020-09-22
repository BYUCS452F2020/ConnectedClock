#include "ClockHands.h"
#include <Servo.h>
#include <Arduino.h>

ClockHands::ClockHands(unsigned int handCount, unsigned int* pins) {
  Serial.println("Initializing ClockHands...");
  this->servos = new Servo*[handCount];
  this->handCount = handCount;

  for (unsigned int i = 0; i < handCount; i++) {
    this->servos[i] = new Servo();
    this->servos[i]->attach(pins[i]);
    this->SetHandAngle(i, 0);
  }
}

int ClockHands::GetServoAngleFromHandAngle(int handAngle) {
  handAngle = handAngle % FULL_CIRCLE;
  if (handAngle < 0) {
    handAngle = FULL_CIRCLE + handAngle;
  }

  int servoAngle = handAngle * HAND_ANGLE_TO_SERVO_ANGLE;
  return servoAngle;
}

void ClockHands::SetHandAngle(unsigned int hand, int angle) {

  int servoAngle = this->GetServoAngleFromHandAngle(angle);
  this->servos[hand]->write(servoAngle);
  Serial.println("Moving servo " + String(hand) + " to handAngle " + String(angle) + ", servoAngle " + String(servoAngle));
}
