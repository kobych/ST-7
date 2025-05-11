package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDrivers\\chromedriver-win64\\chromedriver.exe");
        
        runSeleniumTest();
        
        executeIPFetch();
        executeWeatherFetch();
    }

    private static void runSeleniumTest() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://www.calculator.net/password-generator.html");
            System.out.println(driver.getTitle());
            Thread.sleep(4000);
        } catch (InterruptedException ie) {
            handleException("Interrupted during test:", ie);
        } catch (Exception e) {
            handleException("General error occurred:", e);
        } finally {
            driver.quit();
        }
    }

    private static void executeIPFetch() {
        System.out.println("\nGet IP");
        Task2.fetchIPInformation();
    }

    private static void executeWeatherFetch() {
        System.out.println("\nGet weather");
        Task3.retrieveWeatherData();
    }

    private static void handleException(String message, Exception e) {
        System.out.println(message);
        e.printStackTrace();
    }
}