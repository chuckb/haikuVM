import jssc.SerialPort;
import jssc.SerialPortException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Monitor
{
    public Monitor()
    {
        super();
    }
    
    void connect ( String portName, int speed) throws Exception
    {
    	final SerialPort serialPort = new SerialPort(portName);
    	serialPort.openPort();//Open port
        
        serialPort.setParams(speed, SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
        
        InputStream in = new InputStream() {
			@Override
			public int read() throws IOException {
				try {
					return serialPort.readBytes(1)[0] & 0xff;
				} catch (SerialPortException e) {
					e.printStackTrace();
					return -1;
				}
			}
        };
        OutputStream out = new OutputStream(){

			@Override
			public void write(int b) throws IOException {
				try {
					serialPort.writeByte((byte)b);
				} catch (SerialPortException e) {
					e.printStackTrace();
					throw new IOException(e);
				}			
			}};
        
        (new Thread(new SerialReader(in))).start();
        (new Thread(new SerialWriter(out))).start();
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {
        
        private InputStreamReader in;

        public SerialReader ( InputStream in )
        {
            this.in = new InputStreamReader(in); 
        }
        
        public void run ()
        {
            int c;
            try
            {
                for ( int i=0; (c = in.read()) >0; i++ )
                {
					System.out.print((char) c);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                
                while ( ( c = System.console().reader().read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
    
    public static void main ( String[] args )
    {
    	//System.out.println("hello "+System.getProperty("java.library.path"));
        try
        {
            (new Monitor()).connect("COM17", 57600);
            //(new Monitor()).connect("COM19", 2400);
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
