import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SpringboardInternetAutoLogin
{
	public static void main(String args[])
	{
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try 
				{
					boolean internetAvailable = false;
					internetAvailable = isInternetAvailable();
					if(!internetAvailable)
					{					
						System.out.println("Internet is not working or needs to be connected to");
						portalAutoLogin();
					}
					else
					{
						System.out.println("Internet is Working");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Failed to run the complete task");
				}
			}
		}, 15, 15, TimeUnit.SECONDS);
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
			System.out.println("Unable check internet status");
			e.printStackTrace();
		}

		if (html == null || html.indexOf("91Springboard Captive Portal - Login") > -1 || html.indexOf("Error 302: Hotspot login required") > -1 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static void portalAutoLogin() throws InterruptedException
	{
		WebDriver driver = null;
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Utils\\chromedriver.exe");
			
			ChromeOptions chromeOptions = new ChromeOptions();
	        chromeOptions.addArguments("--headless");
	        
			driver = new ChromeDriver(chromeOptions);
			driver.get("http://portal.91springboard.com/login");
			driver.manage().window().maximize();
			driver.switchTo().frame("captive-portal");
			driver.findElement(By.id("emailField")).sendKeys("dummy@test.com");
			driver.findElement(By.id("passwordField")).sendKeys("dummy.");
			
			driver.findElements(By.cssSelector(".form-submit .submit-button")).get(0).click();
			Thread.sleep(5000);
			driver.close();
			System.out.println("Yehhhhhhhhh.....!!");
			System.out.println("Ho gaya LogIn");
		}
		catch (Exception e) {
			System.out.println("Unable to login via browser");
			e.printStackTrace();
		}
		finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}
}