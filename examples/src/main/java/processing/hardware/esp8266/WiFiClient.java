package processing.hardware.esp8266;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppMethod;

@NativeCBody(cImpl = "#include <ESP8266WiFi.h>")
public class WiFiClient {
    private WiFiClient realSubject;

    public WiFiClient() {
    	realSubject=WiFiClient();
    };

    @NativeCppMethod
    private native static WiFiClient WiFiClient();

    @NativeCppMethod
	public native boolean available();

    @NativeCppMethod
	public native String readStringUntil(char c);

    @NativeCppMethod
	public native void flush();

    @NativeCppMethod
	public native void stop();

    @NativeCppMethod
	public native void print(String s);

}
