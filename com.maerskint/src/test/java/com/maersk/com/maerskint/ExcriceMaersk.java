package com.maersk.com.maerskint;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import junit.framework.Assert;  
public class ExcriceMaersk {  
	private static Properties properties;
	private final static String propertyFilePath= "configs//Configuration.properties";
	@Test
	public  void verifyConfirmationId() throws InterruptedException
	{
		String path = System.getProperty("user.dir"); 
		System.out.println("user.dir is" + path);
		String driverpath = path + "\\chromedriver.exe";  
		System.setProperty("webdriver.chrome.driver",driverpath);  
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
		WebDriver driver=new ChromeDriver();  
		String url = ("https://blazedemo.com/");        
		driver.get(url);  
		Thread.sleep(2000);
		String str1=properties.getProperty("userid");
		driver.findElement(By.xpath("//select[contains(@name,'fromPort')]")).click();
		String fromcity=properties.getProperty("fromcity");
		driver.findElement(By.xpath("//select[contains(@name,'fromPort')]/option[contains(@value,'"+fromcity+"')]")).click();
		Thread.sleep(1000);
		String tocity=properties.getProperty("tocity");
		driver.findElement(By.xpath("//select[contains(@name,'toPort')]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//select[contains(@name,'toPort')]/option[contains(@value,'"+tocity+"')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[contains(@value,'Find Flights')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[contains(@value,'Choose This Flight')])[1]")).click();
		Thread.sleep(2000);
		WebElement scrolldown=driver.findElement(By.xpath("//div[contains(@class,'control-group')]/label[text()='Name on Card']"));
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);",scrolldown);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[contains(@value,'Purchase Flight')]")).click();
		String ConfirmationidActual =  driver.findElement(By.xpath("//div[contains(@class,'container hero-unit')]/table/tbody/tr/td[2]")).getText();
		String ConfirmationIdExpected = properties.getProperty("ExpectedConfirmationid");
		System.out.println("ID is"+ ConfirmationidActual);
		// Verifying the confirmation against the id given by user
		Assert.assertEquals(ConfirmationidActual, ConfirmationIdExpected);
		Thread.sleep(1000);
		driver.close();
	}  
}  