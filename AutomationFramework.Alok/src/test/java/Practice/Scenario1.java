package Practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.util.Date;

public class Scenario1 {

	public static void main(String[] args) throws Throwable 
	{
		int sum = add(10,10);
		System.out.println(sum);
	
		Scenario1 p=new Scenario1();
		String value = p.property("Username");
		System.out.println(value);
		Scenario1 p1=new Scenario1();
		p1.Excel();
		
		Scenario1 p2=new Scenario1();
		String excel = p2.Excelfileutility("Sheet1", 2, 0);
		System.out.println(excel);
		
		Scenario1 p3=new Scenario1();
		String date = p3.getsystemdate();
		System.out.println(date);
		
		
		

	}
	public static int add(int a ,int b)
	{
		int c=a+b;
		return c;
	}
	public String property(String key) throws Throwable
	{
		// open the document in java readable format
		FileInputStream fis= new FileInputStream(".\\src\\test\\resources\\Propertydata.properties");
		// create the object of property class
		Properties pro=new Properties();
		//load the fis into property class
		pro.load(fis);
		String value = pro.getProperty(key);
		return value;
	}
	public void Excel() throws EncryptedDocumentException, IOException
	{
	
	FileInputStream fis= new FileInputStream("C:\\Users\\HP\\Downloads\\Chatbot scenarios (4).xlsx");

    // Create workbook from the file
    Workbook book = WorkbookFactory.create(fis);

    // Get the sheet by name or index
    Sheet sheet = book.getSheet("Sheet1");
     Row row = sheet.getRow(2);
     Cell cell = row.getCell(0);
     String value = cell.getStringCellValue();
     System.out.println(value);
    
	
	}
	public String Excelfileutility(String sheet1, int a, int b) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis1=new FileInputStream("C:\\\\Users\\\\HP\\\\Downloads\\\\Chatbot scenarios (4).xlsx");
		Workbook book1=WorkbookFactory.create(fis1);
		Sheet sheet = book1.getSheet(sheet1);
		Row row = sheet.getRow(a);
		Cell cell = row.getCell(b);
		String value = cell.getStringCellValue();
		return value ;
		
	}
	
	//Generic method of the java
	public String getsystemdate()
	{
	  Date date = new Date();
		return date.toString();
		
	}
	

}
