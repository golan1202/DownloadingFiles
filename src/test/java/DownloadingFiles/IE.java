
package DownloadingFiles;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class IE {
	
	public static void main(String[] args) throws HttpException, IOException, InterruptedException {
		
		WebDriverManager.iedriver().setup();
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.get("http://omayo.blogspot.com/p/page7.html");
		
		WebElement downloadLink = driver.findElement(By.linkText("ZIP file"));
		String fileToDownloadAbsoluteLocation = downloadLink.getAttribute("href");
		URL fileToDownload = new URL(fileToDownloadAbsoluteLocation);
		String hostURL = fileToDownload.getHost();
		int port = fileToDownload.getPort();
		
		HttpClient hClient = new HttpClient();
		hClient.getParams().setCookiePolicy(CookiePolicy.RFC_2965);
		HostConfiguration hostConfig = new HostConfiguration();
		hostConfig.setHost(hostURL,port);
		Set<org.openqa.selenium.Cookie> cookies = driver.manage().getCookies();
		HttpState cookieState = new HttpState();
		for (org.openqa.selenium.Cookie seleniumCookie : cookies) {
            Cookie httpClientCookie = new Cookie(seleniumCookie.getDomain(), seleniumCookie.getName(), seleniumCookie.getValue(), seleniumCookie.getPath(), seleniumCookie.getExpiry(), seleniumCookie.isSecure());
            cookieState.addCookie(httpClientCookie);
        }
		HttpMethod getFileRequest = new GetMethod(fileToDownloadAbsoluteLocation);
		boolean followRedirects = true;
		getFileRequest.setFollowRedirects(followRedirects);
		hClient.executeMethod(getFileRequest);		
		String downloadPath = System.getProperty("user.dir")+"\\downloads";
		File downloadedFile = new File(downloadPath+"\\DownloadDemo-master.zip");
		FileUtils.copyInputStreamToFile(getFileRequest.getResponseBodyAsStream(),downloadedFile);
		Thread.sleep(5000);
		getFileRequest.releaseConnection();
		
				
		if(downloadedFile.exists()) {
			
			System.out.println("ZIP file got successfully downloaded");
			
		}else {
			
			System.out.println("ZIP file download failed");
		}
		
		driver.quit();
		
	}

}
