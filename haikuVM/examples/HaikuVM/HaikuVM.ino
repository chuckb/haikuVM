/*
 *  HaikuVM 1.4.3
 */

#include "haikuJ2C.h"
#include <Wire.h> // i don't know why but this is needed that bootstrap/src/main/java/haiku/arduino/processing/Wire.java is working

#define _DEBUG 0

#if _DEBUG
#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#endif

/*
 * Setup the Haiku JAVA VM
 * and start main(String[]) of the MicroKernel
 */
void setup()
{
#if _DEBUG
  // Open serial communications and wait for port to open:
  Serial.begin(57600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
#endif

#if ESP8266
  delay(10000);
#endif
  setupHaikuVM();
}

/*
 * Interpret for ever a chunk of 100 JAVA byte codes per loop call
 */
void loop(void) {
  interpretNextByteCodeChunk();
}




/*
 * some handy functions for debugging
 *
 */
#if _DEBUG

#ifdef __cplusplus
extern "C" {
#endif
int jprintf(const char * format, ...);
void myprintln(char * msg);
void myprint(char * msg);
void myprintInt(int i);
void myprintLong(long i);
#ifdef __cplusplus
}
#endif

int jprintf(const char * format, ...) {
  int err;
  va_list list;
  va_start(list, format);

  for( const char * p=format; *p; p++) {
    if (p[0]=='%' && p[1]=='d') {
      p++;
      Serial.print(va_arg(list, int));
    } else if (p[0]=='%' && p[1]=='x') {
      p++;
      Serial.print(va_arg(list, int), HEX);
    } else if (p[0]=='%' && p[1]=='l') {
      p++;
      Serial.print(va_arg(list, long));
    } else {
      Serial.print(*p);
    }
  }
  va_end(list);
  return err;
}

  void myprintln(char * msg) {
    Serial.println(msg);
  }

  void myprint(char * msg) {
    Serial.print(msg);
  }

  void myprintInt(int i) {
    Serial.print(i);
  }

  void myprintLong(long i) {
    Serial.print(i);
  }

  void myprint(int i) {
    Serial.print(i);
    Serial.print(" ");
  }
#endif