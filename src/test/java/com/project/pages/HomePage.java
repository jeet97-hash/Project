package com.project.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	public WebDriver driver;
	
	public HomePage(WebDriver driver){
		this.driver = driver;
	}
	
	public void searchItem(String item){
		//Enter the item to search
		driver.findElement(By.id("search")).sendKeys(item);
		driver.findElement(By.id("search_button")).click();
	}
	
	public String searchedItem(){
		//To check searched item
		return driver.findElement(By.xpath("//div[@id='search-results']//div//h2//span")).getText().replaceAll("'", "");
	}
	
	public ArrayList<String> collectionItems(){	
		//To write collection items
		driver.findElement(By.xpath("//*[@class='topnav_item collectionsunit']")).click();
		List<WebElement> subElements=driver.findElements(By.xpath("//*[@class='topnav_item collectionsunit']/div/div/ul/li[1]/ul/li"));
		ArrayList<String> itemNames=new ArrayList<String>();
		for(WebElement element:subElements)
		{
			String item=element.getAttribute("innerText").trim();
			itemNames.add(item);
		}
		return itemNames;
	}
	
	public void clickSearchButton(){
		//To click the search button
		driver.findElement(By.id("search")).sendKeys(Keys.ENTER);
	}
	
	public void giftCard(){
		//To click giftcard button
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//*[@class='featuredLinksBar__linkContainer']/li[4]/a")));
	}
	
	public void logOut(){
		//To logout the site
		driver.findElement(By.xpath("//span[@class=\"header-icon-link user-profile-icon\"]")).click();
		driver.findElement(By.xpath("//ul[@class=\"dropdown\"]/li[4]")).click();
	}
	
	public ArrayList<String> afterLogOut(){
		//To return logout status
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> afterLogoutElements = driver.findElements(By.xpath("//ul[@class=\"dropdown\"]/li"));
		ArrayList<String> stringElements = new ArrayList<String>();
		for(WebElement element : afterLogoutElements)
		{
			String item=element.getAttribute("innerText").trim();
		    stringElements.add(item);
		}
		return stringElements;
	}
	
}
