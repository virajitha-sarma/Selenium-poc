package com.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

// Before running this, please make sure to start docker container using below command:
// docker run -d -p 4445:4444 --shm-size="2g" -v /dev/shm:/dev/shm selenium/standalone-chrome:4.0.0-rc-1-20210902
public class DockerSeleniumTest {

  public static String remote_url_chrome = "http://localhost:4445/";

  public static void main(String[] args) {

    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");

    RemoteWebDriver driver = null;
    try {
      driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
      System.out.println("Session started");
      driver.manage().window().maximize();

      System.out.println("Navigating to url");
      driver.get("https://app.k8s.development.boreas.cloud");
      System.out.println("Waiting for page to load.");
      // This is added just to test the pdf generation. Will be changed later.
      Thread.sleep(10000);

      System.out.println("Printing PDF");
      Pdf pdf = driver.print(new PrintOptions());
      byte[] decodedBytes = Util.base64Decode(pdf.getContent());
      String fileName = "./test-" + UUID.randomUUID().toString() + ".pdf";
      System.out.printf("Writing to File: %s \n", fileName);
      Util.writeToFile(fileName, decodedBytes);

      System.out.println("Done");

    } catch (MalformedURLException | InterruptedException e) {
      System.err.println(e.getMessage());
    } finally {
      // We tried to disconnect the session using this. But, this seems to be not working.
      // We are looking for other ways to disconnect. Currently, this takes around 5 minutes to
      // close the session.
      if (driver != null) driver.close();
    }
  }
}
