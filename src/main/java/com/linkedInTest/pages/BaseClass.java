package com.linkedInTest.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Reporter;

public class BaseClass {
	
	protected WebDriver driver;
	
	public BaseClass(WebDriver driver)
	{
		this.driver = driver;
		
	}
	
	public static void log(String msg, String logLevel) {
		  
		  if(msg.endsWith(".")) {
		   
		   msg=msg.substring(0, msg.length()-1) + "|" ; 
		  
		  } else {
		   
		   msg=msg + "|"; 
		   
		  }
		  
		  System.out.println( IValues.SPACES_THREE + "|" + msg );
		  
		  
		  if(logLevel==ILogLevel.PAGE){
		   
		   Reporter.log("<br/><b><font color='DarkRed'>" + msg + "</font></b>");
		   
		  } else if(logLevel==ILogLevel.TEST){
		   
		   Reporter.log("<br/>" + IValues.HTML_SPACE + "<font color='Green'>" + msg + "</font>");
		   
		  } else if(logLevel==ILogLevel.METHOD){ // add 3 space indentation
		   
		   Reporter.log("<br/>" + IValues.HTML_SPACE_THREE + "<font color='Green'>" + msg + "</font>" );
		  
		  } else if(logLevel == ILogLevel.QUESTION){
		   
		   Reporter.log("<br/>" + IValues.HTML_SPACE + "<i><font color='Yellow'>" + msg + "</font></i>");
		   
		  } else if(logLevel == ILogLevel.TO_DO_STEPS){
		   
		   Reporter.log("<br/>" + IValues.HTML_SPACE + "TODO: <i><font color='Megenta'>" + msg + "</font></i>");
		   
		  } else if(logLevel == ILogLevel.ASSERTS){
		   
		   Reporter.log("<br/><b>" + IValues.HTML_SPACE + "CHECKPOINT: <font color='Green'>" + msg + "</font></b>");
		   
		  } else if(logLevel == ILogLevel.TESTCASE){
		   
		   Reporter.log("<br/> <b>TESTCASE:<font color='Green'>" + msg + "</font></b>");
		   
		  } else if(logLevel == ILogLevel.PRE_CONDITION){
		   
		   Reporter.log("<br/> PRECONDITION:<b><font color='Megenta'>[" + msg + "]</font></b>");
		   
		  } else if(logLevel == ILogLevel.BUG){
		   
		   Reporter.log("<br/><font color='Red' style='background-color: yellow;'><b> BUG: [" + msg + "]</font> </b>");
		   
		  } else if(logLevel == ILogLevel.MANUAL_TESTING_NOTE){
		   
		   Reporter.log("<br/><font color='Black' style='background-color: yellow;'><b> Note For Manual Testers: </b>[" + msg + "]</font>");
		  
		  } else if(logLevel == ILogLevel.BUG_GIT_HUB_LINK){
		   
		   msg = msg.replace("|", IValues.SPACE ) ;
		   Reporter.log("<br/><font color='Blue' style='background-color: yellow;'><b> ISSUE_Link:  "
		     + "<a href='"+ msg + "'>"
		       + "[" + msg + "]"
		         + " </a></font> </b>");
		   //throw new Error("Force-fail-manually. Check Bug description and Issue link in log");
		  } else if(logLevel == ILogLevel.ERROR ){
		   
		   Reporter.log("<br/> <b> <font color='Red' style='background-color: white;'> [" + msg + "]</font> </b>");
		  
		  } else if(logLevel == ILogLevel.WARNING ){
		   
		   Reporter.log("<br/> <b> <font color='Yellow' style='background-color: gray;'> [" + msg + "]</font> </b>");
		  
		  } else if(logLevel == ILogLevel.FAILURE ){
		   
		   Reporter.log("<br/> <b> <font color='Red' size='3' > [" + msg + "]</font> </b>");
		  
		  }  
		  
		 }
	
	public final boolean waitForElementDisplayed(By by) {
		  for(int sec=1; sec<=100; sec++){
		   try{
		    if ( driver.findElement(by).isDisplayed() ) {
		     Thread.sleep(1000);
		     return true;
		    }
		   }catch(Exception e){}
		   try{
		   Thread.sleep(1000);
		   }catch(Exception e){}
		  }
		  analyzeBrowserLogs();
		  return false;
		 }
	
	public final boolean waitForElementDisplayed(WebElement _ele) {
		  for(int sec=1; sec<=100; sec++){
		   try{
		    if ( _ele.isDisplayed() ) {
		     Thread.sleep(1000);
		     return true;
		    }
		   }catch(Exception e){}
		   try{
		   Thread.sleep(1000);
		   }catch(Exception e){}
		  }
		  analyzeBrowserLogs();
		  return false;
		 }
	
	public void analyzeBrowserLogs() {
		  
        LogEntries logEntries = getWebDriver().manage().logs().get(LogType.BROWSER);
        int error_counter = 0 ;
        List<String> logs = new ArrayList<String>();
        for (LogEntry entry : logEntries) {
         
         if (  entry.getLevel().toString().contains( "WARNING" ) ) {
          
          logs.add( getWarningLog( "WARNING[BROWSER]:" + entry.getMessage()  ) );
         
         } else if ( entry.getLevel().toString().contains( "ERROR" ) ) {
          
          logs.add( getErrorLog( "ERROR[BROWSER]:" + entry.getMessage() ) );
          error_counter ++ ;
         
         }
         //System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
	}
	
	public final WebDriver getWebDriver() {
		  return driver;
		 }
	
	public final String getErrorLog( String errorMessage  ){
		  return "<br/> <b> <font color='Red' style='background-color: white;'> [" + errorMessage + "]</font> </b>" ;
		 }
		 
		 public final String getWarningLog( String warningMessage ){
		  return "<br/> <b> <font color='Yellow' style='background-color: gray;'> [" + warningMessage + "]</font> </b>" ;
		 }
	
		 public final void pause(int seconds){
				pauseMilis(seconds*500);  
			}
		 
		 public final void pauseMilis(long miliSeconds){
				try { 
					Thread.sleep(miliSeconds); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		 /**
			 * generate a rundom number of given length and returns it
			 * 
			 * @param length
			 * @return
			 */
			public String AutogenerateNumber(int length){
				return RandomStringUtils.randomNumeric(length);
			}
			
			public boolean isElementPresent(By by) {
			    try {
			       
					driver.findElement(by);
			        return true;
			    } catch (NoSuchElementException e) {
			        return false;
			    }
			}
			
			public void waitForAlert()
			{
			   int i=0;
			   while(i++<10)
			   {
			        try
			        {
			            driver.switchTo().alert();
			            break;
			        }
			        catch(NoAlertPresentException e)
			        {
			          pause(1);
			          continue;
			        }
			   }
			}

			
			/**
			 * Returns true if alert is present
			 * else returns false
			 * 
			 * @return
			 */
			public boolean isAlertPresent(){
				pauseMilis(500);
				try{
					driver.switchTo().alert();
					return true;
				} catch(NoAlertPresentException nep){
					return false;
				}
			}

			/**
			 * Get alert text
			 * 
			 * @return
			 */
			public String getAlertText(){
				pauseMilis(1000);
				return driver.switchTo().alert().getText();
			}

			/**
			 * Accepts alert.
			 */
			public void acceptAlert(){
				pause(1);
				driver.switchTo().alert().accept();
			}

			/**
			 * Dismiss alert
			 */
			public void dismissAlert(){
				pause(1);
				driver.switchTo().alert().dismiss();
			}
			
			
}
