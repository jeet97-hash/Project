package com.project.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelSheet {

	public static void writeFile(String fileName, int sheetNumber,int rowIndex, int columnIndex, ArrayList<String>items) {
		File src = new File(".//ExcelFiles/"+fileName);
		try {
			FileInputStream fin = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(fin);
			XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
			
			//If Excel sheet is empty
			if(sheet.getLastRowNum()==0)
				for(int i=0;i<items.size();i++)
					sheet.createRow(rowIndex+i).createCell(columnIndex).setCellValue(items.get(i));
			
			//If Data already present in Excel sheet
			else
				for(int i=0;i<items.size();i++)
					sheet.getRow(rowIndex+i).createCell(columnIndex).setCellValue(items.get(i));
			
			FileOutputStream fout = new FileOutputStream(src);
			workbook.write(fout);
			workbook.close();
			fout.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to Write to Excel File: "+e.getMessage());
		} catch (IOException e) {
			System.out.println("Unable to Write to Excel File: "+e.getMessage());
		}
	}
}
