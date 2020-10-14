#include "ClockHands.h"
#include <Servo.h>
#include <Arduino.h>

ClockHands::ClockHands(unsigned char handCount, unsigned char* pins) {
  Serial.println(F("Init Hands..."));
  this->servos = new Servo*[handCount];
  this->handCount = handCount;

  for (unsigned char i = 0; i < handCount; i++) {
    this->servos[i] = new Servo();
    Serial.print(F("Servo "));
    Serial.print(i);
    Serial.print(F(" pin "));
    Serial.print(pins[i]);
    this->servos[i]->attach(pins[i]);
    this->SetHandAngle(i, 0);
  }
}

int ClockHands::GetServoAngleFromHandAngle(int handAngle) {
  while (handAngle < 0) {
    handAngle = FULL_CIRCLE + handAngle;
  }
  
  handAngle = handAngle % FULL_CIRCLE;

  int servoAngle = handAngle * HAND_ANGLE_TO_SERVO_ANGLE;
  return servoAngle;
}

void ClockHands::SetHandAngle(unsigned char hand, int angle) {
  angle = 0;
  hand = 0;
  int servoAngle = this->GetServoAngleFromHandAngle(angle);
  this->servos[hand]->write(angle);
  Serial.print(F("Servo "));
  Serial.print((int)hand);
  Serial.print(F(" handAngle "));
  Serial.print((int)angle);
  Serial.print(F(" servoAngle "));
  Serial.println((int)servoAngle);
}
