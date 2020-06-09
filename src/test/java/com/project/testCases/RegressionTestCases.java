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

public class RegressionTestCases extends BaseClass{
	ExtentReports extent;
	ExtentTest logger;
	
	int testCaseRowNumber = 1;
	int testCaseColumnNumber = 7;
	
	@BeforeClass
	public void testReportSetup() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./TestReports/RegressionTestReport.html");
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
	
	@Test(priority=2,dependsOnMethods="loginSite")
	public void checkGiftCard(){
		driver.navigate().back();
		homePage.giftCard();
		giftCardPage.selectOccasion();
		giftCardPage.customizeGiftCard(excelSheet.getNumericData(0,7,1),
				excelSheet.getNumericData(0,8,1),excelSheet.getNumericData(0,9,1));
		giftCardPage.enterDetails(excelSheet.getStringData(0,10,1),excelSheet.getStringData(0,11,2),
				excelSheet.getStringData(0,12,1),excelSheet.getStringData(0,13,1),excelSheet.getStringData(0,14,1));
		Assert.assertEquals(giftCardPage.getConfirmDetails(), "Confirm the details");
	}
	
	@Test(priority=3,dependsOnMethods="loginSite")
	public void searchProduct(){
		driver.navigate().back();	
		homePage.searchItem(excelSheet.getStringData(0,1,2));
		String actItem=homePage.searchedItem();
		Assert.assertEquals(actItem, excelSheet.getStringData(0,1,2));
	}
	
	@Test(priority=4,dependsOnMethods="loginSite")
	public void selectCategory(){
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,2));
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,1));
		searchResultPage.addStorageFilter(excelSheet.getStringData(0,3,1));
		searchResultPage.setPriceRange(excelSheet.getNumericData(0,2,1));
		searchResultPage.outOfStockFilter(Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		searchResultPage.sortingFilter(excelSheet.getStringData(0,5,1));
		
		ArrayList<String> filterList = searchResultPage.filteredCriteria();
		Assert.assertEquals(filterList.get(1), excelSheet.getStringData(0,15,1));
		Assert.assertEquals(filterList.get(2), excelSheet.getStringData(0,15,2));
		Assert.assertEquals(filterList.get(3), excelSheet.getStringData(0,3,1));
		Assert.assertEquals(searchResultPage.checkSortBy(), excelSheet.getStringData(0,5,1));
		Assert.assertEquals(searchResultPage.checkOutOfStock(), Boolean.valueOf(excelSheet.getStringData(0,4,1)));
	}
	
	@Test(priority=5,dependsOnMethods="loginSite")
	public void selectStorage(){
		homePage.clickSearchButton();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,1));
		searchResultPage.addStorageFilter(excelSheet.getStringData(0,3,2));
		searchResultPage.setPriceRange(excelSheet.getNumericData(0,2,1));
		searchResultPage.outOfStockFilter(Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		searchResultPage.sortingFilter(excelSheet.getStringData(0,5,1));
		
		ArrayList<String> filterList = searchResultPage.filteredCriteria();
		Assert.assertEquals(filterList.get(1), excelSheet.getStringData(0,15,1));
		Assert.assertEquals(filterList.get(2), excelSheet.getStringData(0,3,2));
		Assert.assertEquals(searchResultPage.checkSortBy(), excelSheet.getStringData(0,5,1));
		Assert.assertEquals(searchResultPage.checkOutOfStock(), Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		
	}
	
	@Test(priority=6,dependsOnMethods="loginSite")
	public void selectExcludeOutOfStock(){
		homePage.clickSearchButton();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,1));
		searchResultPage.addStorageFilter(excelSheet.getStringData(0,3,1));
		searchResultPage.setPriceRange(excelSheet.getNumericData(0,2,1));
		searchResultPage.outOfStockFilter(Boolean.valueOf(excelSheet.getStringData(0,4,2)));
		searchResultPage.sortingFilter(excelSheet.getStringData(0,5,1));
		
		ArrayList<String> filterList = searchResultPage.filteredCriteria();
		Assert.assertEquals(filterList.get(1), excelSheet.getStringData(0,15,1));
		Assert.assertEquals(filterList.get(2), excelSheet.getStringData(0,3,1));
		Assert.assertEquals(searchResultPage.checkSortBy(), excelSheet.getStringData(0,5,1));
		Assert.assertEquals(searchResultPage.checkOutOfStock(), Boolean.valueOf(excelSheet.getStringData(0,4,2)));
		
	}
	
	@Test(priority=7,dependsOnMethods="loginSite")
	public void selectSortBy(){
		homePage.clickSearchButton();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		searchResultPage.addCategoryFilter(excelSheet.getStringData(0,15,1));
		searchResultPage.addStorageFilter(excelSheet.getStringData(0,3,1));
		searchResultPage.setPriceRange(excelSheet.getNumericData(0,2,1));
		searchResultPage.outOfStockFilter(Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		searchResultPage.sortingFilter(excelSheet.getStringData(0,5,2));
		
		ArrayList<String> filterList = searchResultPage.filteredCriteria();
		Assert.assertEquals(filterList.get(1), excelSheet.getStringData(0,15,1));
		Assert.assertEquals(filterList.get(2), excelSheet.getStringData(0,3,1));
		Assert.assertEquals(searchResultPage.checkSortBy(), excelSheet.getStringData(0,5,2));
		Assert.assertEquals(searchResultPage.checkOutOfStock(), Boolean.valueOf(excelSheet.getStringData(0,4,1)));
		
	}
	
	@Test(priority=8,dependsOnMethods="loginSite")
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
			WriteExcelSheet.writeFile("TestCaseStatus.xlsx", 1, testCaseRowNumber, testCaseColumnNumber, new ArrayList<String>(Arrays.asList("PASS")));
					
			String path = "./Screenshots/PassedTestCases/Screenshot_RegressionTest_"+properties.getConfigData("browser")+"_"+result.getName()+".png";
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
			WriteExcelSheet.writeFile("TestCaseStatus.xlsx", 1, testCaseRowNumber, testCaseColumnNumber, new ArrayList<String>(Arrays.asList("FAIL")));
					
			String path = "./Screenshots/FailedTestCases/Screenshot_RegressionTest_"+properties.getConfigData("browser")+"_"+result.getName()+".png";
			TakeScreenshot.takeScreenshot(driver, path);
					
			//ForExtentReport
			logger.log(Status.FAIL, result.getName()+" Method Failed");
			try {
					logger.fail(result.getThrowable().getMessage()+" Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
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
	public void flushReport() {
		//Extent Report flush
		extent.flush();
	}
}