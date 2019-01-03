package java.lang;

public class Thread implements Runnable {
	static final int IDLE = 0;
	static final int YIELD = 2;
	//static final int INIT = 4;
	static final int WAITING = 6;
	static final int STOPPED = 8;

	public static final int MAX_PRIORITY = 10;
	public static final int NORM_PRIORITY = 5;
	public static final int MIN_PRIORITY = 1;

    // Note the following fields are mapped on/to a C struct held within the VM.
	// They should not be changed. New fields should not
    // be added to this class unless symetric changes are also made to the VM and
	// the former mentioned C struct.

	static volatile Thread currentThread; // read only
	//static volatile Object currentStackBlock; // needed for HaikuVM VM C code

	volatile Runnable target;
	volatile Thread next; // All Threads are in a circular queue.
	volatile int state;

	volatile Object programcounter;
	volatile Object stackpointer;
	volatile Object stackframe;
	volatile Object stackblock; // allocated block of heap memory
	volatile long   waitUntil;
	volatile Object locks; // HaikuVM will use this for an entry in a queue of locks for this thread
	volatile boolean interrupt;
	volatile Object waitingOn; // This Thread waits for this.waitingOn Object to be released

//	/**
//	 * This is needed for HaikuVM VM bootstrap.
//	 * Where currentStackBlock is already assigned (with a pointer to a stack block) from within the C part.
//	 */
//	static {
//		// init main thread
//		currentThread=new Thread();
//		//enqueue it (in a tight loop)
//		currentThread.next = currentThread;
//		currentThread.stackblock = currentStackBlock;
//	}

	public static void foolingProGuard() { // to keep field currentThread
	    currentThread = new Thread();
	}

	public Thread() {
		target = this;
	}

	public Thread(Runnable target) {
		this.target = target;
	}

    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds, subject to
     * the precision and accuracy of system timers and schedulers. The thread
     * does not lose ownership of any monitors.
     *
     * @param      millis   the length of time to sleep in milliseconds.
     * @exception  InterruptedException if any thread has interrupted
     *             the current thread.  The <i>interrupted status</i> of the
     *             current thread is cleared when this exception is thrown.
     * @see        Object#notify()
     */
    public static void sleep(long millis) throws InterruptedException {
		currentThread.waitUntil=System.currentTimeMillis()+millis;
		currentThread.setStateAndSwitch(WAITING);
		if (currentThread.interrupt) {
			currentThread.interrupt=false;
			throw new InterruptedException();
		}
    }

    public static void nap(long millis) {
		currentThread.waitUntil=System.currentTimeMillis()+millis;
		currentThread.setStateAndSwitch(WAITING);
    }

	public void start() {
		//TODO: using synchronized would be correct
		if (next==null) {
			// We have a new Thread to be enqueued into Thread->next chain
			if (fork()==0) {
				//child:
			    currentThread.run$();
                // child never reaches this point
			}
			// parent (is currentThread):
			// enqueue the child. This effectively makes this (the new child) a valid thread for scheduling.
			// order of this statements is critical/important
			next=currentThread.next;
			currentThread.next=this; // this must be atomic in the VM
		} else {
		    throw new IllegalThreadStateException();
		}
	}

	/**
	 * Inspired from UNIX fork().
	 * This is what happens (in C): <pre>
	 *  thisThread->programcounter=pc; // current pc
	 *  thisThread->stack=new StackFrame(36); // so on thisThreads stack, top stack element is an integer 0 (==> child)
	 *  push the integer 1 // in contrast on current stack, top stack element is integer 1 (==> parent) </pre>
	 *  @return 0 if it's the new child Thread else 1
	 */
	private native int fork();

    @Override
    public void run() {
        if (target!=null) {
            target.run();
        }
    }

    public void run$() {
        try {
            run();
        } catch (Throwable e) {
            // Nice idea:
            //if (System.out!=null) System.out.println(e.toString());
            // But:
            // a) induces unwanted IO via undefined(!) System.out. See very simple Thread examples (e.g. asuro.tutorial.BlinkWithThread).
            // b) includes unwanted class and method overhead while including System.*
        }
        stop();   // this forces a Thread switch in HaikuVM
    }

    /**
     * Forces the thread to stop executing.
     * <p>
     * @exception  SecurityException  if the current thread cannot
     *               modify this thread.
     * @see        #interrupt()
     * @see        #checkAccess()
     * @see        #run()
     * @see        #start()
     * @see        ThreadDeath
     * @see        ThreadGroup#uncaughtException(Thread,Throwable)
     * @see        SecurityManager#checkAccess(Thread)
     * @see        SecurityManager#checkPermission
     */
    public final void stop() {
    	/* In case I write: if (state>STOPPED)
    	 * the bytecode (fib(20)) gets larger by 8. But the Atmega Program by 34?? Why?
    	 * ==> reason is here: OPF_GETFIELD_I & getField() for state
    	 */
		if (state!=STOPPED) { //TODO: using synchronized would be more correct
	    	// dequeue this Thread
	    	Thread t=this;
	    	while (t.next!=this) t=t.next;
	    	t.next=t.next.next;
	    	setStateAndSwitch(STOPPED);   // this forces a Thread switch in HaikuVM
		}
    }

    /**
     * Returns a reference to the currently executing thread object.
     *
     * @return  the currently executing thread.
     */
    public static Thread currentThread() {
    	return currentThread;
    }

    /**
     * Returns a reference to the next executing thread object.
     *
     * @return  the next executing thread.
     */
    public static Thread nextThread() {
    	return currentThread.next;
    }

    /**
     * Causes the currently executing thread object to temporarily pause
     * and allow other threads to execute.
     */
    public static void yield() {
		currentThread.setStateAndSwitch(YIELD);   // this forces a Thread switch in HaikuVM
    }

