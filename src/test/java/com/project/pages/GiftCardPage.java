package com.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiftCardPage {

	public WebDriver driver;
	
	public GiftCardPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void selectOccasion(){ 
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Occasion webelement
		WebElement occasionButton=driver.findElement(By.xpath("//*[@class='_2sedU']/li[3]"));
		//Scroll down until the occasion webelement is visible
		JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", occasionButton);
		//Explicit wait for element to be clickable
		WebDriverWait wait1 = new WebDriverWait(driver, 10);
		wait1.until(ExpectedConditions.elementToBeClickable(occasionButton));
		//Click the occasion button
		occasionButton.click();
	}
	
	public void customizeGiftCard(int amount,int month,int date){
		//To enter the amount
		driver.findElement(By.id("ip_2251506436")).sendKeys(""+amount);
		//To select month
		String value=month+"/2020";
		Select dropDown=new Select(driver.findElement(By.xpath("//*[@class='_3PNvG']/select[1]")));
		dropDown.selectByValue(value);
		//To select date
		Select dropDwn=new Select(driver.findElement(By.xpath("//*[@class='_3PNvG']/select[2]")));
		dropDwn.selectByVisibleText(""+date);
		//To go to next page
		driver.findElement(By.xpath("//*[@class='_1IFIb _1fVSi action-button _1gIUf _1XfDi']")).click();
		
	}
	
	public void enterDetails(String recName,String recEmail,String cusName,String cusEmail,String phnNumber){
		//To enter recipient's name ,mail-id,customer's name,mail id and phone number.
		driver.findElement(By.name("recipient_name")).sendKeys(recName);
		driver.findElement(By.name("recipient_email")).sendKeys(recEmail);
		driver.findElement(By.name("customer_name")).sendKeys(cusName);
		driver.findElement(By.name("customer_email")).sendKeys(cusEmail);
		driver.findElement(By.name("customer_mobile_number")).sendKeys(""+phnNumber);
		driver.findElement(By.xpath("//*[@class='_3Hxyv _1fVSi action-button _1gIUf _1XfDi']")).click();
	}
	
	public String getErrorMessage(){
		//To return validation message
		WebElement element=driver.findElement(By.name("recipient_email"));
		return element.getAttribute("validationMessage");
	}

	public String getConfirmDetails(){
		//To check confirmation
		return driver.findElement(By.xpath("//div[@class='_2wEGI']/h2")).getText();
	}
}
