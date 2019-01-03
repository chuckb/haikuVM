package communication;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import jssc.SerialPort;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Communication
{
    private SerialPort serialPort;
	private InputStream in;
	private OutputStream out;

    public Communication()
    {
        super();
    }

    public void connect ( String portName, int speed, int data, int stop, int parity) throws Exception
    {
    	serialPort = new SerialPort(portName);
    	serialPort.openPort();//Open port

        serialPort.setParams(speed, data, stop, parity);
        int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
        serialPort.setEventsMask(mask);//Set mask

        in = new InputStream() {
			@Override
			public int available() throws IOException {
				try {
					return serialPort.getInputBufferBytesCount();
				} catch (SerialPortException e) {
					throw new IOException(""+e);
				}
			}
			
			@Override
			public int read() throws IOException {
				try {
					int i = serialPort.readBytes(1)[0] & 0xff;
					//System.out.println("#"+i+"#");
					return i;
				} catch (SerialPortException e) {
					e.printStackTrace();
					return -1;
				}
			}
        };
        out = new OutputStream(){

			@Override
			public void write(int b) throws IOException {
				try {
					serialPort.writeByte((byte)b);
				} catch (SerialPortException e) {
					e.printStackTrace();
					throw new IOException(e);
				}
			}};
    }

    public void connect ( String portName, int speed) throws Exception
    {
        connect(portName, speed, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
    }

    public void connectRCX ( String portName, int speed) throws Exception
    {
        connect(portName, speed, SerialPort.DATABITS_8,SerialPort.STOPBITS_1_5,SerialPort.PARITY_ODD);
    }

    public OutputStream getOutputStream() throws IOException {
        return out;
    }

    public InputStream getInputStream() throws IOException {
        return in;
    }

    public void close() {
        try {
        	if (serialPort==null) return;
            getOutputStream().close();
            getInputStream().close();
            serialPort.removeEventListener();
            serialPort.closePort();
            serialPort=null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SerialPortException e) {
			e.printStackTrace();
		}
    }

    public void addEventListener(SerialPortEventListener sr) throws TooManyListenersException, SerialPortException {
//      try {
//          serialPort.enableReceiveTimeout(1000);
//      } catch (UnsupportedCommOperationException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
        serialPort.addEventListener(sr);
        //serialPort.notifyOnDataAvailable(true);
    }

	public SerialPort getPort() {
		return serialPort;
	}

}
