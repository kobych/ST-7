package com.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void retrieveWeatherData() {
        WebDriver browser = new ChromeDriver();
        try {
            browser.get(constructAPIUrl());
            WebElement jsonElement = browser.findElement(By.tagName("pre"));
            
            JSONObject parsedData = parseJsonData(jsonElement.getText());
            writeWeatherReport(parsedData);
            
        } catch (Exception e) {
            System.err.println("Weather retrieval failed: " + e.toString());
        } finally {
            browser.quit();
        }
    }

    private static String constructAPIUrl() {
        return "https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44"
                + "&hourly=temperature_2m,rain&current=cloud_cover"
                + "&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms";
    }

    private static JSONObject parseJsonData(String rawData) throws Exception {
        return (JSONObject) new JSONParser().parse(rawData);
    }

    private static void writeWeatherReport(JSONObject data) throws Exception {
        JSONObject hourly = (JSONObject) data.get("hourly");
        try (PrintWriter output = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            output.println(createTableHeader());
            output.println(createTableSeparator());
            
            JSONArray timestamps = (JSONArray) hourly.get("time");
            JSONArray temps = (JSONArray) hourly.get("temperature_2m");
            JSONArray rainfall = (JSONArray) hourly.get("rain");

            for (int idx = 0; idx < timestamps.size(); idx++) {
                String entry = formatTableRow(
                    idx + 1,
                    (String) timestamps.get(idx),
                    Double.parseDouble(temps.get(idx).toString()),
                    Double.parseDouble(rainfall.get(idx).toString())
                );
                System.out.println(entry);
                output.println(entry);
            }
        }
    }

    private static String createTableHeader() {
        return "| №   | Дата/время         | Температура | Осадки (мм) |";
    }

    private static String createTableSeparator() {
        return "|-----|--------------------|-------------|--------------|";
    }

    private static String formatTableRow(int num, String time, double temp, double rain) {
        return String.format("| %-3d | %-18s | %-11.1f | %-12.2f |", num, time, temp, rain);
    }
}