package com.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteWebStorage;

// Before running this, please make sure to start docker container using below command:
// docker run -d -p 4445:4444 --shm-size="2g" -v /dev/shm:/dev/shm
// selenium/standalone-chrome:4.0.0-rc-1-20210902
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

      // To Access local storage
      RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
      RemoteWebStorage webStorage = new RemoteWebStorage(executeMethod);
      LocalStorage storage = webStorage.getLocalStorage();

      System.out.println("Navigating to url");
      driver.get("https://app.k8s.development.boreas.cloud");
      storage.setItem(
          "idp_tokens",
          "{\"acme\":{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ0eXAiOiJCZWFyZXIiLCJhdWQiOiJhY2NvdW50IiwiZXhwIjoxNjMxMDAxNjk5LCJpYXQiOjE2MzEwMDEzOTksInVwbiI6InN1cmluZGVyLnJhanBhbEBsb2dyaHl0aG0uY29tIiwiaXNzIjoibG9ncmh5dGhtLWRldmVsb3BtZW50Iiwic3ViIjoiMDQyYjg2M2ItNzEyMy00ZWQ5LWI2ZjQtYmMzZDc5NTk5MmQzIiwidGVuYW50X2lkIjoiYWNtZSIsImNhcGFiaWxpdGllcyI6WyJWSUVXX05BVklHQVRJT05fU1VHR0VTVElPTlMiLCJNQU5BR0VfRklMVEVSUyIsIkVYRUNVVEVfU0VBUkNIIiwiTUFOQUdFX0FVRElUIiwiTUFOQUdFX1BBU1NXT1JEX1BPTElDSUVTIiwiTUFOQUdFX1VTRVJTIiwiTUFOQUdFX1RFTkFOVF9TRVRUSU5HUyIsIk1BTkFHRV9DT05URU5UIiwiTUFOQUdFX05PVElGSUNBVElPTlMiLCJNQU5BR0VfQ09MTEVDVE9SUyIsIkJPUkVBU19VU0VSIiwiTUFOQUdFX0VOVElUSUVTIiwiTUFOQUdFX1JPTEVTIl0sIm5hbWUiOiJTdXJpbmRlciBSYWpwYWwgUmFqcGFsIiwiZ2l2ZW5fbmFtZSI6IlN1cmluZGVyIFJhanBhbCIsImZhbWlseV9uYW1lIjoiUmFqcGFsIiwiZW1haWwiOiJzdXJpbmRlci5yYWpwYWxAbG9ncmh5dGhtLmNvbSIsInByZWZlcnJlZF91c2VybmFtZSI6InN1cmluZGVyLnJhanBhbEBsb2dyaHl0aG0uY29tIiwianRpIjoiNzNlMTU0MzUtNjM2ZS00ZmFmLTk3MTAtZTRkMTY3YTE3ZjFkIn0.BoeP-c3C2sfyRGN2FAG-aBl35_BaJZgql_9CwfZv4T6RS0-Wei_gajuzYLvHotU3L6iIScHykd9HwmffspaHTpvS8d_TsNBQDRlwyj-3AFDo5BXr9sx5Fak7UXkG1Ogw2ThOPXUu1n_0bW_pRPTxM_YUxFYByaFCUDbhM0s-_F4lD5AzVpunvwsab-GEUwrcv_0zwLusM7fICHW7Y-r7xQkUsrXswSOlDwW9co5s71-Xzq2KIqH96vpvlsFwkEBLcwFWu7ytedAxXq28NyHH2GDU_nCJCmnSppmoZsdYJTJ4wLwxxcDOjs7sqZ_0FclhMQPCPxQzzXhxCNIqz4YhWg\",\"tenantId\":\"acme\",\"capabilities\":[\"VIEW_NAVIGATION_SUGGESTIONS\",\"MANAGE_FILTERS\",\"EXECUTE_SEARCH\",\"MANAGE_AUDIT\",\"MANAGE_PASSWORD_POLICIES\",\"MANAGE_USERS\",\"MANAGE_TENANT_SETTINGS\",\"MANAGE_CONTENT\",\"MANAGE_NOTIFICATIONS\",\"MANAGE_COLLECTORS\",\"BOREAS_USER\",\"MANAGE_ENTITIES\",\"MANAGE_ROLES\"]}}");
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
      if (driver != null) driver.quit();
    }
  }
}
