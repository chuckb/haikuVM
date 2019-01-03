package java.lang;

/**
 * All exceptions and errors extend this class. 
 */
public class Throwable
{
    private int[] _stackTrace;
	private String _message;
    Throwable _cause;
	
	//MISSING void printStackTrace(PrintWriter pw)

    /**
     * Create a Throwable object. Call fillInStackTrace to create a trace of
     * stack when the Throwable was created.
     */
	public Throwable() 
	{
        fillInStackTrace();
	}

    /**
     * Create a Throwable object. Call fillInStackTrace to create a trace of
     * stack when the Throwable was created. Set the message to the provided
     * string.
     * @param message Message providing details of the error/exception.
     */
	public Throwable(String message)
	{
        fillInStackTrace();
		_message = message;
	}
    
    /**
     * Create a Throwable object. Call fillInStackTrace to create a trace of
     * stack when the Throwable was created. Set the message to the provided
     * string. Calls initCause to set the cause of the Throwable.
     * @param message Message providing details of the error/exception.
     * @param cause the cause (can be null)
     */    
    public Throwable(String message, Throwable cause)
    {
        fillInStackTrace();
        _message = message;
        initCause(cause);
    }
    
    /**
     * Create a Throwable object. Call fillInStackTrace to create a trace of
     * stack when the Throwable was created. Set the message to be cause.toString
     * (if cause is not null). Calls initCause to set the cause of the Throwable.
     * @param cause the cause (can be null)
     */    
    public Throwable(Throwable cause)
    {
        fillInStackTrace();
        _message = cause == null ? null : cause.toString();
        initCause(cause);
    }

    /**
     * Initializes the cause of this throwable to the specified value. 
     * @param cause The cause (can be null)
     * @return  a reference to this throwable 
     */
    public Throwable initCause(Throwable cause)
    {
        if (_cause != null)
            throw new IllegalStateException();
        if (cause == this)
            throw new IllegalArgumentException();
        _cause = cause;
        return this;
    }

    /**
     * Returns the cause of this throwable or null if the cause is nonexistent or unknown.
     * @return the cause or null.
     */
    public Throwable getCause()
    {
        return _cause;
    }
    
	/**
	 * Can be overridden, to return localized messages.
	 * The default implementation returns the same as {@link #getMessage()}.
	 * @return Localized message string or null if there is no message
	 */
	public String getLocalizedMessage()
	{
		return this.getMessage();
	}

    /**
     * Return the message associated with this Throwable object.
     * @return Message string or null if there is no message.
     */
	public String getMessage()
	{
		return _message;
	}

    /**
     * Return a string version of the Throwable. This will consist of details of
     * the actual class and the detail message if set.
     * @return A string representation.
     */
	@Override
	public String toString()
	{
        //sb.append(this.getClass().toString());
        return _message;
	}

    /**
     * Capture a stack trace. Note that the frames containing this (and other
     * calls directly relating to the Throwable object). will be omitted.
     * @return The Throwable object.
     */
    public Throwable fillInStackTrace()
    {
        //_stackTrace = VM.createStackTrace(Thread.currentThread(), this);
        return this;
    }

    /**
     * Print details of the exception/error to the provided stream. The details
     * will contain the throwable class, the text of the detail message (if any)
     * and a series of lines providing a stack trace at the time the Throwable
     * was created.
     * @param s The print stream on which to output the trace.
    public void printStackTrace(PrintStream s)
    {
        Throwable curItem = this;
        while(curItem != null)
        {
            if (curItem != this)
                s.print("Caused by: ");
            s.println(curItem.toString());
            if (curItem._stackTrace != null)
            {
                for(int i : curItem._stackTrace)
                    s.println(" at: " + (i >> 16) + ":" + (i & 0xffff));
            }
            curItem = curItem._cause;
        }
    }
     */

    /**
     * Print details of the exception/error on the system error stream. See
    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    private void displayLocation(int method, int pc, int lcdLine)
    {
        if (lcdLine >= 0 && lcdLine < LCD.DISPLAY_CHAR_DEPTH)
        {
            LCD.clear(lcdLine);
            LCD.drawString(" at: ", 0, lcdLine);
            LCD.drawInt(method, 4, 5, lcdLine);
            LCD.drawString(":", 9, lcdLine);
            LCD.drawInt(pc, 10, lcdLine);
        }
    }
     */

    /**
     * This is called by the system to display information about uncaught exceptions.
     * We stop all threads (except this one), halt the motors and present a simple
     * LCD UI to display the information. The left and right cursor keys can be
     * used to display information that does not fit on the screen. The escape key
     * will stop the program.
     * @param methodNo
     * @param pc 
    void uncaughtException(int methodNo, int pc)
    {
        VM.suspendThread(null);
        LCD.setAutoRefresh(false);
        for(int i = 0; i < MotorPort.NUMBER_OF_PORTS; i++)
            MotorPort.getInstance(i).controlMotor(0, MotorPort.FLOAT);
        int curOffset = 0;
        for(;;)
        {
            Throwable curItem = this;
            int curLine = 0;
            LCD.clear();
            while(curItem != null)
            {
                LCD.drawString(curItem == this ? "Exception:" : "Caused by:", 0, curLine - curOffset);
                LCD.drawInt(VM.getClassNumber(curItem.getClass()), 11, curLine++ - curOffset);
                if (curItem._message != null) LCD.drawString(curItem._message, 0, curLine++ - curOffset);

                if (curItem._stackTrace == null)
                {
                    if (curItem == this)
                        displayLocation(methodNo, pc, curLine++ - curOffset);
                }
                else
                    for(int i : curItem._stackTrace)
                        displayLocation(i >> 16, i & 0xffff, curLine++ - curOffset);
                curItem = curItem.getCause();
            }
            LCD.refresh();
            // Scroll the display or exit
            switch(Button.waitForAnyPress())
            {
                case Button.ID_ESCAPE:
                    // resume threads to allow shutdown hooks to run correctly
                    VM.resumeThread(null);
                    // and reboot, running any system shutdown hooks
                    Runtime.getRuntime().exit(0);
                    return;
                case Button.ID_LEFT:
                    if (curOffset > 0) curOffset--;
                    break;
                case Button.ID_RIGHT:
                    if (curOffset < curLine - LCD.DISPLAY_CHAR_DEPTH) curOffset++;
                    break;
            }
        }
    }
     */
}
