package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.project.utilities.ReadPropertiesFile;

public class LoginPage {

	public WebDriver driver;
	public static ReadPropertiesFile properties;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
	}
	
	public void logIn(String username,String password){
		//Explicit wait for element to be clickable
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='login-link link-color']")));
		//To open the login page from signup page
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//*[@class='login-link link-color']")));		
		//To enter the username
		driver.findElement(By.xpath("//*[@class='email required input_authentication']")).sendKeys(username);
		//To enter the password
		driver.findElement(By.xpath("//*[@class='required input_authentication']")).sendKeys(password);
		//To click login button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//*[@name='commit' and @value='Log In']")));
	}
		
	public String checkLogIn(){
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//To click the profile icon button
		driver.findElement(By.xpath("//span[@class='header-icon-link user-profile-icon']")).click();
		//Explicit wait for element to avoid interception of two elements
		WebDriverWait wait =new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.invisibilityOfElementLocated((By.xpath("//*[@class='login-message'"))));
		//Explicit wait for element to be clickable
		wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//span[@class='header-icon-link user-profile-icon']/ul/li[1]"))));
		driver.findElement(By.xpath("//span[@class='header-icon-link user-profile-icon']/ul/li[1]")).click();
		//To return email id
		return driver.findElement(By.xpath("//*[@id='info']/tbody[1]/tr[2]/td[2]")).getText();
	}
}
