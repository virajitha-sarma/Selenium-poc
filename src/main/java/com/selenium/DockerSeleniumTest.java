package com.selenium;

import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v93.network.Network;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandPayload;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.html5.RemoteWebStorage;

// Before running this, please make sure to start docker container using below command:
// docker run -d -p 4445:4444 --shm-size="2g" -v /dev/shm:/dev/shm
// selenium/standalone-chrome:4.0.0-rc-1-20210902
public class DockerSeleniumTest implements Runnable {

  public static String remote_url_chrome = "http://localhost:4444/";

  public static void main(String[] args) {
    ExecutorService executorService =
        new ThreadPoolExecutor(
            10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    for (int i = 0; i < 1; i++) {
      executorService.execute(new DockerSeleniumTest());
    }
  }

  public void run() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");

    CDPRemoteDriver driver = null;
    try {
      // CDPRemoteDriver is wrapper over RemoteWebDriver which handles executeCdpCommand
      // RemoteWebDriver don't support CDPCommands.
      // RemoteWebDriver driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
      driver = new CDPRemoteDriver(new URL(remote_url_chrome), options);

      System.out.println("Session started");
      driver.manage().window().maximize();

      HashMap parameters =
          new HashMap() {
            {
              put(
                  "source",
                  "window.localStorage.setItem('idp_tokens', '{\"acme\":{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ0eXAiOiJCZWFyZXIiLCJhdWQiOiJhY2NvdW50IiwiZXhwIjoxNjMxODU4MDI3LCJpYXQiOjE2MzE4NTc3MjcsInVwbiI6InN1cmluZGVyLnJhanBhbEBsb2dyaHl0aG0uY29tIiwiaXNzIjoibG9ncmh5dGhtLWRldmVsb3BtZW50Iiwic3ViIjoiMDQyYjg2M2ItNzEyMy00ZWQ5LWI2ZjQtYmMzZDc5NTk5MmQzIiwidGVuYW50X2lkIjoiYWNtZSIsImNhcGFiaWxpdGllcyI6WyJNQU5BR0VfQVVESVQiLCJNQU5BR0VfQ09MTEVDVE9SUyIsIk1BTkFHRV9GSUxURVJTIiwiTUFOQUdFX0NPTlRFTlQiLCJNQU5BR0VfUk9MRVMiLCJNQU5BR0VfUEFTU1dPUkRfUE9MSUNJRVMiLCJNQU5BR0VfVEVOQU5UX1NFVFRJTkdTIiwiTUFOQUdFX1VTRVJTIiwiTUFOQUdFX05PVElGSUNBVElPTlMiLCJNQU5BR0VfRU5USVRJRVMiLCJFWEVDVVRFX1NFQVJDSCIsIlZJRVdfTkFWSUdBVElPTl9TVUdHRVNUSU9OUyIsIkJPUkVBU19VU0VSIl0sIm5hbWUiOiJTdXJpbmRlciBSYWpwYWwgUmFqcGFsIiwiZ2l2ZW5fbmFtZSI6IlN1cmluZGVyIFJhanBhbCIsImZhbWlseV9uYW1lIjoiUmFqcGFsIiwiZW1haWwiOiJzdXJpbmRlci5yYWpwYWxAbG9ncmh5dGhtLmNvbSIsInByZWZlcnJlZF91c2VybmFtZSI6InN1cmluZGVyLnJhanBhbEBsb2dyaHl0aG0uY29tIiwianRpIjoiYjFkMmFhZGQtYzQ5Zi00MTJkLWJkMTctMmQ2N2NlMGNiMjJhIn0.LVoOp4RnLG5pd511hT1tKCGVpOGavROWbuW0ZaXB2PbVjoxOulaxQ9ZeLqwhvUNPvpzoL7t--8bisZXBeVpAof5AgxCx0dL_B4l1l1we6FOLFLU6EkQuMYogMtIM3xHCd9AoEYmWn0jMKq-Elr6p0G8KQpLbgcstHi9AQGMluxF7hY_RPgCi36__DMK3NpgI95byn9XHhU7gSy8Ngw3QvI5z-U2frCQAz4qMNO5Rfq1QUmhX51aQNhzPYKEfxSBKYDaHjq8_gUKJFhO0zswopsMxZg4qfog-rm-1OxKgxf8wRE0eiTulMkOvE8ozNpix5PnjLarwytTO6G5a8anx4Q\",\"tenantId\":\"acme\",\"capabilities\":[\"MANAGE_AUDIT\",\"MANAGE_COLLECTORS\",\"MANAGE_FILTERS\",\"MANAGE_CONTENT\",\"MANAGE_ROLES\",\"MANAGE_PASSWORD_POLICIES\",\"MANAGE_TENANT_SETTINGS\",\"MANAGE_USERS\",\"MANAGE_NOTIFICATIONS\",\"MANAGE_ENTITIES\",\"EXECUTE_SEARCH\",\"VIEW_NAVIGATION_SUGGESTIONS\",\"BOREAS_USER\"]}}')");
            }
          };

      driver.getCommandExecutor().execute(new Command(driver.getSessionId(), new CommandPayload("executeCdpCommand", ImmutableMap
          .of("cmd", "Page.addScriptToEvaluateOnNewDocument", "params", parameters))));

      System.out.println("Navigating to url");
      driver.navigate().to("https://app.k8s.development.boreas.cloud");

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
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (driver != null) driver.quit();
    }
  }
}
