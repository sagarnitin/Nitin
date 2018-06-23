package com.sgtesting.dependentMethods;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sgtesting.driver.DriverScript;

public class ActiTime_UtilityMethods extends DriverScript{

	/*******************************************
	 * MEthod Name		: launchApp
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public WebDriver launchApp(String strBrowser)
	{
		try{
			switch(strBrowser.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Library\\Drivers\\chromedriver.exe");
					oBrowser=new ChromeDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\Library\\Drivers\\IEDriverServer.exe");
					oBrowser=new InternetExplorerDriver();
					break;
				case "ff":
					System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Library\\Drivers\\geckodriver.exe");
					oBrowser=new FirefoxDriver();
					break;
				default:
					datatable.WriteResult(oBrowser, "Fail", "Specify the Browser name", "Invalid browser name was given :'"+strBrowser+"'");
			}
			if(oBrowser!=null){
				oBrowser.manage().window().maximize();
				return oBrowser;
			}
			else{
				datatable.WriteResult(oBrowser, "Fail", "Browser object should get initialize", "Failed to initialize browser object for :"+strBrowser);
				return null;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'launchApp' method", "Exception is :"+e.getMessage());
			return null;
		}
	}
	
	
	/*******************************************
	 * MEthod Name		: navigateURL
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean navigateURL(WebDriver oDriver)
	{
		try{
			oDriver.navigate().to(System.getProperty("URL"));
			oDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			if(appInd.compareValues(oDriver, oDriver.getCurrentUrl(), System.getProperty("URL"))) return true;
			else{
				datatable.WriteResult(oDriver, "Fail", "URL should navigated", "URL didn't navigated");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'navigateURL' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/*******************************************
	 * Method Name		: loginToApp
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean loginToApp(WebDriver oDriver, String strLogicalName)
	{
		boolean blnRes=datatable.getDataFromExcel(oDriver, strLogicalName, "loginToApp");
		String strSuccess="";
		try{
			if(blnRes)
			{
				strSuccess+=String.valueOf(appInd.EnterValue(oDriver, "obj_UserName_Edit", System.getProperty("UserName")));
				strSuccess+=String.valueOf(appInd.EnterValue(oDriver, "obj_Password_Edit", System.getProperty("Password")));
				strSuccess+=String.valueOf(appInd.clickObject(oDriver, "obj_Login_Button"));
				strSuccess+=String.valueOf(common.waitFor(oDriver, "obj_TimeTrack_Page_Name", "text", "Enter Time-Track", 20));
				Thread.sleep(2000);
				if(common.waitFor(oDriver, "obj_Shortcut_Div", "Element", "", 10))
				{
					strSuccess+=String.valueOf(appInd.clickObject(oDriver, "obj_Shortcut_Div"));
				}
				if(strSuccess.contains("false"))
				{
					datatable.WriteResult(oDriver, "Fail", "Login should be successful", "Failed to login with USerName :'"+System.getProperty("UserName")+"'");
					return false;
				}else{
					datatable.WriteResult(oDriver, "Pass", "Login should be successful", "Login was successful with USerName :'"+System.getProperty("UserName")+"'");
					return true;
				}
			}else{
				datatable.WriteResult(oDriver, "Fail", "Testdata should be available", "Failed to get the testdata for the logicalname:'"+strLogicalName+"'");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'navigateURL' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/*******************************************
	 * Method Name		: waitFor
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean waitFor(WebDriver oDriver, String strObjectName, String strWaitFor, String strText, int timeOut)
	{
		WebDriverWait oWait=null;
		WebElement oEle=null;
		
		try{
			
			oWait=new WebDriverWait(oDriver, timeOut);
			switch(strWaitFor.toLowerCase())
			{
				case "element":
					oWait.until(ExpectedConditions.presenceOfElementLocated(appInd.returnLocator(oDriver, strObjectName)));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(appInd.returnLocator(oDriver, strObjectName), strText));
					break;
				case "textvalue":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(appInd.returnLocator(oDriver, strObjectName), strText));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(appInd.returnLocator(oDriver, strObjectName)));
					break;
				default:
					datatable.WriteResult(oDriver, "Fail", "provide correct wait condition", "Invalid wait condition :'"+strWaitFor+"' was provided");
					return false;
			}
			return true;
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'waitFor' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/*******************************************
	 * Method Name		: waitFor
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean waitFor(WebDriver oDriver, By objBy, String strWaitFor, String strText, int timeOut)
	{
		WebDriverWait oWait=null;
		WebElement oEle=null;
		
		try{
			
			oWait=new WebDriverWait(oDriver, timeOut);
			switch(strWaitFor.toLowerCase())
			{
				case "element":
					oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, strText));
					break;
				case "textvalue":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, strText));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				default:
					datatable.WriteResult(oDriver, "Fail", "provide correct wait condition", "Invalid wait condition :'"+strWaitFor+"' was provided");
					return false;
			}
			return true;
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'waitFor' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/*******************************************
	 * Method Name		: loginToActiTime
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean logoutFromApp(WebDriver oDriver)
	{
		String strSuccess="";
		try{
			strSuccess+=String.valueOf(appInd.clickObject(oDriver, "obj_Logout_Button"));
			strSuccess+=String.valueOf(common.waitFor(oDriver, "obj_Logo_Img", "Element", "", 20));
			
			if(strSuccess.contains("false"))
			{
				datatable.WriteResult(oDriver, "Fail", "Logout should be successful", "Failed to logout from actiTime");
				return false;
			}else{
				datatable.WriteResult(oDriver, "Pass", "Logout should be successful", "logout from actiTime was successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'logoutFromApp' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/*******************************************
	 * Method Name		: loginToActiTime
	 * 
	 * 
	 * 
	 * ****************************************
	 */
	public boolean closeBrowser(WebDriver oDriver)
	{
		try{
			oDriver.close();
			oDriver=null;
			return true;
		}catch(Exception e)
		{
			System.out.println("Failed to close the browser");
			return false;
		}
	}
}
