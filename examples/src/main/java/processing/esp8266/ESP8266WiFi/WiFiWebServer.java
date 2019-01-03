package processing.esp8266.ESP8266WiFi;

import static processing.hardware.arduino.cores.arduino.Arduino.*;
//#include <ESP8266WiFi.h>
import static processing.hardware.esp8266.ESP8266WiFiClass.*;
import processing.hardware.esp8266.WiFiClient;
import processing.hardware.esp8266.WiFiServer;
import processing.hardware.esp8266.wl_definitions;

/**
 * <pre>
 *  This sketch demonstrates how to set up a simple HTTP-like server.
 *  The server will set a GPIO pin depending on the request
 *    http://server_ip/gpio/0 will set the GPIO2 low,
 *    http://server_ip/gpio/1 will set the GPIO2 high
 *  server_ip is the IP address of the ESP8266 module, will be
 *  printed to Serial when the module is connected.
 * </pre>
 */
public class WiFiWebServer {

	private static final String ssid = "peac0ck!";
	private static final String password = "bqvc13mt";

	// Create an instance of the server
	// specify the port to listen on as an argument
	private static WiFiServer server =  new WiFiServer(80);

	public static void setup() {
	  Serial.begin(57600);
	  delay(5000);

	  // prepare GPIO2
	  pinMode(2, OUTPUT);
	  digitalWrite(2, 0);

	  // Connect to WiFi network
	  Serial.println();
	  Serial.println();
	  Serial.print("Connecting to ");
	  Serial.println(ssid);

	  WiFi.begin(ssid, password);

	  while (WiFi.status() != wl_definitions.WL_CONNECTED) {
	    delay(500);
	    Serial.print(".");
	  }
	  Serial.println("");
	  Serial.println("WiFi connected");

	  // Start the server
	  server.begin();
	  Serial.println("Server started");

	  // Print the IP address
	  Serial.println(WiFi.localIP());
	}

	public static void loop() {
	  // Check if a client has connected
	  WiFiClient client = server.available();
	  if (client==null) {
	    return;
	  }

	  // Wait until the client sends some data
	  Serial.println("new client");
	  while(!client.available()){
	    delay(1);
	  }

	  Serial.println("Read the first line of the request");
	  String req = client.readStringUntil('\r');
	  Serial.println("Done");
	  Serial.println(req);
	  client.flush();
	  Serial.println("flushed");

	  // Match the request
	  int val;
	  if (req.indexOf("/gpio/0") != -1)
	    val = 0;
	  else if (req.indexOf("/gpio/1") != -1)
	    val = 1;
	  else {
	    Serial.println("invalid request");
	    client.stop();
	    return;
	  }

	  // Set GPIO2 according to the request
	  digitalWrite(2, val);

	  client.flush();

	  // Prepare the response
	  String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE HTML>\r\n<html>\r\nGPIO is now ";
	  s += (val!=0)?"high":"low";
	  s += "</html>\n";

	  // Send the response to the client
	  client.print(s);
	  delay(1);
	  Serial.println("Client disonnected");

	  // The client will actually be disconnected
	  // when the function returns and 'client' object is detroyed
	}
}
