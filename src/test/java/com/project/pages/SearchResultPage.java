package com.project.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class SearchResultPage { //Give an implicit wait before calling each method
	
	public WebDriver driver;
	
	public SearchResultPage(WebDriver driver){
		this.driver = driver;
	}
	
	public void addCategoryFilter(String category){
		//To add category filter
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//li[@class=\"item\" and @data-group=\"category\"]")));
		
		String categoryXpath = "//label[@for='filters_primary_category_"+category.replaceAll(" ", "_")+"']";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath(categoryXpath)));
	}
	
	public void addStorageFilter(String choice){
		/*Read "choice" from Excel Sheet
		 *It should be same as the text displayed in web-page
		 *e.g: "Open" should be the entry in Excel for applying Open type filter*/
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//li[@class=\"item\" and @data-group=\"storage type\"]")));
		
		String xpath = "//input[@value='"+choice+"']";
		((JavascriptExecutor) driver).executeScript("arguments[0].click();",driver.findElement(By.xpath(xpath)));
	}
	
	public void setPriceRange(int upperLimit){
		//Read upperLimit from Excel Sheet
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//li[@class=\"item\" and @data-group=\"price\"]")))
		   .perform();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement slideBar = driver.findElement(
				By.xpath("//div[@class=\"range-slider noUi-target noUi-ltr noUi-horizontal noUi-background\"]"));
		
		Dimension dim = slideBar.getSize();
		int x = dim.getWidth();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement slideElement = driver.findElement(
				By.xpath("//div[@class=\"noUi-handle noUi-handle-upper\"]"));
		
		//Get min and max values of price range
		String min = driver.findElement(By.xpath("//span[@class=\"range-min\"]")).getText();
		String max = driver.findElement(By.xpath("//span[@class=\"range-max\"]")).getText();
		min = min.substring(1).replace(",", "");
		max = max.substring(1).replace(",", "");
		int range = Integer.parseInt(max)-Integer.parseInt(min);
		
		int toSlide = (15000*x)/range;
		toSlide+=3; //To make up for the lossy conversion from float to int
		act.clickAndHold(slideElement).moveByOffset(toSlide-x, 0).release().build().perform();
		
		//Move cursor from price filter to disable the visibility of slide bar
		act.moveToElement(driver.findElement(By.xpath("//input[@id=\"filters_availability_In_Stock_Only\"]")))
		   .perform();
	}
	
	public void outOfStockFilter(Boolean choice){
		//if want to Exclude out of stock, pass true as argument while method calling
		WebElement filter = driver.findElement(By.xpath("//input[@id=\"filters_availability_In_Stock_Only\"]"));
		if(choice)
			if(!filter.isSelected()) //if check-box is not selected in its normal state
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", filter);
		
		else
			if(filter.isSelected()) //if check-box is selected in its normal state and it's not desirable
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", filter);
	}
	
	public void sortingFilter(String choice){ 
		/*Read "choice" from Excel Sheet
		 *It should be same as the text displayed in web-page
		 *e.g: "Recommended" should be the entry in Excel for applying Recommended filter*/
		String xpath = "//li[contains(text(), '"+choice+"')]";
		WebElement sort = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", sort);
	}
	
	public ArrayList<String> getItemNames(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		//To return the product namelist
		List<WebElement> names= driver.findElements(By.xpath("//span[@class=\"name\" and @itemprop=\"name\"]"));
		ArrayList<String> nameList = new ArrayList<String>();
		for(WebElement element : names)
			nameList.add(element.getText());
		return nameList;
	}
	
	public ArrayList<String> getItemPrices(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		//To return product price list
		List<WebElement> prices=driver.findElements(By.xpath("//div[@class=\"price-number\"]/span"));
		ArrayList<String> priceList = new ArrayList<String>();
		for(WebElement element : prices)
			priceList.add(element.getText());
		return priceList;
	}

	public ArrayList<String> filteredCriteria()
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		//To return the selected filter criteria
		List<WebElement> filterCriteria=driver.findElements(By.xpath("//ul[@class='list']/li/span[1]"));
		ArrayList<String> filterList = new ArrayList<String>();
		for(WebElement filter : filterCriteria)
			filterList.add(filter.getText());
		return filterList;
	}
	
	public Boolean checkOutOfStock()
	{
		return driver.findElement(By.xpath("//div[@class='option']/input")).isSelected();
	}
	
	public String checkSortBy()
	{
		return driver.findElement(By.xpath("//div[@class='grouplist sort']/div/div[1]/span")).getText();
	}
	
}