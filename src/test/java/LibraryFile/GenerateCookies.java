package LibraryFile;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import LibraryFile.ReusableMethodsClass;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;	

public class GenerateCookies implements ConstantVaribales {
	
	public static Cookie generateCookiesAfterLogin()
	{
	Cookie cookies = null;
	WebDriver driver = null;
	Properties prp;
	try
	{
		prp = ReusableMethodsClass.getPropertyFile();
		//System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver_2.29_win32\\chromedriver.exe");
		driver = new HtmlUnitDriver();
		
		driver.get(prp.getProperty("QA_ENV"));
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(By.xpath("//*[@id='ctl00_PageContentPlaceholder_UserNameInput']")).sendKeys(prp.getProperty("USERNAME"));
		driver.findElement(By.xpath("//*[@id='ctl00_PageContentPlaceholder_PasswordInput']")).sendKeys(prp.getProperty("PASSWORD"));
		driver.findElement(By.xpath("//*[@id='ctl00_PageContentPlaceholder_LoginButton']")).click();
		Thread.sleep(3000);
		cookies = driver.manage().getCookieNamed(COOKIES_NAME);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	finally
	{
		driver.close();
	}
		return cookies;
		
	}
}
