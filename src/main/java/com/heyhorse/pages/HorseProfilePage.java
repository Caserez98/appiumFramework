package com.heyhorse.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HorseProfilePage {

    protected AppiumDriver driver;
    public WebDriverWait wait;

    public HorseProfilePage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AndroidFindBy(xpath="(//android.view.View[@content-desc=\"Horse’s show name\"]/following-sibling::android.view.View)[1]")
    protected WebElement lblHorseName;

    @AndroidFindBy(xpath="(//android.view.View[@content-desc=\"Birth Date\"]/following-sibling::android.view.View)[1]")
    protected WebElement lblBirthdate;

    @AndroidFindBy(xpath="(//android.view.View[@content-desc=\"Breed \"]/following-sibling::android.view.View)[1]")
    protected WebElement lblBreed;

    @AndroidFindBy(xpath="(//android.view.View[@content-desc=\"Gender\"]/following-sibling::android.view.View)[1]")
    protected WebElement lblGender;

    @AndroidFindBy(xpath="(//android.view.View)[5]")
    protected WebElement lblBarn;

    @AndroidFindBy(xpath="(//android.view.View)[6]")
    protected WebElement lblLocation;

    @AndroidFindBy(xpath="(//android.widget.Button)[1]")
    protected WebElement btnBack;

    public void clickBack(){
        wait.until(ExpectedConditions.elementToBeClickable(btnBack));
        btnBack.click();

    }

    public String getHorseName(){
        return lblHorseName.getText();
    }

    public String getBirthDate(){
        return lblBirthdate.getText();
    }

    public String getBreed() {
        return lblBreed.getText();
    }

    public String getGender() {
        return lblGender.getText();
    }

    public String getBarn() {
        return lblBarn.getText();
    }

    public String getLocation() {
        return lblLocation.getText();
    }

}
