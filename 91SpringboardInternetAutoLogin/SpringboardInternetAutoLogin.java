import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SpringboardInternetAutoLogin
{
	public static void main(String args[])
	{
		isInternetAvailable();
		System.out.println(isInternetAvailable());
		
		if(!isInternetAvailable())
		{
			try {
				portalAutoLogin();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isInternetAvailable()
	{
		StringBuffer html = null;
		try {
	
			String url = "http://portal.91springboard.com";
	
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(5000);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "google.com");
	
				
			BufferedReader in = new BufferedReader(
		                              new InputStreamReader(conn.getInputStream()));
			String inputLine;
			html = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) 
			{
				html.append(inputLine);
			}
			in.close();
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }

		if (html.indexOf("91Springboard Captive Portal - Login") > -1)
		{
			//System.out.println("internet not on");
			return false;
		}
		else
		{
			//System.out.println("internet on");
			return true;
		}

		//System.out.println("URL Content... \n" + html.toString());
		//System.out.println("Done");
	}
	
	public static void portalAutoLogin() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Utils\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://portal.91springboard.com/login");
		driver.manage().window().maximize();
		driver.switchTo().frame("captive-portal");
		driver.findElement(By.id("emailField")).sendKeys("dummy@test.com");
		driver.findElement(By.id("passwordField")).sendKeys("dummy.", Keys.ENTER);
	}
}