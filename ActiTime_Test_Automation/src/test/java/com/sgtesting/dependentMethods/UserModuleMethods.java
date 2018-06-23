package com.sgtesting.dependentMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sgtesting.driver.DriverScript;

public class UserModuleMethods extends DriverScript{

	/**************************************
	 * Method Name		: createUser
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean createUser(WebDriver oDriver, String strLogicalName)
	{
		boolean blnRes = datatable.getDataFromExcel(oDriver, "user_1001", "createUser");
		String strStatus="";
		try{
			if(blnRes)
			{
				common.waitFor(oDriver, "obj_User_Tab", "Element", "", 10);
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_User_Tab"));
				strStatus+=String.valueOf(common.waitFor(oDriver, "obj_User_Page_Name", "Text", "User List", 10));
				strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_AddUser_Button"));
				if(common.waitFor(oDriver, "obj_UserTitle_Label", "Text", "Add User", 10))
				{
					System.setProperty("RT_FirstName",System.getProperty("FirstName")+appInd.getDateTime("ddMMyy_hhmmss"));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_FirstName_Edit", System.getProperty("RT_FirstName")));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_LastName_Edit", System.getProperty("LastName")));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_Email_Edit", System.getProperty("Email")));
					System.setProperty("RT_U_UserName",System.getProperty("U_UserName")+appInd.getDateTime("ddMMyy_hhmmss"));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_UN_Edit", System.getProperty("RT_U_UserName")));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_PWD_Edit", System.getProperty("U_Password")));
					strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_ReTypePwd_Edit", System.getProperty("RetypePassword")));
					strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, "obj_UserName_Label", System.getProperty("LastName")+", "+System.getProperty("RT_FirstName"), "Full"));
					
					if(System.getProperty("TimeZone").equalsIgnoreCase("New"))
					{
						strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_TimeZone_List"));
						strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_NewTimeZone_Link"));
						strStatus+=String.valueOf(common.waitFor(oDriver, "obj_SelectedTimeZone_Edit", "Element", "", 10));
						strStatus+=String.valueOf(appInd.EnterValue(oDriver, "obj_SelectedTimeZone_Edit", System.getProperty("TimeZoneValue")));
						strStatus+=String.valueOf(common.waitFor(oDriver, "obj_GetSelectedZone_Link", "Element", "", 10));
						strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_GetSelectedZone_Link"));
						strStatus+=String.valueOf(common.waitFor(oDriver, "obj_NewTimeZone_Edit", "Element", "", 10));
						String sZoneName=appInd.createAndGetObject(oDriver, "obj_NewTimeZone_Edit").getAttribute("value");
						strStatus+=String.valueOf(appInd.compareValues(oDriver, sZoneName, System.getProperty("TimeZoneValue")));
					}
					
					//HireDate
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_HireDate_Button"));
					strStatus+=String.valueOf(common.waitFor(oDriver, "obj_HireDate_Span", "Element", "", 10));
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_HireDate_Span"));
					
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_CreateUser_Button"));
					strStatus+=String.valueOf(common.waitFor(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+System.getProperty("LastName")+", "+System.getProperty("RT_FirstName")+"'"+"]"), "Element", "", 10));
					strStatus+=String.valueOf(appInd.VerifyTextExist(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+System.getProperty("LastName")+", "+System.getProperty("RT_FirstName")+"'"+"]"), System.getProperty("LastName")+", "+System.getProperty("RT_FirstName"), "Full"));
					
					if(strStatus.contains("false"))
					{
						datatable.WriteResult(oDriver, "Fail", "USer should be created successful", "Failed to create the user '"+System.getProperty("FirstName"));
						return false;
					}else{
						datatable.WriteResult(oDriver, "Pass", "USer should be created successful", "user '"+System.getProperty("FirstName")+"'  created successful");
						return true;
					}
				}else{
					datatable.WriteResult(oDriver, "Fail", "'Add User' page should open", "Failed to open 'Add User' page");
					return false;
				}
			}else{
				datatable.WriteResult(oDriver, "Fail", "Testdata for creating the User", "Testdat '"+strLogicalName+"' not found for creating the user");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'createUser' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
	
	
	
	/**************************************
	 * Method Name		: createUser
	 * Purpose			: 
	 * Author			: xyz
	 * reviewed By		:
	 * Date Created 	:
	 * Date Modified 	:
	 * ************************************
	 */
	public boolean deleteUser(WebDriver oDriver, String strLogicalName, boolean blnRunTime)
	{
		boolean blnRes= datatable.getDataFromExcel(oDriver, "Delete_1001", "deleteUser");
				String strStatus="";
		try{
			if(blnRes)
			{
				if(blnRunTime)
				{
					System.setProperty("First_Name", System.getProperty("RT_FirstName"));
					System.setProperty("User_Name", System.getProperty("RT_U_UserName"));
				}
				strStatus+=String.valueOf(common.waitFor(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+System.getProperty("Last_Name")+", "+System.getProperty("First_Name")+"'"+"]"), "Element", "", 10));
				strStatus+=String.valueOf(appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+System.getProperty("Last_Name")+", "+System.getProperty("First_Name")+"'"+"]")));
				strStatus+=String.valueOf(common.waitFor(oDriver, "obj_UserName_Label", "Text", System.getProperty("Last_Name")+", "+System.getProperty("First_Name"), 10));
				strStatus+=String.valueOf(appInd.VerifyValueExist(oDriver, "obj_FirstName_Edit", System.getProperty("First_Name"), "Full"));
				strStatus+=String.valueOf(appInd.VerifyValueExist(oDriver, "obj_LastName_Edit", System.getProperty("Last_Name"), "Full"));
				strStatus+=String.valueOf(appInd.VerifyValueExist(oDriver, "obj_Email_Edit", System.getProperty("eMail_ID"), "Full"));
				strStatus+=String.valueOf(appInd.VerifyValueExist(oDriver, "obj_UN_Edit", System.getProperty("User_Name"), "Full"));
				
				if(strStatus.contains("false"))
				{
					datatable.WriteResult(oDriver, "Fail", "Dele the user", "Wrong user is selected. Hence can't be deleted");
					return false;
				}else{
					strStatus+=String.valueOf(appInd.clickObject(oDriver, "obj_DeleteUser_Btn"));
					Thread.sleep(2000);
					oDriver.switchTo().alert().accept();
					strStatus+=String.valueOf(common.waitFor(oDriver, By.xpath("//div[@class='name']/span[text()="+"'"+System.getProperty("Last_Name")+", "+System.getProperty("First_Name")+"'"+"]"), "invisibility", "", 10));
				}
				
				if(strStatus.contains("false"))
				{
					datatable.WriteResult(oDriver, "Fail", "Delete user", "Failed to delete the user");
					return false;
				}else{
					datatable.WriteResult(oDriver, "Pass", "Delete user", "The user was deleted successful");
					return true;
				}
			}else{
				datatable.WriteResult(oDriver, "Fail", "Testdata for deleting the User", "Testdat '"+strLogicalName+"' not found for deleting the user");
				return false;
			}
		}catch(Exception e)
		{
			datatable.WriteResult(oDriver, "Fail", "Executing 'deleteUser' method", "Exception is :"+e.getMessage());
			return false;
		}
	}
}
