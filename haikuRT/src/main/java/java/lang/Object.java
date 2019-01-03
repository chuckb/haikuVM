package java.lang;

public class Object {

	public String toString() {
		int hash = System.identityHashCode(this);
		return "Object@"+hash;
	}

	public boolean equals(Object other)
	{
		return other == this;
	}

    /**
     * Causes the current thread to wait until either another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or a
     * specified amount of time has elapsed.
     * <p>
     * The current thread must own this object's monitor.
     * <p>
     * This method causes the current thread (call it <var>T</var>) to
     * place itself in the wait set for this object and then to relinquish
     * any and all synchronization claims on this object. Thread <var>T</var>
     * becomes disabled for thread scheduling purposes and lies dormant
     * until one of four things happens:
     * <ul>
     * <li>Some other thread invokes the <tt>notify</tt> method for this
     * object and thread <var>T</var> happens to be arbitrarily chosen as
     * the thread to be awakened.
     * <li>Some other thread invokes the <tt>notifyAll</tt> method for this
     * object.
     * <li>Some other thread {@linkplain Thread#interrupt() interrupts}
     * thread <var>T</var>.
     * <li>The specified amount of real time has elapsed, more or less.  If
     * <tt>timeout</tt> is zero, however, then real time is not taken into
     * consideration and the thread simply waits until notified.
     * </ul>
     * The thread <var>T</var> is then removed from the wait set for this
     * object and re-enabled for thread scheduling. It then competes in the
     * usual manner with other threads for the right to synchronize on the
     * object; once it has gained control of the object, all its
     * synchronization claims on the object are restored to the status quo
     * ante - that is, to the situation as of the time that the <tt>wait</tt>
     * method was invoked. Thread <var>T</var> then returns from the
     * invocation of the <tt>wait</tt> method. Thus, on return from the
     * <tt>wait</tt> method, the synchronization state of the object and of
     * thread <tt>T</tt> is exactly as it was when the <tt>wait</tt> method
     * was invoked.
     * <p>
     * A thread can also wake up without being notified, interrupted, or
     * timing out, a so-called <i>spurious wakeup</i>.  While this will rarely
     * occur in practice, applications must guard against it by testing for
     * the condition that should have caused the thread to be awakened, and
     * continuing to wait if the condition is not satisfied.  In other words,
     * waits should always occur in loops, like this one:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * (For more information on this topic, see Section 3.2.3 in Doug Lea's
     * "Concurrent Programming in Java (Second Edition)" (Addison-Wesley,
     * 2000), or Item 50 in Joshua Bloch's "Effective Java Programming
     * Language Guide" (Addison-Wesley, 2001).
     *
     * <p>If the current thread is {@linkplain java.lang.Thread#interrupt()
     * interrupted} by any thread before or while it is waiting, then an
     * <tt>InterruptedException</tt> is thrown.  This exception is not
     * thrown until the lock status of this object has been restored as
     * described above.
     *
     * <p>
     * Note that the <tt>wait</tt> method, as it places the current thread
     * into the wait set for this object, unlocks only this object; any
     * other objects on which the current thread may be synchronized remain
     * locked while the thread waits.
     * <p>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the <code>notify</code> method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * @param      timeout   the maximum time to wait in milliseconds.
     * @exception  IllegalArgumentException      if the value of timeout is
     *		     negative.
     * @exception  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @exception  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final void wait(long timeout) throws InterruptedException {
    	Thread.haikuWait(this, timeout);
    }

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object, or
     * some other thread interrupts the current thread, or a certain
     * amount of real time has elapsed.
     * <p>
     * This method is similar to the <code>wait</code> method of one
     * argument, but it allows finer control over the amount of time to
     * wait for a notification before giving up. The amount of real time,
     * measured in nanoseconds, is given by:
     * <blockquote>
     * <pre>
     * 1000000*timeout+nanos</pre></blockquote>
     * <p>
     * In all other respects, this method does the same thing as the
     * method {@link #wait(long)} of one argument. In particular,
     * <tt>wait(0, 0)</tt> means the same thing as <tt>wait(0)</tt>.
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until either of the
     * following two conditions has occurred:
     * <ul>
     * <li>Another thread notifies threads waiting on this object's monitor
     *     to wake up either through a call to the <code>notify</code> method
     *     or the <code>notifyAll</code> method.
     * <li>The timeout period, specified by <code>timeout</code>
     *     milliseconds plus <code>nanos</code> nanoseconds arguments, has
     *     elapsed.
     * </ul>
     * <p>
     * The thread then waits until it can re-obtain ownership of the
     * monitor and resumes execution.
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait(timeout, nanos);
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the <code>notify</code> method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * @param      timeout   the maximum time to wait in milliseconds.
     * @param      nanos      additional time, in nanoseconds range
     *                       0-999999.
     * @exception  IllegalArgumentException      if the value of timeout is
     *			    negative or the value of nanos is
     *			    not in the range 0-999999.
     * @exception  IllegalMonitorStateException  if the current thread is not
     *               the owner of this object's monitor.
     * @exception  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     */
	public final void wait(long timeout, int nanos) throws InterruptedException {
		if (timeout < 0) {
			throw new IllegalArgumentException("timeout value is negative");
		}

		if (nanos < 0 || nanos > 999999) {
			throw new IllegalArgumentException(
					"nanosecond timeout value out of range");
		}

		if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
			timeout++;
		}

