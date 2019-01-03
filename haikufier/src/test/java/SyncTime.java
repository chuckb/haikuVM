import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SyncTime
{
    public SyncTime()
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
    	BufferedReader in;
        
        public SerialReader ( InputStream in )
        {
            this.in = new BufferedReader(new InputStreamReader(in)); 
        }
        
        public void run ()
        {
            String line;
            try
            {
                for(int i=0; ( line = in.readLine()) != null && i<5; i++)
                {
                    System.out.println(line);
                }
                line = in.readLine();
            	long j0=System.currentTimeMillis();
                System.out.println(line);
                long h0=Integer.parseInt(line);
                System.out.printf("%s\t%d\n", line, j0);
                
                while ( ( line = in.readLine()) != null )
                {
                	long j1=System.currentTimeMillis();
                    try {
						long h1=Integer.parseInt(line);
						System.out.printf("%s\t%g\n", line, 1.0*(j1-j0)/(h1-h0));
					} catch (NumberFormatException e) {
						long h1=0;
						System.out.printf("%s\t%g\n", line, 1.0*(j1-j0)/(h1-h0));
					}
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
                while ( ( c = System.in.read()) > -1 )
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
            (new SyncTime()).connect("COM17", 57600);
            (new SyncTime()).connect("COM19", 2400);
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
