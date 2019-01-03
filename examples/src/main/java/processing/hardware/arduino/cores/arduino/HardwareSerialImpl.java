package processing.hardware.arduino.cores.arduino;

public class HardwareSerialImpl {
    public static native void begin(long speed);

    public static native int available();

    public static native int read();

    public static native byte write(byte c);

	public static native byte write(char c);

    public static native boolean isOpen();
}
