package com.project.utilities;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshot {
	public static void takeScreenshot(WebDriver driver, String path) {
		//To take screenshot and saved in specified folder
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {
			System.out.println("Screenshot could not be taken: "+e.getMessage());
		}
	}
}

