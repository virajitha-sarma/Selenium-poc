package com.selenium;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;

public class Testpdf {
	
	 public static void main(String[] args) {
	    	
	    	ChromeOptions options = new ChromeOptions();
	        options.addArguments("--headless");

		try {
			//Download Chromedriver according to the Chrome browser : https://chromedriver.chromium.org/downloads
			System.setProperty("webdriver.chrome.driver","D:\\sel-poc\\chromedriver_win32\\chromedriver.exe");
			ChromeDriver driver = new ChromeDriver(options);

			//Token should be the latest one
			  HashMap token = new HashMap() {{ put("source",
			  "window.localStorage.setItem('idp_tokens','{\"acme\":{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ0eXAiOiJCZWFyZXIiLCJhdWQiOiJhY2NvdW50IiwiZXhwIjoxNjMwNjc1Nzc3LCJpYXQiOjE2MzA2NzU0NzcsInVwbiI6InZpcmFqaXRoYS5zYXJtYUBsb2dyaHl0aG0uY29tIiwiaXNzIjoibG9ncmh5dGhtLWRldmVsb3BtZW50Iiwic3ViIjoiOWQ1ZDY3NzYtNTRlOS00NmNlLThkNzYtNjlhNzI4ZjRiZmQ5IiwidGVuYW50X2lkIjoiYWNtZSIsImNhcGFiaWxpdGllcyI6WyJCT1JFQVNfVVNFUiIsIk1BTkFHRV9ST0xFUyIsIk1BTkFHRV9OT1RJRklDQVRJT05TIiwiTUFOQUdFX0NPTExFQ1RPUlMiLCJFWEVDVVRFX1NFQVJDSCIsIlZJRVdfTkFWSUdBVElPTl9TVUdHRVNUSU9OUyIsIk1BTkFHRV9QQVNTV09SRF9QT0xJQ0lFUyIsIk1BTkFHRV9BVURJVCIsIk1BTkFHRV9DT05URU5UIiwiTUFOQUdFX0ZJTFRFUlMiLCJNQU5BR0VfVVNFUlMiLCJNQU5BR0VfVEVOQU5UX1NFVFRJTkdTIiwiTUFOQUdFX0VOVElUSUVTIl0sIm5hbWUiOiJWaXJhaml0aGEgU2FybWEiLCJnaXZlbl9uYW1lIjoiVmlyYWppdGhhIiwiZmFtaWx5X25hbWUiOiJTYXJtYSIsImVtYWlsIjoidmlyYWppdGhhLnNhcm1hQGxvZ3JoeXRobS5jb20iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ2aXJhaml0aGEuc2FybWFAbG9ncmh5dGhtLmNvbSIsImp0aSI6IjNiNmZiYTc4LTRhY2YtNGIwNS1hZWY5LTk1YzRhZDZiODBmMyJ9.fGIX4_5P2tIhaF-cxJ7gxK58HkT3dr6RNdgAsZV5s4y0pey8gP0wSNt_aBdgIzldQ4nVlEwdtFU7DZqcy8d40xOU8Se45l2Uh8Z6CijA4RXIjV7bQwV6Q4n50Z9gCFjoWVvRRq-lXHq1AIGlwxnJHiakEixfjtvb8fqarQU5sVmy9v2DAKoio02KLX9jTaTtu0Ali3HXCXsLX3RXY-PTXBfC9Vl8OiIwmioGvhEIDJC0xmzwzDimBgN8kbNFDgiNmnNw4ULwZatEU2jLnYTinZxLUrs-oQOsJMxk2WjIbstbhzdG6hQm0d2c1OFsDLYaJqZKO7JqGfhxUyjIHEcYsA\",\"tenantId\":\"acme\",\"capabilities\":[\"BOREAS_USER\",\"MANAGE_ROLES\",\"MANAGE_NOTIFICATIONS\",\"MANAGE_COLLECTORS\",\"EXECUTE_SEARCH\",\"VIEW_NAVIGATION_SUGGESTIONS\",\"MANAGE_PASSWORD_POLICIES\",\"MANAGE_AUDIT\",\"MANAGE_CONTENT\",\"MANAGE_FILTERS\",\"MANAGE_USERS\",\"MANAGE_TENANT_SETTINGS\",\"MANAGE_ENTITIES\"]}}')"
			  );
			  
			  }}; 			 

			  driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument",token);
		       
			  driver.navigate().to("https://app.k8s.development.boreas.cloud");
			//This needs to be looked for Pageload completion event	
			  Thread.sleep(20000);
			  //We will not be using Printoptions and use MFE printer-friendly css. PrintOptions is not having header/footer support
			Pdf p =  driver.print(new PrintOptions());
	        
			
	        File file = new File("D:/test.pdf");

	        try {
	        	
	        	FileOutputStream fos = new FileOutputStream(file); 
	          byte[] decoder = Base64.getDecoder().decode(p.getContent());

	          fos.write(decoder);
	          fos.close();
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }


}
