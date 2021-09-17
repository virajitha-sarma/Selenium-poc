package com.selenium;

import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteWebStorage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 *
 * This class verifies if ChromeDriver can be used with selenium-docker environment. Assumption was that Chromedriver which extends RemoteChromeDriver
 * will have functionality to connect to remote server as does RemoteWebDriver. But ChromeDriver doesnot have the support and throws an error asking us to
 * configure the local chrome path "webdriver.chrome.driver" {@link org.openqa.selenium.chrome.ChromeDriverService} and doesnot consider the "webdriver.remote.server" setting that RemoteWebDriver sets
*/
public class DevTools {

    public static String remote_url_chrome = "http://localhost:4444";

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        ChromeDriver cd = null;
        try {
            System.getProperty("webdriver.remote.server", "http://localhost:4444/");
           // driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
            cd = new ChromeDriver(options);
            System.out.println("Session started");
            cd.manage().window().maximize();

            // To Access local storage
            RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(cd);
            RemoteWebStorage webStorage = new RemoteWebStorage(executeMethod);
            LocalStorage storage = webStorage.getLocalStorage();

            System.out.println("Navigating to url");
            cd.get("https://app.k8s.development.boreas.cloud");
      storage.setItem(
          "idp_tokens",
          "{\"acme\":{\"accessToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ0eXAiOiJCZWFyZXIiLCJhdWQiOiJhY2NvdW50IiwiZXhwIjoxNjMxODc3MzMyLCJpYXQiOjE2MzE4NzcwMzIsInVwbiI6InZpcmFqaXRoYS5zYXJtYUBsb2dyaHl0aG0uY29tIiwiaXNzIjoibG9ncmh5dGhtLWRldmVsb3BtZW50Iiwic3ViIjoiOWQ1ZDY3NzYtNTRlOS00NmNlLThkNzYtNjlhNzI4ZjRiZmQ5IiwidGVuYW50X2lkIjoiYWNtZSIsImNhcGFiaWxpdGllcyI6WyJNQU5BR0VfQVVESVQiLCJNQU5BR0VfQ09MTEVDVE9SUyIsIk1BTkFHRV9GSUxURVJTIiwiTUFOQUdFX0NPTlRFTlQiLCJNQU5BR0VfUk9MRVMiLCJNQU5BR0VfUEFTU1dPUkRfUE9MSUNJRVMiLCJNQU5BR0VfVEVOQU5UX1NFVFRJTkdTIiwiTUFOQUdFX1VTRVJTIiwiTUFOQUdFX05PVElGSUNBVElPTlMiLCJNQU5BR0VfRU5USVRJRVMiLCJFWEVDVVRFX1NFQVJDSCIsIlZJRVdfTkFWSUdBVElPTl9TVUdHRVNUSU9OUyIsIkJPUkVBU19VU0VSIl0sIm5hbWUiOiJWaXJhaml0aGEgU2FybWEiLCJnaXZlbl9uYW1lIjoiVmlyYWppdGhhIiwiZmFtaWx5X25hbWUiOiJTYXJtYSIsImVtYWlsIjoidmlyYWppdGhhLnNhcm1hQGxvZ3JoeXRobS5jb20iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ2aXJhaml0aGEuc2FybWFAbG9ncmh5dGhtLmNvbSIsImp0aSI6ImI1NmY5MjQ0LTE0NDctNDg0YS1iNDZkLTc4YzY4Mzk4NWFlNiJ9.RbEVyXM1Dajo5xnyPEuaXjHvLSe8w246DGRZT7wrel7dt5PhHXXL91A_RQjF-lNMQviC8Pg54J2DzMxF5u1T4htTmhA7ok-j0-5lgJAtxggPaCR-vVVa78pip7g1bMrN53B3GZzKIu3jauk2aRXJ9a1K6Ty7__da_Z7YEWeGeviwsRNYT2OWVJO33nTk4x8U4_hYrl_08L1_4yfO3hrI_1ZxLimjJttWOenAeqXdQR9ffvnPP8qEQ-ksxV4IhpgebOrKZ3KfTzUOpbboUt87DKoD2O3E7xlRstBaAdZ2WEw5qssmO3NmKjj6tsfjWCeHLHEknSyufChT7bwQ0ywAWA\",\"tenantId\":\"acme\",\"capabilities\":[\"MANAGE_AUDIT\",\"MANAGE_COLLECTORS\",\"MANAGE_FILTERS\",\"MANAGE_CONTENT\",\"MANAGE_ROLES\",\"MANAGE_PASSWORD_POLICIES\",\"MANAGE_TENANT_SETTINGS\",\"MANAGE_USERS\",\"MANAGE_NOTIFICATIONS\",\"MANAGE_ENTITIES\",\"EXECUTE_SEARCH\",\"VIEW_NAVIGATION_SUGGESTIONS\",\"BOREAS_USER\"]}}");
            cd.get("https://app.k8s.development.boreas.cloud");
            System.out.println("Waiting for page to load.");

            // This is added just to test the pdf generation. Will be changed later.
            Thread.sleep(10000);

            System.out.println("Printing PDF");
            Pdf pdf = cd.print(new PrintOptions());
            byte[] decodedBytes = Util.base64Decode(pdf.getContent());
            String fileName = "./test-" + UUID.randomUUID().toString() + ".pdf";
            System.out.printf("Writing to File: %s \n", fileName);
            Util.writeToFile(fileName, decodedBytes);

            System.out.println("Done");
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        } finally {
            if (cd != null) cd.quit();
        }
    }
}