		wait(timeout);
	}

    /**
     * Causes the current thread to wait until another thread invokes the
     * {@link java.lang.Object#notify()} method or the
     * {@link java.lang.Object#notifyAll()} method for this object.
     * In other words, this method behaves exactly as if it simply
     * performs the call <tt>wait(0)</tt>.
     * <p>
     * The current thread must own this object's monitor. The thread
     * releases ownership of this monitor and waits until another thread
     * notifies threads waiting on this object's monitor to wake up
     * either through a call to the <code>notify</code> method or the
     * <code>notifyAll</code> method. The thread then waits until it can
     * re-obtain ownership of the monitor and resumes execution.
     * <p>
     * As in the one argument version, interrupts and spurious wakeups are
     * possible, and this method should always be used in a loop:
     * <pre>
     *     synchronized (obj) {
     *         while (&lt;condition does not hold&gt;)
     *             obj.wait();
     *         ... // Perform action appropriate to condition
     *     }
     * </pre>
     * This method should only be called by a thread that is the owner
     * of this object's monitor. See the <code>notify</code> method for a
     * description of the ways in which a thread can become the owner of
     * a monitor.
     *
     * @exception  IllegalMonitorStateException  if the current thread is not
     *               the owner of the object's monitor.
     * @exception  InterruptedException if any thread interrupted the
     *             current thread before or while the current thread
     *             was waiting for a notification.  The <i>interrupted
     *             status</i> of the current thread is cleared when
     *             this exception is thrown.
     * @see        java.lang.Object#notify()
     * @see        java.lang.Object#notifyAll()
     */
    public final void wait() throws InterruptedException {
    	wait(0);
    }

	protected native Object clone() throws CloneNotSupportedException;

	protected void finalize() {
		// TODO Auto-generated method stub
	}

	/**
	 * HaikuVM delivers the physical memory address as hash.
	 *
	 * TODO: Will not work if we move objects e.g. with a compacting GC.
	 *
	 * @return
	 */
	public int hashCode() {
		return System.identityHashCode(this);
	}


	public Class<?> getClass() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Wake up all threads blocked on a wait(). Must be synchronized on
	 * this object otherwise an IllegalMonitorStateException will be thrown.
	 */
	public void notifyAll() {
		Thread.notifyAll(this);
	}

	/**
	 * Wake up one thread blocked on a wait(). Must be synchronized on
	 * this object otherwise an IllegalMonitorStateException will be thrown.
	 * <P>
	 * If multiple threads are waiting, the thread that gets woken is essentially
	 * random.
	 */
	public void notify() {
		Thread.notify(this);
	}

}