    /**
     * Thread.state is set and a Thread switch is forced.
     *
     * @param state Thread.state is set to state
     * @return
     */
	native int setStateAndSwitch(int state);

	  /**
	   * Sets the state of the threads daemon flag. If this flag is set then the
	   * system will not wait for it to exit when all other none daemon threads
	   * have exited.
	   * @param on the new state of the daemon flag
	   */
    public void setDaemon(boolean b) {
		// TODO Auto-generated method stub
	}

	  /**
	   * Set the priority of this thread. Higher number have higher priority.
	   * The scheduler will always run the highest priority thread in preference
	   * to any others. If more than one thread of that priority exists the
	   * scheduler will time-slice them. In order for lower priority threas
	   * to run a higher priority thread must cease to be runnable. i.e. it
	   * must exit, sleep or wait on a monitor. It is not sufficient to just
	   * yield.
	   * <P>
	   * Threads inherit the priority of their parent. The primordial thread
	   * has priority NORM_PRIORITY.
	   *
	   * @param priority must be between MIN_PRIORITY and MAX_PRIORITY.
	   */
	public void setPriority(int priority) {
		// TODO Auto-generated method stub
	}


	  public Thread (String name)
	  {
	    this();
	  }

	  public Thread(Runnable target, String name)
	  {
	      this(target);
	  }

	  public final int getPriority() {
		  return 0;
	  }

	  /**
	   * Returns the string name of this thread.
	   * @return The name of this thread.
	   */
	  public String getName()
	  {
	      return "";
	  }

	  /**
	   * Sets the string name associated with this thread.
	   * @param name The new name of the thread.
	   */
	  public void setName(String name)
	  {
	  }

	  /**
	   * Set the interrupted flag. If we are asleep we will wake up
	   * and an InterruptedException will be thrown.
	   */
	  public void interrupt() {
		  interrupt=true;
		  setStateAndSwitch(IDLE);
	  }

	  /**
	   * Tests the interrupted state of the current thread. If it is interrupted it
	   * will true and clear the interrupted state. Otherwise it will return false.
	   * @return true if the current thread has been interrupted otherwise false
	   */
	  public static boolean interrupted() {
		  if (currentThread.isInterrupted()) {
			  currentThread.interrupt=false;
			  return true;
		  }
		  return false;
	  }

	  /**
	   * Tests to see if the current thread has been interrupted but leaves the
	   * interrupted state unchanged.
	   * @return true if the current thread has been interrupted otherwise false
	   */
	  public final boolean isInterrupted() {
		  return interrupt;
	  }

	  /**
	   * Set the daemon flag. If a thread is a daemon thread its existence will
	   * not prevent a JVM from exiting.
	   * @return true if this thread has the daemon flag set otherwise false
	   */
		public final boolean isDaemon() {
			return false;
		}


		public final boolean isAlive() {
			return state==IDLE;
		}

	  /**
	   * Waits for this thread to die.
	   * @throws InterruptedException
	   */
	  public final void join() throws InterruptedException {
	    	while (state!=STOPPED) {
	    		sleep(500);
	    	}
	  }

	  /**
	   * Waits for up to timeout mS for this thread to die.
	   * @param timeout The period in ms to wait for this thread to die
	   * @throws InterruptedException
	   */
	  public final void join(long timeout) throws InterruptedException {
		  long t1 = System.currentTimeMillis()+timeout;
		  while (state!=STOPPED && System.currentTimeMillis()-t1>0) {
			  sleep(500);
		  }
	  }

		/**
		 * Wake up all threads blocked on a wait(). Must be synchronized on
		 * this object otherwise an IllegalMonitorStateException will be thrown.
		 */
	static void notifyAll(Object object) {
    	Thread t=currentThread;
    	while (t.next!=currentThread) {
    		t=t.next;
    		if (t.waitingOn==object) {
    			t.state=IDLE;
    		}
    	}
	}

	/**
	 * Wake up one thread blocked on a wait(). Must be synchronized on
	 * this object otherwise an IllegalMonitorStateException will be thrown.
	 * <P>
	 * If multiple threads are waiting, the thread that gets woken is essentially
	 * random.
	 */
	static void notify(Object object) {
		int count=0;
    	Thread t=currentThread;
    	while (t.next!=currentThread) {
    		t=t.next;
    		if (t.waitingOn==object) {
        		count++;
    		}
    	}
    	if (count==0) return;
		int rand=(int) System.currentTimeMillis() % count;
    	t=currentThread;
    	while (t.next!=currentThread) {
    		t=t.next;
    		if (t.waitingOn==object) {
    			if (rand==0) {
        			t.state=IDLE;
        			return;
    			} else {
        			rand--;
    			}
    		}
    	}
	}

	/**
	 * releases a lock (object.MONITOREXIT) and<br>
	 * tries to get it again after waitUntil is reached (object.MONITORENTER).
	 * Indirect used from Object.wait(..).
	 */
	private static native void haikuReleaseLock(Object object);

    /**
     * releases a lock on object and<br>
     * tries to get it again after timeout ms.
	 * Used from/for Object.wait(..).
     */
	static void haikuWait(Object object, long timeout) throws InterruptedException {
    	try {
			currentThread.waitingOn=object;
	        if (timeout==0) {
	            currentThread.waitUntil=-1; //intern waitUntil is unsigned long (but beware spurious wakeup)
	        } else {
	            currentThread.waitUntil=System.currentTimeMillis()+timeout;
	        }
			haikuReleaseLock(object);
			if (interrupted()) {
				throw new InterruptedException();
			}
		} catch (InterruptedException e) {
			throw e;
		} finally {
			currentThread.waitingOn=null;
		}
	}
}
