package DownloadingFiles;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Chrome {
	
	public static void main(String[] args) throws InterruptedException {
		
		ChromeOptions options = new ChromeOptions();
		
		Map<String,Object> preferences = new HashMap<String,Object>();
		preferences.put("profile.default_content_settings.popups",0);
		String downloadPath = System.getProperty("user.dir")+"\\downloads";
		preferences.put("download.default_directory",downloadPath);
		
		options.setExperimentalOption("prefs", preferences);
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://omayo.blogspot.com/p/page7.html");
		
		driver.findElement(By.linkText("ZIP file")).click();
		
		Thread.sleep(5000);
		
		File file = new File(downloadPath+"\\DownloadDemo-master.zip");
		
		if(file.exists()) {
			
			System.out.println("ZIP file got successfully downloaded");
			
		}else {
			
			System.out.println("ZIP file download failed");
		}
		
		driver.quit();
		
	}

}