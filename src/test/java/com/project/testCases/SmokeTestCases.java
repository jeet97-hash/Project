package com.project.testCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.project.utilities.TakeScreenshot;
import com.project.utilities.WriteExcelSheet;

public class SmokeTestCases extends BaseClass {
	ExtentReports extent;
	ExtentTest logger;
	
	int testCaseRowNumber = 1;
	int testCaseColumnNumber = 7;
	
	@BeforeClass
	public void testReportSetup() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./TestReports/SmokeTestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}
		
	@Test(priority=0)
	public void openURL(){
		driver.get(properties.getConfigData("baseUrl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String expTitle="Furniture Online: Buy Home Wooden Furniture Online In India At Best Price - Urban Ladder - Urban Ladder";
		Assert.assertEquals(driver.getTitle(),expTitle);
	}
	
	@Test(priority=1)
	public void loginSite(){
		loginPage.logIn(properties.getConfigData("username"), properties.getConfigData("password"));
		String actualId=loginPage.checkLogIn();
		Assert.assertEquals(actualId, properties.getConfigData("username"));
	}
		
	@Test(priority=2 ,dependsOnMethods="loginSite")
	public void giftPageOperation(){
		driver.navigate().back();
		homePage.giftCard();
		giftCardPage.selectOccasion();
		giftCardPage.customizeGiftCard(excelSheet.getNumericData(0,7,1),
				excelSheet.getNumericData(0,8,1),excelSheet.getNumericData(0,9,1));
		giftCardPage.enterDetails(excelSheet.getStringData(0,10,1),excelSheet.getStringData(0,11,1),
				excelSheet.getStringData(0,12,1),excelSheet.getStringData(0,13,1),excelSheet.getStringData(0,14,1));
		String actualErrorMessage=giftCardPage.getErrorMessage();
		String regEx1="^[\\w\\d]+";
		String regEx2="^([\\w\\d]+)@";
		if(properties.getConfigData("browser").equalsIgnoreCase("chrome"))
		{
			if(excelSheet.getStringData(0,11,1).matches(regEx1))
				Assert.assertEquals(actualErrorMessage,"Please include an '@' in the email address. '"+excelSheet.getStringData(0,11,1)+"' is missing an '@'.");
			else if(excelSheet.getStringData(0,11,1).matches(regEx2))
				Assert.assertEquals(actualErrorMessage,"Please enter a part following '@'. '"+excelSheet.getStringData(0,11,1)+"' is incomplete.");
		}
		else if(properties.getConfigData("browser").equalsIgnoreCase("firefox"))
		{
			if(excelSheet.getStringData(0,11,1).matches(regEx1) || excelSheet.getStringData(0,11,1).matches(regEx2) )
				Assert.assertEquals(actualErrorMessage,"Please enter an email address.");
		}
	}
	
	@Test(priority=3,dependsOnMethods="loginSite")
	public void searchProduct(){
		driver.navigate().back();	
		homePage.searchItem(excelSheet.getStringData(0,1,1));
		String actItem=homePage.searchedItem();
		Assert.assertEquals(actItem.toLowerCase(), excelSheet.getStringData(0,1,1).toLowerCase());
	}
	
	@Test(priority=4,dependsOnMethods="searchProduct")
	public void advanceSearch(){
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,1));
		searchResultPage.addStorageFilter(excelSheet.getStringData(0,3,1));
		searchResultPage.setPriceRange(excelSheet.getNumericData(0,2,1));
		searchResultPage.outOfStockFilter(Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		searchResultPage.sortingFilter(excelSheet.getStringData(0,5,1));
		ArrayList<String> allNames = searchResultPage.getItemNames();
		ArrayList<String> allPrices = searchResultPage.getItemPrices();
		
		ArrayList<String> writeNames = new ArrayList<String>();
		ArrayList<String> writePrices = new ArrayList<String>();
		int itemCount=0, index=0;
		
		while(itemCount!=3 && index<allNames.size()) {
			if(!writePrices.contains(allPrices.get(index)))
				itemCount++;
			
			writeNames.add(allNames.get(index));
			writePrices.add(allPrices.get(index));
			
			index++;
		}
		WriteExcelSheet.writeFile("WriteItems.xlsx", 0, 1, 0, writeNames);
		WriteExcelSheet.writeFile("WriteItems.xlsx", 0, 1, 1, writePrices);
		
		ArrayList<String> filterList = searchResultPage.filteredCriteria();
		Assert.assertEquals(filterList.get(1), excelSheet.getStringData(0,15,1));
		Assert.assertEquals(filterList.get(2), excelSheet.getStringData(0,3,1));
		Assert.assertEquals(searchResultPage.checkSortBy(), excelSheet.getStringData(0,5,1));
		Assert.assertEquals(searchResultPage.checkOutOfStock(), Boolean.valueOf(excelSheet.getStringData(0,4,1)));
	}
	
	@Test(priority=5,dependsOnMethods="loginSite")
	public void logOutSite(){
		driver.navigate().back();
		homePage.logOut();
		ArrayList<String> logOutElements=homePage.afterLogOut();
		Assert.assertEquals(logOutElements.get(0), "Log In");
		Assert.assertEquals(logOutElements.get(1), "Sign Up");
	}
	
	@AfterMethod
	public void updateTestCaseStatusAndTakeScreenshot(ITestResult result) {
		//Create logger test
		String testName = result.getName().substring(0,1).toUpperCase()+result.getName().substring(1);
		logger = extent.createTest(testName);
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			WriteExcelSheet.writeFile("TestCaseStatus.xlsx", 0, testCaseRowNumber, testCaseColumnNumber, new ArrayList<String>(Arrays.asList("PASS")));
			
			String path = "./Screenshots/PassedTestCases/Screenshot_SmokeTest_"+properties.getConfigData("browser")+"_"+result.getName()+".png";
			TakeScreenshot.takeScreenshot(driver, path);
			
			//ForExtentReport
			logger.log(Status.PASS, result.getName()+" Method Passed");
			try {
				logger.pass(result.getName()+" Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (IOException e) {
				System.out.println("Cannot find specified Screenshot");
			}
		}
		
		if(result.getStatus()==ITestResult.FAILURE) {
			WriteExcelSheet.writeFile("TestCaseStatus.xlsx", 0, testCaseRowNumber, testCaseColumnNumber, new ArrayList<String>(Arrays.asList("FAIL")));
			
			String path = "./Screenshots/FailedTestCases/Screenshot_SmokeTest_"+properties.getConfigData("browser")+"_"+result.getName()+".png";
			TakeScreenshot.takeScreenshot(driver, path);
			
			//ForExtentReport
			logger.log(Status.FAIL, result.getName()+" Method Failed");
			try {
				logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(path).build());
			} catch (IOException e) {
				System.out.println("Cannot find specified Screenshot");
			}
		}
		if(result.getStatus()==ITestResult.SKIP){
			WriteExcelSheet.writeFile("TestCaseStatus.xlsx", 1, testCaseRowNumber, testCaseColumnNumber, new ArrayList<String>(Arrays.asList("SKIP")));
								
			//ForExtentReport
			logger.log(Status.SKIP, result.getName()+" Method Skipped");
		}
		testCaseRowNumber++;
	}
	
	@AfterClass
	public void displayCollectionAndFlushReport() {
		//Extent Report flush
		extent.flush();
		
		//Display collection items in console
		ArrayList<String> itemName=homePage.collectionItems();
		System.out.println("===============================================");
		System.out.println(">>Collection items:>> ");
		System.out.println("===============================================");
		for(int i=0;i<itemName.size();i++)
			System.out.println(">> "+itemName.get(i));
		System.out.println("===============================================");
	}
	
}