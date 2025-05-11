package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    public static void fetchIPInformation() {
        WebDriver browser = new ChromeDriver();
        try {
            browser.get("https://api.ipify.org/?format=json");
            WebElement dataElement = browser.findElement(By.tagName("pre"));
            
            JSONParser jsonProcessor = new JSONParser();
            JSONObject responseData = (JSONObject) jsonProcessor.parse(dataElement.getText());
            
            String ip = (String) responseData.get("ip");
            System.out.println("Detected IP: " + ip);
            
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
            System.err.println("Operation interrupted: " + ie.getMessage());
        } catch (Exception e) {
            System.err.println("Execution error: " + e.toString());
        } finally {
            browser.quit();
        }
    }
}