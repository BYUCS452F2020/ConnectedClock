#include <LowPower.h>
#include <WiFiEsp.h>
#include <SoftwareSerial.h>
#include <ArduinoJson.h>
#include "ClockHands.h"
#include "Secrets.h"

const int sleep8sCount = 1;

const int USB_BAUD_RATE = 9600;

int* handAngles;
int* directions;
ClockHands* clockHands;
const int handCount = 2;

WiFiEspClient client;
SoftwareSerial wifiSerial(2,3); //RX, TX



void setupInternet() {
  int status = WL_IDLE_STATUS;
  char ssid[Secrets::GetWifiNetworkName().length() + 1];
  char pass[Secrets::GetWifiNetworkPassword().length() + 1];
  strcpy(ssid, Secrets::GetWifiNetworkName().c_str());
  strcpy(pass, Secrets::GetWifiNetworkPassword().c_str());
  
  wifiSerial.begin(9600);
  // initialize ESP module
  WiFi.init(&wifiSerial);

  // check for the presence of the shield
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    // don't continue
    while (true);
  }

  // attempt to connect to WiFi network
  while ( status != WL_CONNECTED) {
    Serial.print("Attempting to connect to WPA SSID: ");
    Serial.println(ssid);
    // Connect to WPA/WPA2 network
    status = WiFi.begin(ssid, pass);
  }

  // you're connected now, so print out the data
  Serial.println("You're connected to the network");
}

void setupClockHands() {
  int clockHandPins[] = {10, 11};
  handAngles = new int[handCount];
  handAngles[0] = 0;
  handAngles[1] = 0;
  directions = new int[handCount];
  directions[0] = 1;
  directions[1] = 1;
  clockHands = new ClockHands(handCount, clockHandPins);
}

void setupSleep() {
  pinMode(LED_BUILTIN,OUTPUT);//We use the led on pin 13 to indicate when Arduino is a  sleep
  digitalWrite(LED_BUILTIN,HIGH);//turning LED on
}

void setup() {
  Serial.begin(USB_BAUD_RATE);
  Serial.println("Setting up ConnectedClock...");

  setupSleep();
  setupClockHands();
  //setupInternet();
}





void updateClockHands(int* handAngles) {
  for (unsigned int i = 0; i < handCount; i++) {
    clockHands->SetHandAngle(i, handAngles[i]);
  }
}

char* getJsonFromServer() {
  char ip[Secrets::GetServerIPAddress().length() + 1];
  strcpy(ip, Secrets::GetServerIPAddress().c_str());
  char* output = nullptr;
  
  if (client.connected()) {
    Serial.println("Client already connected! Oops!");
  }
  
  if (client.connect(ip, 80)) {
    Serial.println("Connected to server");
    // Send HTTP request
    client.println("GET /users/zones HTTP/1.1");
    client.print("Host: ");
    client.println(ip);
    client.println("Connection: close");
    client.println();

    char readBuffer[200];
    int size = 0;
    while (client.connected()) {
      while(client.available()) {
        size += client.read(readBuffer + size, 32);
        Serial.println("Read " + String(size) + " bytes");
      }
    }
    client.stop();
    Serial.println();

    char* jsonStartPtr = strstr(&(readBuffer[0]), "\r\n\r\n");
    if (jsonStartPtr > 0) {
      int jsonStartIndex = (jsonStartPtr + 5) - &(readBuffer[0]);
      int jsonSize = size - jsonStartIndex;
      output = new char[jsonSize + 1] {0};
      memcpy(output, jsonStartPtr, jsonSize); 
    }
//    char* finalBuffer = new char[size + 1] {0};
//    memcpy(finalBuffer, readBuffer, size);
    Serial.write(output);
  }  
  return output;
}

void checkServer(int* handAngles) {
  char* jsonString = getJsonFromServer();
  
//  const char* input = "[45, 135]";


  // https://arduinojson.org/v6/doc/deserialization/
  // https://randomnerdtutorials.com/decoding-and-encoding-json-with-arduino-or-esp8266/
  StaticJsonDocument<200> doc;
  DeserializationError err = deserializeJson(doc, jsonString);
  if (err) {
    Serial.print("deserializeJson() failed with code: ");
    Serial.println(err.c_str());
  }
  else {
    JsonArray clockHandAngles = doc.as<JsonArray>();
    int clockHandAnglesCount = clockHandAngles.size();
    for(unsigned int i = 0; i < handCount; i++) {
      if (i >= clockHandAnglesCount) {
        handAngles[i] = 0;
        Serial.println("No handAngle at index " + String(i) + " in JsonArray. Setting handAngle to 0");
      }
      else {
        handAngles[i] = clockHandAngles[i];
        Serial.println("Setting handAngle " + String(i) + " to JsonArray value " + String(handAngles[i]));
      }
    }
  }
}

void sleep() {
  digitalWrite(LED_BUILTIN,LOW);//turning LED off
  Serial.println("Sleeping...");
  for (unsigned int i = 0; i < sleep8sCount; i++) {
    //LowPower.powerDown(SLEEP_4S, ADC_OFF, BOD_OFF);
  }
  delay(2000);
  Serial.println("Wake Up!");
  digitalWrite(LED_BUILTIN,HIGH);//turning LED on
}

void loop() {
  //checkServer(handAngles);
  updateClockHands(handAngles);
  handAngles[0] += 5 * directions[0];
  handAngles[1] += 7 * directions[1];
  for (int i = 0; i < handCount; i++)
  {
    if (handAngles[i] > 350)
    {
      directions[i] = -1;
    }
    else if (handAngles[i] < 10)
    {
      directions[i] = 1;
    }
  }
  //sleep();
}
