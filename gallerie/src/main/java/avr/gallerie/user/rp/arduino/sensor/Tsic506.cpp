#include "Arduino.h"
#include "Tsic506.h"

uint8_t readBit(uint8_t dataPin) {
  uint8_t loopIndex = 0;
  
  while (digitalRead(dataPin) == HIGH) {
    loopIndex++;
    if (loopIndex >= LOOP_TIME_OUT) {
      return ERROR_VALUE;
    }
  }
  loopIndex = 0;
  while (digitalRead(dataPin) == LOW) {
    loopIndex++;
    if (loopIndex >= LOOP_TIME_OUT) {
      return ERROR_VALUE;
    }
  }
  
  return loopIndex;
}

boolean readByte(uint16_t *data, uint8_t dataPin) {
  uint16_t startLoopIndex;
  uint8_t strobeTime;
  uint8_t bitTime;
  uint8_t bitCount;

  // start bit
  startLoopIndex = 0;
  while (digitalRead(dataPin) == HIGH) {
    startLoopIndex++;
    if (startLoopIndex >= START_LOOP_TIME_OUT) {
      return false;
    }
  }
  strobeTime = 0;
  while (digitalRead(dataPin) == LOW) {
    strobeTime++;
    if (strobeTime >= LOOP_TIME_OUT) {
      return false;
    }
  }

  // read byte
  for (bitCount = 0; bitCount < 8; bitCount++) {
    bitTime = readBit(dataPin);
    if (bitTime == ERROR_VALUE) {
      return false;
    }
    *data <<= 1;
    if (bitTime < strobeTime) {
      *data |= 1;
    }
  }
  
  // parity bit
  if (readBit(dataPin) == ERROR_VALUE) {
    return false;
  }
  // TODO check parity
  
  return true;
}

uint16_t readSensor(uint8_t dataPin) {
  delayMicroseconds(1000);
  
  uint16_t data = 0;
  if (readByte(&data, dataPin) == false) {
    return ERROR_VALUE;
  }
  if (readByte(&data, dataPin) == false) {
    return ERROR_VALUE;
  }
  
  return data;
}
