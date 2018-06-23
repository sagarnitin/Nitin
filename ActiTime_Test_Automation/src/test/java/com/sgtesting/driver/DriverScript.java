package com.sgtesting.driver;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.sgtesting.datatable.Datatable;
import com.sgtesting.dependentMethods.ActiTime_UtilityMethods;
import com.sgtesting.dependentMethods.TaskModuleMethods;
import com.sgtesting.dependentMethods.UserModuleMethods;
import com.sgtesting.independentMethods.AppIndependentMethods;
import com.sgtesting.independentMethods.ReportsUtility;
import com.sgtesting.testScripts.TaskModuleScripts;
import com.sgtesting.testScripts.UserModuleScripts;

public class DriverScript {
	public static WebDriver oBrowser=null;
	public static String strFile=null;
	public static AppIndependentMethods appInd=null;
	public static Datatable datatable=null;
	public static Map<String,String> oMap=null;
	public static TaskModuleMethods tasks=null;
	public static UserModuleMethods users=null;
	public static ActiTime_UtilityMethods common=null;
	public static UserModuleScripts userScripts=null;
	public static TaskModuleScripts taskScripts=null;
	public static ReportsUtility reports=null;
	public static int tcCount=0;
	public static int tcPass=0;
	public static int tcFail=0;
	public static int tcSkip=0;
	
	@BeforeSuite
	public void preRequisite()
	{
		FileInputStream fin=null;
		Properties prop=null;
		try{
			appInd=new AppIndependentMethods();
			datatable=new Datatable();
			oMap=datatable.getObjectDetails();
			tasks=new TaskModuleMethods();
			users=new UserModuleMethods();
			common=new ActiTime_UtilityMethods();
			strFile=System.getProperty("user.dir")+"\\ExecutionController\\Controller.xlsx";
			datatable.CreateResultFile();
			reports=new ReportsUtility();
			
			//read config.properties file
			fin=new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\Config.properties");
			prop=new Properties();
			prop.load(fin);
			System.setProperty("URL", prop.getProperty("URL"));
			System.setProperty("BrowserName", prop.getProperty("BrowserName"));
			
			//Reports utility
			String ReportFileName=System.getProperty("user.dir")+"\\Results\\HTMLReports\\actiTime_Automation_Results.html";
			String teststarttime=appInd.getDateTime("dd/MM/yyyy hh:mm:ss a");
			reports.CreateReport(ReportFileName, "ActiTime", System.getProperty("URL"), teststarttime);
		}catch(Exception e)
		{
			System.out.println("Failed to run the 'preRequisite' method in the driverScript.java");
		}finally{
			try{
				fin.close();
				fin=null;
				prop=null;
			}catch(Exception e)
			{
				
			}
		}
	}
	
	@Test
	public void ExecuteTests()
	{
		String startTime=null;
		String endTime=null;
		reports.startSuite();
		try{
			int Proj_rows=datatable.getRowCount(strFile, "Projects");
			for(int i=0;i<Proj_rows;i++)
			{
				int intPass=0;
				int intFail=0;
				String strExecute1=datatable.getCellData(strFile, "Projects", i+2, "ExecuteTest");
				if(strExecute1.equalsIgnoreCase("Yes"))
				{
					String strProject=datatable.getCellData(strFile, "Projects", i+2, "ProjectName");
					int rows=datatable.getRowCount(strFile, strProject);
					for(int j=0;j<rows;j++)
					{
						String strExecute2=datatable.getCellData(strFile, strProject, j+2, "ExecuteTest");
						if(strExecute2.equalsIgnoreCase("Yes"))
						{
							String strModule=datatable.getCellData(strFile, strProject, j+2, "ModuleName");
							System.setProperty("ModuleName", strModule);
							int rows2=datatable.getRowCount(strFile, strModule);
							for(int k=0;k<rows2;k++)
							{
								tcCount+=1;
								String status="";
								System.setProperty("TestCaseID", datatable.getCellData(strFile, strModule, k+2, "TestCaseID"));
								System.setProperty("ProjectName", datatable.getCellData(strFile, strModule, k+2, "ProjectName"));
								System.setProperty("ScriptName", datatable.getCellData(strFile, strModule, k+2, "ScriptName"));
								System.setProperty("ClassName", datatable.getCellData(strFile, strModule, k+2, "ClassName"));
								System.setProperty("ExecuteTest", datatable.getCellData(strFile, strModule, k+2, "ExecuteTest"));
								
								if(System.getProperty("ExecuteTest").equalsIgnoreCase("Yes"))
								{
									startTime=appInd.getDateTime("dd/MM/yyyy hh:mm:ss");
									Class cls=Class.forName(System.getProperty("ClassName"));
									Object obj=cls.newInstance();
									Method method=obj.getClass().getMethod(System.getProperty("ScriptName"));
									Object Runstatus=method.invoke(obj);
									String strStatus=String.valueOf(Runstatus);
									if(strStatus.equalsIgnoreCase("true"))
									{
										datatable.setCellData(strFile, strModule, "Status", k+2, "Passed");
										status="Pass";
										tcPass+=1;
										intPass++;
									}else{
										datatable.setCellData(strFile, strModule, "Status", k+2, "Failed");
										status="Fail";
										tcFail+=1;
										intFail++;
									}
									endTime=appInd.getDateTime("dd/MM/yyyy hh:mm:ss");
									String totlaTime=appInd.dateDifference(startTime, endTime);
									reports.addTestCase(System.getProperty("TestCaseID"), System.getProperty("ModuleName"), System.getProperty("ScriptName"), System.getProperty("BrowserName"), status, startTime, endTime);
								}
							}
						}
					}
				}
			}
			reports.endSuite();
		}catch(Exception e)
		{
			System.out.println("Failed to run the 'ExecuteTests' method in the driverScript.java");
		}
	}
	
	
	@AfterSuite
	public void postRequisite()
	{
		reports.endSuite();
		String endtime=appInd.getDateTime("dd/MM/yyyy hh:mm:ss a");
		reports.updateEndTime(endtime);
	}
}
