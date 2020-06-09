package com.project.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelSheet {
	public XSSFWorkbook workbook;
	
	public ReadExcelSheet(String path) {
		//To read excel file from specified path 
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			workbook = new XSSFWorkbook(fin);
		} catch (FileNotFoundException e) {
			System.out.println("Unable To Read Excel file: "+e.getMessage());
		}
		catch (IOException e) {
			System.out.println("Unable To Read Excel file: "+e.getMessage());
		}
	}
	
	public String getStringData(int sheetNumber, int row, int column) {
		return workbook.getSheetAt(sheetNumber).getRow(row).getCell(column).getStringCellValue();
	}
	
	public int getNumericData(int sheetNumber, int row, int column) {
		
		return (int) workbook.getSheetAt(sheetNumber).getRow(row).getCell(column).getNumericCellValue();
	}
}
