#ifndef CLOCKHANDS_H
#define CLOCKHANDS_H

class Servo;

// https://www.arduino.cc/reference/en/libraries/servo/
// Servo wires:
// Red - 5V Power
// Black/Brown - Ground
// Yellow - Control (Digital pin). I'm using pins 10 and 11 
class ClockHands {
private:
  Servo* servos;
  unsigned char* pins;
  unsigned int* handAngles;
  unsigned char handCount;
  const int FULL_CIRCLE = 360;
  const int MAX_SERVO_VALUE = 165; // For some reason, the max value my servos accept is 165, but that is almost a 180 degree rotation???
  const float CLOCK_FACE_TO_SERVO_RATIO = (float)MAX_SERVO_VALUE / FULL_CIRCLE;
  const int TRANSITION_DELAY_MS = 50;
  const float TIME_FOR_FULL_CIRCLE_MS = 2000;//15000;
  const float ROTATION_SPEED_DEGREE_PER_UPDATE = (FULL_CIRCLE / TIME_FOR_FULL_CIRCLE_MS) * TRANSITION_DELAY_MS;
        //  360 degrees        30 millisec
        //  -----------  x    ------------   = 0.72 degrees per update
        //  15000 millisec       1 update



  // Our servos have a 1:2 gear ratio where 1 full rotation of the servo is 2 rotations of the clock hand.
  // Furthermore, our servos can only comfortably rotate between [0, 165] degrees.
  // This function takes those two points into account and maps between the angle on the clock face and
  // the amount the servo must rotate to achieve that clock hand angle.
  int ClockFaceAngleToServoAngle(int clockFaceAngle);

  // Directly moves a clock hand to the specified angle. Angle is in the 
  // range [0, 360] where 0 and 360 point at 12o'clock.
  // Hand is the index of which hand to move.
  void MoveHandToAngle(unsigned char handIndex, int handAngle);
  
public:
  ClockHands(unsigned char handCount, unsigned char* pins);

  // Smoothly moves the clock hand at handIndex from its current angle to handAngle.
  void TransitionHandTo(unsigned char handIndex, int handAngle);

  void DetachAll();
  void AttachAll();
};

#endif
