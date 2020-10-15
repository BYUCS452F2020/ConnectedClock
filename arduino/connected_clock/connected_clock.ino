#include <Arduino.h>
#include <LowPower.h>
#include "Clock.h"

const int sleep8sCount = 1;
const int USB_BAUD_RATE = 9600;
Clock* clock;

//void setupSleep() {
//  pinMode(LED_BUILTIN,OUTPUT);//We use the led on pin 13 to indicate when Arduino is asleep
//  digitalWrite(LED_BUILTIN,HIGH);//turning LED on
//}

void setup() {
  Serial.begin(USB_BAUD_RATE);
  clock = new Clock();
  Serial.println(F("Pause to fix hands"));
  delay(5000);

  //setupSleep();
}

//void sleep() {
//  digitalWrite(LED_BUILTIN,LOW);//turning LED off
//  Serial.println("Sleeping...");
//  for (unsigned int i = 0; i < sleep8sCount; i++) {
//    //LowPower.powerDown(SLEEP_4S, ADC_OFF, BOD_OFF);
//  }
//  delay(2000);
//  Serial.println("Wake Up!");
//  digitalWrite(LED_BUILTIN,HIGH);//turning LED on
//}

void loop() {
  clock->Update();
  //sleep();
}
