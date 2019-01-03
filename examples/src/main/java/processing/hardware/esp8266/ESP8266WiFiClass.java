package processing.hardware.esp8266;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppMethod;

//#include <stdint.h>

/**
ESP8266WiFi.h - esp8266 Wifi support.
Based on WiFi.h from Arduino WiFi shield library.
Copyright (c) 2011-2014 Arduino.  All right reserved.
Modified by Ivan Grokhotkov, December 2014

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
@NativeCBody(cImpl = "#include <ESP8266WiFi.h>")
public class ESP8266WiFiClass {
    private ESP8266WiFiClass realSubject;

    public ESP8266WiFiClass() {
    	realSubject=ESP8266WiFiClass();
    };

	public static ESP8266WiFiClass WiFi = new ESP8266WiFiClass();


    @NativeCppMethod
    private native static ESP8266WiFiClass ESP8266WiFiClass();

    @NativeCppMethod
    public native void begin(String ssid, String password);

    @NativeCppMethod
    public native int status();

    @NativeCppMethod
    public native String localIP();
}
