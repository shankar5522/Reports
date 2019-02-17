package com.at.report.impl;

import org.automationtesting.excelreport.Xl;

public class MyATReport {

	/*
	 * reference to read concept
	 * http://automationtesting.in/generate-excel-report-in-selenium-using-testng/
	 * 
	 * link to get the jar file
	 * https://drive.google.com/open?id=0B3MavAHOpQakYlZwZGpqdzRqNXM
	 * 
	 * Steps to get the excel
	 * 
	 * After running test suite just create Simple java class like this and then run
	 * only this class as Java Application and wait for console to Print like
	 * "Excel Report Generated".
	 * 
	 * After that goto testng default output folder to find out generated .excel
	 * file
	 * 
	 * referesh the folder to get the excel file
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// Xl.generateReport("MY_AT_Report.xlsx"); // generates in default folder
			// location where testng suite output
			// file is stored i,e test-output

			Xl.generateReport(System.getProperty("user.dir") + "/AT_Report", "MY_AT_Report.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
