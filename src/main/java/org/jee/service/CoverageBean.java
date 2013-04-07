package org.jee.service;

import javax.ejb.Stateless;


/**
 * This bean is explicitly mentioned in the "includes" of jacoco configuration
 * in the the arquillian.xml and therefore is instrumented with code coverage.
 * 
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
@Stateless
public class CoverageBean 
{
   public void test(Boolean value) 
   {
      String test = "test";
      if(value)
      {
         if(test.length() == 4)
         {
            long start = System.currentTimeMillis();
            test = String.valueOf(start);
         }
      } 
      else
      {
         long start = System.currentTimeMillis();
         test = String.valueOf(start);
      }
   }
}
