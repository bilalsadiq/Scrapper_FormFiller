/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package dataentry;

import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
*
* @author Bilal
*/
public class FormFiller {

    static String jobTitle;
    static String categoryOptions;
    static String Desc;
    static String jobCity;
    static String jobURL;
    static String username = "jobscitibank@aol.com" , password = "abc123";
    static String State = "Texas";
//SAVE IT TO MYSQL
        public static void jobNubFormFiller() throws InterruptedException, RuntimeException, org.openqa.selenium.NoSuchElementException{
        

        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bilal\\Documents\\NetBeansProjects\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.jobnub.com");
        WebElement loginOption = driver.findElement(By.xpath("//a[@class='login-trigger custom-back-color']"));
        Thread.sleep(500);  // Let the user actually see something!
        loginOption.click();
        
        //Fill out the Login Info
        WebElement emailFill = driver.findElement(By.id("login-email"));
        WebElement passwordFill = driver.findElement(By.id("login-password"));
        emailFill.sendKeys(username);
        passwordFill.sendKeys(password);
        passwordFill.submit();
        
        //Click on the Post job
         WebElement postAJob = driver.findElement(By.xpath("//a[@class='home-tile box-1-back']"));
         postAJob.click();
         
        //Fill out the form
        Select category = new Select(driver.findElement(By.id("category_1_0"))); 
        category.selectByVisibleText("Banking");

        
        WebElement titleField = driver.findElement(By.id("title"));
        titleField.sendKeys(jobTitle);
        titleField.sendKeys(Keys.TAB + Desc);
        
        Select location = new Select(driver.findElement(By.id("region_0"))); 
        location.selectByIndex(1);
        Thread.sleep(5000);
        Select state = new Select(driver.findElement(By.id("region_1"))); 
        state.selectByVisibleText(State);
        WebElement cityTown = driver.findElement(By.id("zip"));
        cityTown.sendKeys(jobCity);
        WebElement URL = driver.findElement(By.id("application_url"));
        URL.sendKeys(jobURL);
        URL.sendKeys(Keys.RETURN);
        Thread.sleep(1000);
        driver.quit();
        }

        

    
    public static void trialMode() throws InterruptedException{  
 
        try{
        
        WebDriver driver = new ChromeDriver();
        
        driver.navigate().to("https://jobs.citi.com/search-jobs");
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        driver.manage().window().maximize();
        WebElement locationsTab = driver.findElement(By.id("country-toggle"));
        locationsTab.click();
        WebElement locationsTab1 = driver.findElement(By.id("country-filter-66"));
        locationsTab1.click();
        jse.executeScript("window.scrollBy(0,150)", "");
         Thread.sleep(500);
        WebElement locationsTab2 = driver.findElement(By.xpath("//a[@id='region-toggle']"));
        locationsTab2.click();
        WebElement locationsTab3 = driver.findElement(By.id("region-filter-23"));
        locationsTab3.click();      
        jse.executeScript("window.scrollBy(0,-150)", "");
        Thread.sleep(1000);
        
        for (int i = 0; i < 100; i++){

        WebElement table_element = driver.findElement(By.id("search-results-list"));
        List<WebElement> tr_collection=table_element.findElements(By.xpath("//ul//li//a[@href]//h2"));
        List<WebElement> tr_collection1=table_element.findElements(By.className("job-location"));
        jse.executeScript("window.scrollBy(0,20)", "");
        //table_element.sendKeys(Keys.DOWN);
        jobTitle = tr_collection.get(i).getText();
        String jobCty = tr_collection1.get(i).getText();
        String tokens[] = jobCty.split("[,]");
        jobCity = tokens[0];
        tr_collection.get(i).click();
            WebElement itemDesc = driver.findElement(By.className("ats-description"));
            Desc = itemDesc.getText();
            
            System.out.println(i + " " + jobTitle + "\n " + "City: " + jobCity + "\n\n");
            jobURL = driver.getCurrentUrl();
            driver.navigate().back();
           //  CREATE A CLASS WITH  3 VARIABLES USE IT AS ARRAY LIST
            //jobNubFormFiller();
            if (i == 14){
                jse.executeScript("window.scrollBy(0,1050)", "");
                WebElement nextButton = driver.findElement(By.className("next"));
                nextButton.click();
                Thread.sleep(1000);
                i = -1;
                jse.executeScript("window.scrollBy(0,-1050)", "");
            }
            Thread.sleep(500);
        }
       
        }
        
 
        catch (Exception e){
            System.out.println("Error " + e);
        }
    }
}
