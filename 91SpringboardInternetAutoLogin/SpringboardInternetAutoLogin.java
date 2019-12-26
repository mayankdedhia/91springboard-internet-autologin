import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class SpringboardInternetAutoLogin
{
	public static void main(String args[])
	{
		TimerTask task = new TimerTask() 
		{
			@Override
			public void run() 
			{
				boolean internetAvailable = false;
				internetAvailable = isInternetAvailable();
				if(!internetAvailable)
				{
					try 
					{
						portalAutoLogin();
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Failed to connect to internet");
					}
				}
				else
				{
					System.out.println("Internet is Working");
				}
			}
		};

		Timer timer = new Timer();
		long delay = 0;
		long intevalPeriod = 15 * 1000; 

		// schedules the task to be run in an interval 
		timer.scheduleAtFixedRate(task, delay,
				intevalPeriod);

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

		if (html == null || html.indexOf("91Springboard Captive Portal - Login") > -1 || html.indexOf("Error 302: Hotspot login required") > -1 )
		{
			//System.out.println("internet not on");
			return false;
		}
		else
		{
			//System.out.println("internet on");
			return true;
		}
	}

	public static void portalAutoLogin() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Utils\\chromedriver.exe");
		
		ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        
		WebDriver driver = new ChromeDriver(chromeOptions);
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
}