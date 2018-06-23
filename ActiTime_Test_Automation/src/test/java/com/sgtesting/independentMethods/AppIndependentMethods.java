package com.sgtesting.independentMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.sgtesting.driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	
	/*
	 ***************************************
	 * Method Name		: getDateTime
	 * Purpose			: to get the system date & time in the required format
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public String getDateTime(String dtDateTime)
	{
		String dtDate;
		Date dt=null;
		try{
			dt=new Date();
			Calendar cal=Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat(dtDateTime);
			dtDate=sdf.format(dt);
			return dtDate;
		}catch(Exception e)
		{
			return null;
		}
	}
	
	
	/**************************************
	 * Method Name		: captureScreenShot
	 * Purpose			: to capture error screenshots upon failure
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	 public String captureScreenShot(WebDriver oDriver)
	 {
		 File srcFile;
		 String strDest=System.getProperty("user.dir")+"\\Results\\ErrorScreenShots\\"+System.getProperty("TestCaseID")+"\\Error_ScreenShot_"+appInd.getDateTime("ddMMYYYY_hhmmss")+".png";
		 try{
			 srcFile=((TakesScreenshot) oDriver).getScreenshotAs(OutputType.FILE);
			 FileUtils.copyFile(srcFile, new File(strDest));
			 return strDest;
		 }catch(Exception e)
		 {
			 return null;
		 }
	 }
	 
	 
	 /**************************************
	 * Method Name		: returnObjectDesc
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	 public String returnObjectDesc(String strObjectName)
	 {
		 try{
			 return oMap.get(strObjectName);
		 }catch(Exception e)
		 {
			return null;
		 }
	 }
	 
	 
	 /**************************************
	 * Method Name		: createAndGetObject
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public WebElement createAndGetObject(WebDriver oDriver, String strObjectName)
	{
		WebElement oEle=null;
		String strObj=appInd.returnObjectDesc(strObjectName);
		String arr[];
		try{
			arr=strObj.split("#");
			switch(arr[0].toLowerCase())
			{
				case "id":
					oEle=oDriver.findElement(By.id(arr[1]));
					break;
				case "name":
					oEle=oDriver.findElement(By.name(arr[1]));
					break;
				case "xpath":
					oEle=oDriver.findElement(By.xpath(arr[1]));
					break;
				case "cssselector":
					oEle=oDriver.findElement(By.cssSelector(arr[1]));
					break;
				case "classname":
					oEle=oDriver.findElement(By.className(arr[1]));
					break;
				default:
					oEle=null;
					datatable.WriteResult(oDriver, "Fail", "Locator type", "Invalid locator type was provided:"+arr[0]);
			}
			return oEle;
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'createAndGetObject' method", "Exception :"+e.getMessage());
			return null;
		}
	}
	 
	
	 /**************************************
	 * Method Name		: clickObject
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean clickObject(WebDriver oDriver, String strObjectName)
	{
		WebElement oEle=null;
		try{
			oEle=appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle!=null)
			{
				oEle.click();
				datatable.WriteResult(oDriver, "Pass", "click the element '"+strObjectName+"'", "Click was successful");
				return true;
			}else{
				datatable.WriteResult(oDriver, "Fail", "click the element '"+strObjectName+"'", "Object doesnot exist");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'clickObject' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name		: clickObject
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean clickObject(WebDriver oDriver, By objBy)
	{
		WebElement oEle=null;
		try{
			oEle=oDriver.findElement(objBy);
			if(oEle!=null)
			{
				oEle.click();
				datatable.WriteResult(oDriver, "Pass", "click the element '"+String.valueOf(objBy)+"'", "Click was successful");
				return true;
			}else{
				datatable.WriteResult(oDriver, "Fail", "click the element '"+String.valueOf(objBy)+"'", "Object doesnot exist");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'clickObject' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name		: EnterValue
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean EnterValue(WebDriver oDriver, String strObjectName, String strData)
	{
		WebElement oEle=null;
		try{
			oEle=appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle!=null)
			{
				oEle.sendKeys(strData);
				datatable.WriteResult(oDriver, "Pass", "Enter value '"+strData+"' in '"+strObjectName+"'", "The value was entered successful");
				return true;
			}else{
				datatable.WriteResult(oDriver, "Fail", "Enter value '"+strData+"' in '"+strObjectName+"'", "Failed to find the object");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'EnterValue' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/**************************************
	 * Method Name		: SelectValue
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean SelectValue(WebDriver oDriver, String strObjectName, String strData)
	{
		WebElement oEle=null;
		Select oSel=null;
		try{
			oEle=appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle!=null)
			{
				oSel=new Select(oEle);
				oSel.selectByVisibleText(strData);
				datatable.WriteResult(oDriver, "Pass", "select value '"+strData+"' in '"+strObjectName+"'", "The value was selected successful");
				return true;
			}else{
				datatable.WriteResult(oDriver, "Fail", "select value '"+strData+"' in '"+strObjectName+"'", "Failed to find the object");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'SelectValue' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name		: VerifyTextExist
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean VerifyTextExist(WebDriver oDriver, String strObjectName, String strExpected, String compareType)
	{
		WebElement oEle=null;
		Select oSel=null;
		String sText=null;
		String sStatus="";
		try{
			oEle=appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle!=null)
			{
				sText=oEle.getText();
				switch(compareType.toLowerCase())
				{
					case "full":
						if(sText.equalsIgnoreCase(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					case "partial":
						if(sText.contains(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					default:
						datatable.WriteResult(oDriver, "Fail", "Compare type value '"+compareType+"'", "Invalid compareType value");
						
				}
				
				if(sStatus.equals("true")) return true;
				else return false;
				
			}else{
				datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Failed to find the object");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'VerifyTextExist' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	public boolean VerifyValueExist(WebDriver oDriver, String strObjectName, String strExpected, String compareType)
	{
		WebElement oEle=null;
		Select oSel=null;
		String sText=null;
		String sStatus="";
		try{
			oEle=appInd.createAndGetObject(oDriver, strObjectName);
			if(oEle!=null)
			{
				sText=oEle.getAttribute("value");
				switch(compareType.toLowerCase())
				{
					case "full":
						if(sText.equalsIgnoreCase(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					case "partial":
						if(sText.contains(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					default:
						datatable.WriteResult(oDriver, "Fail", "Compare type value '"+compareType+"'", "Invalid compareType value");
						
				}
				
				if(sStatus.equals("true")) return true;
				else return false;
				
			}else{
				datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Failed to find the object");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'VerifyTextExist' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name		: VerifyTextExist
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean VerifyTextExist(WebDriver oDriver, By objBy, String strExpected, String compareType)
	{
		WebElement oEle=null;
		Select oSel=null;
		String sText=null;
		String sStatus="";
		try{
			oEle=oDriver.findElement(objBy);
			if(oEle!=null)
			{
				sText=oEle.getText();
				switch(compareType.toLowerCase())
				{
					case "full":
						if(sText.equalsIgnoreCase(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					case "partial":
						if(sText.contains(strExpected))
						{
							sStatus="true";
						}else{
							datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Both mis-matched");
							sStatus="false";
						}
						break;
					default:
						datatable.WriteResult(oDriver, "Fail", "Compare type value '"+compareType+"'", "Invalid compareType value");
						
				}
				
				if(sStatus.equals("true")) return true;
				else return false;
				
			}else{
				datatable.WriteResult(oDriver, "Fail", "Compare actual '"+sText+"' & expected '"+strExpected+"' value", "Failed to find the object");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'VerifyTextExist' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/**************************************
	 * Method Name		: compareValues
	 * Purpose			: compares actual & expected values
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean compareValues(WebDriver oDriver, String strActual, String strExpected)
	{
		try{
			if(strActual.trim().compareToIgnoreCase(strExpected.trim())==0)
			{
				datatable.WriteResult(oDriver, "Pass", "Compare actual '"+strActual+"' & Expected '"+strExpected+"' values", "Both matched");
				return true;
			}else{
				datatable.WriteResult(oDriver, "Fail", "Compare actual '"+strActual+"' & Expected '"+strExpected+"' values", "Both doesnot matched");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "executing 'compareValues' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/**************************************
	 * Method Name		: returnLocator
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public By returnLocator(WebDriver oDriver, String strObjectName)
	{
		By objBy=null;
		String strObj="";
		String arr[];
		try{
			strObj=appInd.returnObjectDesc(strObjectName);
			arr=strObj.split("#");
			switch(arr[0].toLowerCase())
			{
				case "id":
					objBy=By.id(arr[1]);
					break;
				case "name":
					objBy=By.name(arr[1]);
					break;
				case "xpath":
					objBy=By.xpath(arr[1]);
					break;
				case "classname":
					objBy=By.className(arr[1]);
					break;
				case "cssselector":
					objBy=By.cssSelector(arr[1]);
					break;
				default:
					datatable.WriteResult(oDriver, "Fail", "provide valid locator name", "The locator name '"+arr[0]+"' is invalid");
					return null;
			}
			return objBy;
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'returnLocator' method", "Exception is :"+e.getMessage());
			return null;
		}
	}
	
	
	/**************************************
	 * Method Name		: returnLocator
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public void readCSVFile(WebDriver oDriver, String sFileName)
	{
		BufferedReader br=null;
		String sArr[];
		try{
			br=new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\TestData\\CSVFiles\\"+sFileName+".csv"));
			String sLine;
			int row=0;
			int col=0;
			int r=0;
			while((sLine=br.readLine())!=null)
			{
				int c=0;
				col=0;
				sArr=sLine.split(",",-1);
				for(String str:sArr)
				{
					System.setProperty("R"+r+",C"+c, sArr[c]);
					if(System.getProperty("R"+r+",C"+c)==null)
					{
						System.setProperty("R"+r+",C"+c, "");
					}
					System.out.print(System.getProperty("R"+r+",C"+c));
					c++;
					col++;
				}
				System.out.println();
				r++;
				row++;
			}
			
			System.setProperty("Rows", String.valueOf(row));
			System.setProperty("Columns", String.valueOf(col));
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'readCSVFile' method", "Exception is :"+e.getMessage());
		}
	}
	
	
	
	/**************************************
	 * Method Name		: returnLocator
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean executeAutoIT(WebDriver oDriver, String strExeFile, String strCSVFile)
	{
		String sComm=null;
		String strExe=System.getProperty("user.dir")+"\\Library\\autoIT\\"+strExeFile+".exe";
		String strFile=System.getProperty("user.dir")+"\\TestData\\CSVFiles\\"+strCSVFile+".csv";
		try{
			strExeFile=
			sComm=strExe+" "+strFile;
			Runtime.getRuntime().exec(sComm);
			return true;
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'executeAutoIT' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/*
 	 * ********************************************************************
 	 * Method Name				: dateDifference(String dtStartDate, String dtEndDate)
 	 * Author							: 
 	 * Created Date					: 
 	 * Modified Date				:
 	 * Parameters					:dtStartDate, dtEndDate
 	 * Purpose							: To find the difference of the two given dates
 	 * ******************************************************************
 	 */
    public String dateDifference(String dtStartDate, String dtEndDate)
 	{
 		String dateDifference=null;
 		try
 		{
 		    SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
 		    Date d1 = null;
 		    Date d2 = null;
 		    try {
 		        d1 = format.parse(dtStartDate);
 		        d2 = format.parse(dtEndDate);
 		    } catch (ParseException e) {
 		        e.printStackTrace();
 		    }

 		    // Get msec from each, and subtract.
 		    long diff = d2.getTime() - d1.getTime();
 		    long diffSeconds = diff / 1000 % 60;
 		    long diffMinutes = diff / (60 * 1000) % 60;
 		    dateDifference=diffMinutes+" Min:"+diffSeconds+" Sec";
 			
 		}catch (Exception e)
 		{
 		}
 		return dateDifference;
 	}

}
