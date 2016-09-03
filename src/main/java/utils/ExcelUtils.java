package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

//import java.io.FileOutputStream;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

//	private static HSSFSheet ExcelWSheet;
//	private static HSSFWorkbook ExcelWBook;
//	private static HSSFCell Cell;
//	private static HSSFRow Row;
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	private static String path;

	// This method is to set the File path and to open the Excel file, Pass
	// Excel Path and Sheet name as Arguments to this method
	public static void setExcelFile(String Path, String SheetName) throws Exception {

		path = Path;
		try {
			// Opens the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);

			// Accesses the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	// This method is to read the test data from the Excel cell, in this we are
	// passing parameters as Row number and Column number
	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}

	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters;
	public static synchronized void setCellData(String Result, int RowNum, int ColNum) throws Exception {

		try {
			// String cellData = getCellData(RowNum, ColNum);
			Row = ExcelWSheet.getRow(RowNum);
			if (Row == null) {
				Row = ExcelWSheet.createRow(RowNum);
				Row = ExcelWSheet.getRow(RowNum);
				Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (Cell == null) {
					Cell = Row.createCell(ColNum);
					Cell.setCellValue(Result);
				} else {
					// Cell.setCellValue(cellData + "\n" + Result);
					Cell.setCellValue(Result);
				}
			} else {
				Row = ExcelWSheet.getRow(RowNum);
				Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
				if (Cell == null) {
					Cell = Row.createCell(ColNum);
					Cell.setCellValue(Result);
				} else {
					// Cell.setCellValue(cellData + "\n" + Result);
					Cell.setCellValue(Result);
				}
			}

			// Write Data in file
			FileOutputStream fileOut = new FileOutputStream(path);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}

	}

}

