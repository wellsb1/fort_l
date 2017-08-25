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

import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
 * @author wells.burke
 */
public class ConsoleAppender extends org.apache.log4j.ConsoleAppender
{
   protected static PrintStream out = System.out;
   protected static PrintStream err = System.err;

   @Override
   public void activateOptions()
   {
      if (target.equals(SYSTEM_OUT))
      {
         setWriter(new OutputStreamWriter(out));
      }
      else
      {
         setWriter(new OutputStreamWriter(err));
      }
   }

   /**
    * @return Returns the err.
    */
   public static PrintStream getErr()
   {
      return err;
   }

   /**
    * @param err The err to set.
    */
   public static void setErr(PrintStream errStream)
   {
      err = errStream;
   }

   /**
    * @return Returns the out.
    */
   public static PrintStream getOut()
   {
      return out;
   }

   /**
    * @param out The out to set.
    */
   public static void setOut(PrintStream outStream)
   {
      out = outStream;
   }
}
