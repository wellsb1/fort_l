/*
 * Copyright 2008-2017 Wells Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.forty11.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.logging.Log;

/**
 * @author wells.burke
 */
public class LoggingPrintStream extends PrintStream
{
   protected static String RETURN_CODE = System.getProperty("line.separator");

   public static final int TRACE       = 0;
   public static final int DEBUG       = 1;
   public static final int INFO        = 2;
   public static final int WARN        = 3;
   public static final int ERROR       = 4;
   public static final int FATAL       = 5;

   protected Log           log         = null;
   protected int           level       = INFO;

   public LoggingPrintStream()
   {
      super(new DoNothingOutputStream());
   }

   public LoggingPrintStream(Log log, int level)
   {
      super(new DoNothingOutputStream());
      this.log = log;
      this.level = level;
   }

   @Override
   public void write(int b)
   {

   }

   @Override
   public void write(byte buf[], int off, int len)
   {

   }

   @Override
   public void write(byte[] b) throws IOException
   {

   }

   @Override
   public void print(boolean b)
   {
      print(new Boolean(b));
   }

   @Override
   public void print(char c)
   {
      print(new Character(c));
   }

   @Override
   public void print(int i)
   {
      print(new Integer(i));
   }

   @Override
   public void print(long l)
   {
      print(new Long(l));
   }

   @Override
   public void print(float f)
   {
      print(new Float(f));
   }

   @Override
   public void print(double d)
   {
      print(new Double(d));
   }

   @Override
   public void print(char s[])
   {
      String str = new String(s);
      print(str);
   }

   @Override
   public void print(String s)
   {
      switch (level)
      {
         case DEBUG:
            log.debug(s);
            break;
         case INFO:
            log.info(s);
            break;
         case WARN:
            log.warn(s);
            break;
         case ERROR:
            log.error(s);
            break;
         case FATAL:
            log.fatal(s);

      }
   }

   @Override
   public void print(Object obj)
   {
      switch (level)
      {
         case DEBUG:
            log.debug(obj);
            break;
         case INFO:
            log.info(obj);
            break;
         case WARN:
            log.warn(obj);
            break;
         case ERROR:
            log.error(obj);
            break;
         case FATAL:
            log.fatal(obj);

      }
   }

   @Override
   public void println()
   {
      print(RETURN_CODE);
   }

   @Override
   public void println(boolean x)
   {
      print(x);
   }

   @Override
   public void println(char x)
   {
      print(x);
   }

   @Override
   public void println(int x)
   {
      print(x);
   }

   @Override
   public void println(long x)
   {
      print(x);
   }

   @Override
   public void println(float x)
   {
      print(x);
   }

   @Override
   public void println(double x)
   {
      print(x);
   }

   @Override
   public void println(char x[])
   {
      print(new String(x));
   }

   @Override
   public void println(String x)
   {
      print(x);
   }

   @Override
   public void println(Object x)
   {
      print(x);
   }

   static class DoNothingOutputStream extends OutputStream
   {
      /**
       * Does nothing
       */
      @Override
      public void write(int b) throws IOException
      {
         // do nothing
      }
   }
}
