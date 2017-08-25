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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.helpers.FileWatchdog;
import org.apache.log4j.xml.DOMConfigurator;
import org.w3c.dom.Document;

import io.forty11.j.J;

/**
 * This class will attempt to configure Log4J as soon 
 * as it is statically loaded.  You should
 * use this class by simply calling Class.forName
 * to load it applications you want to use it
 * 
 * @author Wells Burke
 */
public class Logs
{
   private static PrintStream _splitOut = null;
   private static PrintStream _splitErr = null;

   private static PrintStream _out      = System.out;
   private static PrintStream _err      = System.err;

   static
   {

//      try
//      {
//         _out = System.out;
//         _err = System.err;
//
//         //if this is not set there will be a recursive 
//         //log->screen->log->screen loop
//         ConsoleAppender.setOut(_out);
//         ConsoleAppender.setErr(_err);
//
//         File f = new File("log-config.xml");
//         if (!f.exists())
//         {
//            f = new File("src/log-config.xml");
//         }
//         if (!f.exists())
//         {
//            f = new File("WEB-INF/log-config.xml");
//         }
//         if (!f.exists())
//         {
//            f = new File("war/WEB-INF/log-config.xml");
//         }
//         if (f.exists())
//         {
//            System.out.println("logging config: " + f.getCanonicalPath());
//            CustomXmlWatchdog loggingWatchDog = new CustomXmlWatchdog(f.getAbsolutePath());
//            loggingWatchDog.start();
//         }
//         else
//         {
//            try
//            {
//               InputStream stream = Logs.class.getResourceAsStream("/log-config.xml");
//               Document doc = J.loadXml(stream);
//               DOMConfigurator.configure(doc.getDocumentElement());
//            }
//            catch (Exception ex)
//            {
//               //System.err.println("unable to load logging configuration file log-config.xml");
//               //ex.printStackTrace();
//            }
//
//         }
//
//         //log all system.out at level INFO
//         LoggingPrintStream logOut = new LoggingPrintStream(LogFactory.getLog("out"), LoggingPrintStream.INFO);
//
//         //log all system.err at level ERROR
//         LoggingPrintStream logErr = new LoggingPrintStream(LogFactory.getLog("err"), LoggingPrintStream.WARN);
//         //
//         System.setOut(logOut);
//         System.setErr(logErr);
//      }
//      catch (Exception e)
//      {
//         e.printStackTrace();
//      }
   }

   static class CustomXmlWatchdog extends FileWatchdog
   {
      protected CustomXmlWatchdog(String filename)
      {
         super(filename);
         this.setName("Main_Logging_Config_Watchdog");
         this.setPriority(Thread.MIN_PRIORITY);
         this.setDelay(60000); // 1 mins
         this.setDaemon(true);
      }

      @Override
      protected void doOnChange()
      {
         try
         {
            Document doc = J.loadXml(new FileInputStream(filename));
            DOMConfigurator.configure(doc.getDocumentElement());
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }

}
