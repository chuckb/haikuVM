package processing.hardware.esp8266;

import haiku.vm.NativeCBody;
import haiku.vm.NativeCppMethod;

@NativeCBody(cImpl = "#include <ESP8266WiFi.h>")
public class WiFiServer {
    private WiFiServer realSubject;

    public WiFiServer(int port) {
        realSubject=WiFiServer(port);
	}


    @NativeCppMethod
    private native static WiFiServer WiFiServer(int port);

    @NativeCppMethod
	public native void begin();

    @NativeCppMethod
	public native WiFiClient available();

}
