#include "ClockHands.h"
#include <Servo.h>
#include <Arduino.h>

ClockHands::ClockHands(unsigned char handCount, unsigned char* pins) {
  Serial.println(F("Init Hands"));
  this->servos = new Servo[handCount];
  this->handCount = handCount;
  this->pins = pins;
  this->handAngles = new int[handCount] {0};

  this->AttachAll();
  for (unsigned char i = 0; i < handCount; i++) {
    this->MoveHandToAngle(i, 0);
  }
}

void ClockHands::AttachAll() {
  for (int i = 0; i < this->handCount; i++  ) {
    this->servos[i].attach(this->pins[i]);
  }
}

void ClockHands::DetachAll() {
  for (int i = 0; i < this->handCount; i++) {
    this->servos[i].detach();
  }
}

int ClockHands::ClockFaceAngleToServoAngle(int clockFaceAngle) {
  // Keep clockFaceAngle in range [0, 360)
  while (clockFaceAngle < 0) {
    clockFaceAngle += FULL_CIRCLE;
  }
  clockFaceAngle = clockFaceAngle % FULL_CIRCLE;

  int servoAngle = clockFaceAngle * CLOCK_FACE_TO_SERVO_RATIO;
  return servoAngle;
}

void ClockHands::MoveHandToAngle(unsigned char handIndex, int handAngle) {
  int servoAngle = this->ClockFaceAngleToServoAngle(handAngle);
  this->servos[handIndex].write(servoAngle);
  this->handAngles[handIndex] = handAngle;

  Serial.print(F("Servo "));
  Serial.print(handIndex);
  Serial.print(F(" to "));
  Serial.println(handAngle);
}

void ClockHands::TransitionHandTo(unsigned char handIndex, int handAngle) {
  
  int startHandAngle = this->handAngles[handIndex];
  int endHandAngle = handAngle;
  
  Serial.print(F("transition hand "));
  Serial.print(handIndex);
  Serial.print(F(" from "));
  Serial.print(startHandAngle);
  Serial.print(F(" to "));
  Serial.println(endHandAngle);

  int transitionDirection = 0;
  if (endHandAngle - startHandAngle > 0) {
    transitionDirection = 1;
  }
  else if (endHandAngle - startHandAngle < 0) {
    transitionDirection = -1;
  }

  int updateCount = ceil(abs(endHandAngle - startHandAngle) / ROTATION_SPEED_DEGREE_PER_UPDATE);
  for (int i = 0; i < updateCount; i++) {
    int currentHandAngle = startHandAngle + transitionDirection * ROTATION_SPEED_DEGREE_PER_UPDATE * i;
    this->MoveHandToAngle(handIndex, currentHandAngle);
    delay(TRANSITION_DELAY_MS);
  }
}
