package com.sgtesting.dependentMethods;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.sgtesting.driver.DriverScript;

public class TaskModuleMethods extends DriverScript{

	/**************************************
	 * Method Name		: importTask
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean importTask(WebDriver oDriver, String strLogicalName)
	{
		boolean blnRes= datatable.getDataFromExcel(oDriver, "Task_101", "importTask");
		String strStatus="";
		try{
			if(blnRes)
			{
				if(System.getProperty("ReadFile").equalsIgnoreCase("Yes"))
				{
					appInd.readCSVFile(oDriver, System.getProperty("CsvFileName"));
				}
				common.waitFor(oDriver, "obj_TASK_Menu", "Element", "", 10);
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_TASK_Menu"));
				strStatus+=String.valueOf(common.waitFor(oDriver, "obj_OpenTask_PageName", "Text", "Open Tasks", 10));
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ImportTasks_Button"));
				if(common.waitFor(oDriver, "obj_ImportableClick_Element", "Element", "", 10))
				{
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_ImportableClick_Element"));
					Thread.sleep(2000);
					strStatus+=String.valueOf(appInd.executeAutoIT(oDriver, System.getProperty("AutoITFileName"), System.getProperty("CsvFileName")));
					Thread.sleep(2000);
					
					if(System.getProperty("ReadFile").equalsIgnoreCase("Yes"))
					{
						strStatus+=String.valueOf(tasks.verifyTableData(oDriver));
					}
				
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CompleteImport_Btn"));
					Thread.sleep(2000);
					strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, "obj_ImportCompletionStatus_Label", "Import Completed", "Partial"));
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CloseTask_Btn"));
				}
			}else{
				datatable.WriteResult(oDriver, "Fail", "Testdata for importing the tasks", "Testdat '"+strLogicalName+"' not found for importing the tasks");
				return false;
			}
			
			if(strStatus.contains("false"))
			{
				datatable.WriteResult(oDriver, "Fail", "import the csv file", "Fail to import the csv file");
				return  false;
			}else{
				datatable.WriteResult(oDriver, "Pass", "import the csv file", "import of the csv file '"+System.getProperty("CsvFileName")+"' was successful");
				return  true;
			}
			
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'importTask' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/**************************************
	 * Method Name		: verifyTableData
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean verifyTableData(WebDriver oDriver)
	{
		String strStatus="";
		try{
			/*//VErify Column names
			List<WebElement> oHeader=oDriver.findElements(By.xpath("//table[@id='resultTableHeader']/thead/tr/td"));
			for(int i=0;i<Integer.parseInt(System.getProperty("Columns"))-1;i++)
			{
				WebElement strHeader=oDriver.findElement(By.xpath("//table[@id='resultTableHeader']/thead/tr/td["+(i+3)+"]"));
				strStatus+=String.valueOf(appInd.compareValues(oDriver, strHeader.getText(), System.getProperty("R0,C"+i)));
			}*/
			
			List<WebElement> oTableRows=oDriver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr[@class='taskRow correct']"));
			for(int i=0;i<Integer.parseInt(System.getProperty("Rows"))-1;i++)
			{
				List<WebElement> strCell=oDriver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr["+(i+2)+"]/td[@style]"));
				for(int j=0;j<strCell.size();j++)
				{
					WebElement strCellData=oDriver.findElement(By.xpath("//table[@id='resultTable']/tbody/tr["+(i+2)+"]/td["+(j+3)+"]"));
					strStatus+=String.valueOf(appInd.compareValues(oDriver, strCellData.getText(), System.getProperty("R"+(i+1)+",C"+j)));
				}
			}
			if(strStatus.contains("false"))
			{
				datatable.WriteResult(oDriver, "Fail", "Compare table data", "Invali data found after importing CSV file");
				return false;
			}else{
				datatable.WriteResult(oDriver, "Pass", "Compare table data", "Valid data was found after importing CSV file");
				return true;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'verifyTableData' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	/**************************************
	 * Method Name		: deleteTask
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean deleteTask(WebDriver oDriver)
	{
		String strStatus="";
		try{
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_All_Link"));
			Thread.sleep(1000);
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeleteTaSK_btn"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, By.xpath("//div[@class='orangeBlock']/span[1]"), "You are about to delete these tasks. Please confirm.", "Partial"));
			strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, By.xpath("//div[@class='orangeBlock']/span[2]"), "Warning: Task deletion cannot be undone.", "Partial"));
			strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeletePermanently_Btn"));
			Thread.sleep(2000);
			strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, "obj_SuccessMsg_Label", "Selected tasks have been successfully deleted.", "Partial"));
			
			if(strStatus.contains("false"))
			{
				datatable.WriteResult(oDriver, "Fail", "delete the tasks", "failed to delete the task");
				return false;
			}else{
				datatable.WriteResult(oDriver, "Pass", "delete the tasks", "The task was deleetd successful");
				return true;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'deleteTask' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
}
