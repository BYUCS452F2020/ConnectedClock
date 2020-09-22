class Servo;

// https://www.arduino.cc/reference/en/libraries/servo/
// Servo wires:
// Red - 5V Power
// Black/Brown - Ground
// Yellow - Control (Digital pin). I'm using pins 10 and 11 
class ClockHands {
private:
  Servo** servos;
  unsigned int handCount;
  const int FULL_CIRCLE = 360;
  const float HAND_ANGLE_TO_SERVO_ANGLE = 0.5f;

public:
  ClockHands(unsigned int handCount, unsigned int* pins);

  // The values for a servo are in the range [0,180], but the values
  // for the clock are in the range [0, 360], where 0 and 360 point at 12o'clock.
  // This method converts from hand angle to servo angle.
  int GetServoAngleFromHandAngle(int servoAngle);

  // Moves a clock hand to the specified angle. Angle is in the 
  // range [0, 360] where 0 and 360 point at 12o'clock.
  // Hand is the index of which hand to move.
  void SetHandAngle(unsigned int hand, int angle);
};