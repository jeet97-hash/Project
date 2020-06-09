package com.project.testCases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.project.pages.GiftCardPage;
import com.project.pages.HomePage;
import com.project.pages.LoginPage;
import com.project.pages.SearchResultPage;
import com.project.utilities.DriverSetup;
import com.project.utilities.ReadExcelSheet;
import com.project.utilities.ReadPropertiesFile;

public class BaseClass {
	
	public WebDriver driver;
	public ReadExcelSheet excelSheet;
	public ReadPropertiesFile properties;
	
	public SearchResultPage searchResultPage;
	public LoginPage loginPage;
	public HomePage homePage;
	public GiftCardPage giftCardPage;
		
	@BeforeSuite(alwaysRun=true)
	public void setupSuit(){
		excelSheet = new ReadExcelSheet("./ExcelFiles/ReadItems.xlsx");
		properties = new ReadPropertiesFile("./ConfigurationFile/Config.properties");
	}
	
	@BeforeTest(alwaysRun=true)
	public void setupTest(){
		try {
			if(properties.getConfigData("browser").equalsIgnoreCase("chrome"))
				driver = DriverSetup.ChromeDriver();
			if(properties.getConfigData("browser").equalsIgnoreCase("firefox"))
				driver = DriverSetup.FirefoxDriver();
			if(properties.getConfigData("browser").equalsIgnoreCase("edge"))
				driver = DriverSetup.EdgeDriver();
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		//POM Setup
		searchResultPage = new SearchResultPage(driver);
		loginPage=new LoginPage(driver);
		homePage=new HomePage(driver);
		giftCardPage=new GiftCardPage(driver);
	}
	
	@AfterTest(alwaysRun=true)
	public void endTest() {
		driver.quit();
	}
	
	
}
