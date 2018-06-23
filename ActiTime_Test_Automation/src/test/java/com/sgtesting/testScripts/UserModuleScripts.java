package com.sgtesting.testScripts;

import org.testng.Assert;

import com.sgtesting.driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	
	/******************************
	 * Test Script NAme	: Login_CR_101
	 * test case id	:	Tc_101
	 * 
	 * *******************************
	 */
	public boolean TC_Login_Logout()
	{
		boolean blnRes=false;
		try{
			oBrowser=common.launchApp(System.getProperty("BrowserName"));
			Assert.assertNotNull(oBrowser);
			Assert.assertTrue(common.navigateURL(oBrowser));
			Assert.assertTrue(common.loginToApp(oBrowser, "User_1001"));
			Assert.assertTrue(common.logoutFromApp(oBrowser));
			Assert.assertTrue(common.closeBrowser(oBrowser));
			blnRes=true;
			return blnRes;
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'TC_Login_Logout' test script", "Exception is :"+e.getMessage());
			return blnRes;
		}
	}
	
	
	/******************************
	 * Test Script NAme	: TC_Create_DeleteUser
	 * test case id	:	Tc_102
	 * 
	 * *******************************
	 */
	public boolean TC_Create_DeleteUser()
	{
		boolean blnRes=false;
		try{
			oBrowser=common.launchApp(System.getProperty("BrowserName"));
			Assert.assertNotNull(oBrowser);
			Assert.assertTrue(common.navigateURL(oBrowser));
			Assert.assertTrue(common.loginToApp(oBrowser, "User_1002"));
			Assert.assertTrue(users.createUser(oBrowser, "user_1001"));
			Assert.assertTrue(users.deleteUser(oBrowser, "Delete_1001", true));
			Assert.assertTrue(common.logoutFromApp(oBrowser));
			Assert.assertTrue(common.closeBrowser(oBrowser));
			blnRes=true;
			return blnRes;
		}catch(Exception e)
		{
			datatable.WriteResult(oBrowser, "Fail", "Executing 'TC_Login_Logout' test script", "Exception is :"+e.getMessage());
			return blnRes;
		}
	}
}
