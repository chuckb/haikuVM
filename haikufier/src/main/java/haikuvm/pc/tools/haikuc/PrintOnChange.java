package haikuvm.pc.tools.haikuc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Formatter;

public class PrintOnChange {
    StringBuilder buffer = new StringBuilder();
    protected File file;

    public PrintOnChange(File file) {
        this.file=file;
    }

    public void println(String line) {
        buffer.append(line).append("\n");
    }
    
    public void println(Object obj) {
        buffer.append(obj).append("\n");
    }

    public void println() {
        buffer.append("\n");
    }

    public void print(String str) {
        buffer.append(str);
    }

    public void print(Object obj) {
        buffer.append(obj);
    }

   public void printf(String format, Object ... args) {
        buffer.append(new Formatter().format(format, args));
    }

   public void close() throws IOException {
       close(file, buffer);
   }

   static void close(File file, StringBuilder buffer) throws IOException {
       try {
           long len = file.length();
           byte[] f = buffer.toString().getBytes("utf-8");
           if (len==f.length) {
               InputStream is=new FileInputStream(file);
               byte[] old = new byte[(int)file.length()];
               is.read(old);
               is.close();
               if (Arrays.equals(f, old)) {
                   return;
               }
           }
           write(file, buffer);
       } catch (UnsupportedEncodingException e) {
       } catch (FileNotFoundException e) {
           write(file, buffer);
       }
    }

    private static void write(File file, StringBuilder buffer) throws IOException {
        file.getParentFile().mkdirs();
        PrintStream out = new PrintStream(file);
        out.write(buffer.toString().getBytes("utf-8"));
        out.close();        
    }
}
