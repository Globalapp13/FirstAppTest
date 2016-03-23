package com.linkedInTest.ProjectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.linkedInTest.pages.BaseClass;

public class LoginPage extends BaseClass {
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void login(){
		waitForElementDisplayed(By.xpath("//*[@id='pagekey-uno-reg-guest-home']/div[1]/div/h1/img"));
		driver.findElement(By.id("login-email")).sendKeys("testsids5@gmail.com");
		driver.findElement(By.id("login-password")).sendKeys("testing123");
		driver.findElement(By.name("submit")).click();
		pause(10);
	}

}
