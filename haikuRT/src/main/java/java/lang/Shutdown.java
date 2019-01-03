package java.lang;

/**
 * Do nothing: Just overwrite leJOS Shutdown
 * 
 * @author genom2
 *
 */
class Shutdown
{
    private Shutdown()
    {
        //empty
    }
    
    /**
     * Terminate the application. Does not trigger the calling of shutdown hooks.
     */
    public static void halt(int code) {
    	
    }
    
    /**
     * Tell the system that we want to shutdown. Calling this will trigger the running
     * of any shutdown hooks. If no hooks are installed the system will simply terminate.
     */
    private static void shutdown(){
    }
    

    
    /**
     * Called to shutdown the system. If any shutdown hooks have been installed 
     * these will be run before the system terminates. This function will never return.
     */
    public static void shutdown(int code)
    {
   }

    /**
     * Install a shutdown hook. Can only be called before system shutdown has
     * started.
     * @param hook
     */
    public static void addShutdownHook(Thread hook)
    {
    }

    /**
     * Remove a shutdown hook.
     * @param hook item to be removed
     * @return true iff the hook is actually removed
     */
    public static boolean removeShutdownHook(Thread hook)
    {
        return false;
    }

}
