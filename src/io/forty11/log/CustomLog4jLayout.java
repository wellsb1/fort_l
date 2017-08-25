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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

import io.forty11.j.J;

public class CustomLog4jLayout extends Layout
{
   protected static final String   NEW_LINE           = System.getProperty("line.separator");
   protected static final String[] EMPTY_STRING_ARRAY = new String[0];

   protected boolean               showDate           = false;
   protected String                dateFormat         = "MM/dd/yy hh:mm:ss aaa | ";
   protected SimpleDateFormat      sdf                = null;

   /**
    * Formats a log using the following template:
    * time - level - thread id - logger name - message
    */
   @Override
   public String format(LoggingEvent record)
   {
      //if((record.getMessage() + "").equals("locked"))
      //   Thread.dumpStack();

      StringBuffer prefix = new StringBuffer();

      prefix.append(getDateStr());

      if (record.getLevel().toInt() == Priority.FATAL_INT)
      {
         prefix.append("FATAL: ");
      }
      else if (record.getLevel().toInt() == Priority.ERROR_INT)
      {
         prefix.append("ERROR: ");
      }
      else if (record.getLevel().toInt() == Priority.WARN_INT)
      {
         prefix.append("WARN : ");
      }
      else if (record.getLevel().toInt() == Priority.INFO_INT)
      {
         prefix.append("INFO : ");
      }
      else if (record.getLevel().toInt() == Priority.DEBUG_INT)
      {
         prefix.append("DEBUG: ");
      }
      else
      {
         prefix.append("TRACE: ");
      }

      String loggerName = record.getLoggerName();
      int index = loggerName.lastIndexOf('.');
      if (index > 0 && index < loggerName.length() - 1)
      {
         loggerName = loggerName.substring(index + 1, loggerName.length());
      }

      loggerName = getLoggerName(loggerName);

      loggerName += "                      ";
      loggerName = loggerName.substring(0, 20);

      //
      //      if (loggerName.startsWith("err"))
      //      {
      //         System.out.println("calling system.err....");
      //      }

      prefix.append(loggerName);//| ");      
      prefix.append(" ");

      StringBuffer message = new StringBuffer(record.getMessage() + "");
      if (record.getThrowableInformation() != null)
      {
         message.append(NEW_LINE);
         message.append(getStacktraceString(record.getThrowableInformation().getThrowable()));
      }

      String[] lines = splitLines(message.toString());

      StringBuffer buffer = new StringBuffer("");
      for (int i = 0; i < lines.length; i++)
      {
         buffer.append(prefix);
         if (i == 0)
         {
            buffer.append("|");
         }
         else
         {
            buffer.append("-");
         }

         buffer.append(lines[i]);
         buffer.append(NEW_LINE);
      }

      return buffer.toString();
   }

   public static String getLoggerName(String loggerName)
   {
      if (loggerName.startsWith("out") || loggerName.startsWith("err"))
      {
         // try to find the class that is calling System.out.println and append it to the loggername

         List v = J.getStackTraceLines(new Exception("CustomLog4jLayout sys.out class finder"));
         String theLine = null;
         for (Object o : v)
         {
            String line = (String) o;
            if (!line.contains("log") && !line.contains("Log") && !line.contains("Throwable.printStackTrace") && !line.contains("Appender"))
            {
               theLine = line;
               break;
            }
         }

         String theClass = null;
         String theMethod = null;
         if (theLine != null)
         {
            Class c = J.getClassFromStackLine(theLine);
            if (c != null)
            {
               theClass = c.getSimpleName();
            }

            theMethod = J.getMethodNameFromStackLine(theLine);
         }

         if (theClass != null)
         {
            if (theMethod != null)
            {
               theClass = theClass + "." + theMethod + "()";
            }

            loggerName += " - " + theClass;
         }
      }

      return loggerName;
   }

   public static String[] splitLines(String text)
   {
      try
      {
         if (text == null || "".equals(text))
         {
            return EMPTY_STRING_ARRAY;
         }

         String lineSeparator = text.indexOf(NEW_LINE) >= 0 ? NEW_LINE : "\n";
         return text.split(lineSeparator);
      }
      catch (Error ex)
      {
         System.err.println("Error splitting lines for: " + text);
         ex.printStackTrace();
      }

      return new String[]{text};
   }

   public static String getStacktraceString(Throwable stackTrace)
   {
      ByteArrayOutputStream baos = null;
      PrintWriter writer;

      baos = new ByteArrayOutputStream();
      writer = new PrintWriter(baos);

      boolean createNewTrace = false;

      if (stackTrace != null)
      {
         try
         {
            stackTrace.printStackTrace(writer);
         }
         catch (Exception e)
         {
            createNewTrace = true;
         }
      }
      else
      {
         createNewTrace = true;
      }

      if (createNewTrace)
      {
         try
         {
            throw new Exception("Unable to get original stacktrace.");
         }
         catch (Exception e)
         {
            e.printStackTrace(writer);
         }
      }

      writer.close();

      return new String(baos.toByteArray());

   }

   @Override
   public boolean ignoresThrowable()
   {
      return false;
   }

   @Override
   public void activateOptions()
   {
   }

   public String getDateStr()
   {
      if (isShowDate())
      {
         if (sdf == null)
         {
            sdf = new SimpleDateFormat(getDateFormat());
         }

         try
         {
            return sdf.format(new Date());
         }
         catch (RuntimeException e)
         {
         }
      }
      return "";
   }

   public boolean isShowDate()
   {
      return showDate;
   }

   public void setShowDate(boolean showDate)
   {
      this.showDate = showDate;
   }

   public String getDateFormat()
   {
      return dateFormat;
   }

   public void setDateFormat(String dateFormat)
   {
      this.dateFormat = dateFormat;
   }

}
