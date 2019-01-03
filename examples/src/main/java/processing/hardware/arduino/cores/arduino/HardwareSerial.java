package processing.hardware.arduino.cores.arduino;

public class HardwareSerial extends Stream {
    // HardwareSerial(ring_buffer *rx_buffer,
    // volatile uint8_t *ubrrh, volatile uint8_t *ubrrl,
    // volatile uint8_t *ucsra, volatile uint8_t *ucsrb,
    // volatile uint8_t *udr,
    // uint8_t rxen, uint8_t txen, uint8_t rxcie, uint8_t udre);

    // public void flush();
    private int lastChar = -1;

    /**
     * 9600
     * 57600
     * etc.
     * @param speed
     */
    public void begin(long speed) {
        HardwareSerialImpl.begin(speed);
    }

    public int available() {
        return HardwareSerialImpl.available();
    }

    public int read() {
        if (lastChar!=-1) {
            int c=lastChar;
            lastChar=-1;
            return c;
        }
        return HardwareSerialImpl.read();
    }

    public byte write(byte c) {
        return HardwareSerialImpl.write(c);
    }

    public boolean isOpen() {
        return HardwareSerialImpl.isOpen();
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public int peek() {
        if (lastChar!=-1) {
            return lastChar;
        }
        lastChar= HardwareSerialImpl.read();
        return lastChar;
    }

	public byte write(char c) {
        return HardwareSerialImpl.write(c);
	}
}
