package com.heyhorse.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AddHorsesPage {

    protected AppiumDriver driver;
    public WebDriverWait wait;

     public AddHorsesPage(AppiumDriver driver){
            this.driver=driver;
             PageFactory.initElements(new AppiumFieldDecorator(driver), this);
             this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     }

     @AndroidFindBy(xpath="(//android.widget.EditText)[1]")
     protected WebElement txtName;

     @AndroidFindBy(xpath="(//android.widget.EditText)[2]")
     protected WebElement txtBarn;

     @AndroidFindBy(xpath="(//android.widget.EditText)[3]")
     protected WebElement txtLocation;

     @AndroidFindBy(accessibility="-")
     protected WebElement btnBirthdate;

     @AndroidFindBy(xpath="(//android.widget.Button)[1]")
     protected WebElement btnEditDate;
     @AndroidFindBy(xpath="//android.widget.EditText")
     protected WebElement txtDate;

     @AndroidFindBy(accessibility="OK")
     protected WebElement btnOk;

     @AndroidFindBy(accessibility="See horse's profile")
     protected WebElement btnHorseProfile;

     @AndroidFindBy(xpath="//android.widget.Button[@content-desc=\"Add Horse\"]")
     protected WebElement btnAddHorse;

     @AndroidFindBy(accessibility="Select a horse breed...")
     protected WebElement btnBreed;

     @AndroidFindBy(accessibility="Select a horse gender...")
     protected WebElement btnGender;

    @AndroidFindBy(xpath="(//android.widget.Button)[1]")
    protected WebElement btnBack;

     public void enterName(String name){
         wait.until(ExpectedConditions.elementToBeClickable(txtName));
         txtName.click();
         txtName.sendKeys(name);

     }

    public void enterBarn(String barn){
        wait.until(ExpectedConditions.elementToBeClickable(txtBarn));
        txtBarn.click();
        txtBarn.sendKeys(barn);

    }

    public void enterLocation(String location){
        wait.until(ExpectedConditions.elementToBeClickable(txtLocation));
        txtLocation.click();
        txtLocation.sendKeys(location);
    }

    public void selectBreed(){
         wait.until(ExpectedConditions.elementToBeClickable(btnBreed));
         btnBreed.click();
         selectFromList().click();
    }

    public void setBirthdate(){
         wait.until(ExpectedConditions.elementToBeClickable(btnBirthdate));
         btnBirthdate.click();
         try{
             wait.until(ExpectedConditions.elementToBeClickable(btnEditDate));
             btnEditDate.click();
         }catch(Exception ex){
             driver.findElement(By.xpath("(//android.widget.Button)[1]")).click();
        }
        try{
            wait.until(ExpectedConditions.elementToBeClickable(txtDate));
            txtDate.click();
            txtDate.clear();
            txtDate.sendKeys("7/7/2020");

        }catch (Exception e){
            WebElement txtField = driver.findElement(By.xpath("//android.widget.EditText"));
            txtField.click();
            txtField.clear();
            txtField.sendKeys("7/7/2020");
        }
        btnOk.click();

    }


    public void selectGender(){
        wait.until(ExpectedConditions.elementToBeClickable(btnGender));
        btnGender.click();
        selectFromList().click();
    }

    public void clickAddHorse(){
         wait.until(ExpectedConditions.elementToBeClickable(btnAddHorse));
         btnAddHorse.click();
    }

    public void clickHorseProfile(){
         wait.until(ExpectedConditions.elementToBeClickable(btnHorseProfile));
         btnHorseProfile.click();
    }

    public WebElement selectFromList(){
        List<WebElement> elements = driver.findElements(By.xpath("//android.widget.EditText/following-sibling::android.view.View//android.view.View//android.view.View"));
        System.out.println(elements.size());
        Random rand = new Random();
        return elements.get(rand.nextInt(elements.size()));
     }

    public  void scrollDown() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        int startX = this.driver.manage().window().getSize().getWidth() / 2;
        int startY = driver.manage().window().getSize().getHeight() / 2;
        Point start = new Point(startX,startY);

        int endY = (int) (this.driver.manage().window().getSize().getHeight() * 0.2);
        Point end = new Point(startX,endY);

        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), start.getX(),
                        start.getY()))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(250), PointerInput.Origin.viewport(), end.getX(),
                        end.getY()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        this.driver.perform(Collections.singletonList(swipe));
    }


}
