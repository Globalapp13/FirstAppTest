package com.linkedInTest.tests;

import org.testng.annotations.Test;

import com.linkedInTest.ProjectPage.LoginPage;
import com.linkedInTest.pages.TestCore;

public class LoginTest extends TestCore {
	@Test(priority=0)
	public void login()
	
		{
			LoginPage login = new LoginPage(driver);
			
			login.login();
		}

}
