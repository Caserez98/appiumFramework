package com.heyhorse.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
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

public class HomePage {
    protected AppiumDriver driver;

    protected WebDriverWait wait;

    public HomePage(AppiumDriver driver) {
        this.driver=driver;

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AndroidFindBy(accessibility = "Logout")
    protected WebElement btnLogout;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Notifications')]")
    protected WebElement btnNotifications;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Home')]/following-sibling::android.view.View[contains(@content-desc,'Messages')]")
    protected WebElement btnMessages;

    @AndroidFindBy(accessibility = "Home")
    protected WebElement btnHome;

    @AndroidFindBy(accessibility = "Calendar")
    protected WebElement btnCalendar;



    @AndroidFindBy(accessibility = "Menu")
    protected WebElement btnMenu;

    @AndroidFindBy(accessibility = "Horses")
    protected WebElement btnHorses;

    @AndroidFindBy(accessibility = "Profile")
    protected WebElement btnProfile;

    @AndroidFindBy(accessibility = "Barn")
    protected WebElement btnBarn;

    @AndroidFindBy(accessibility = "Trainers")
    protected WebElement btnTrainers;

    @AndroidFindBy(accessibility = "Settings")
    protected WebElement btnSettings;

    public void clickHorses() {
        wait.until(ExpectedConditions.elementToBeClickable(btnHorses));
        btnHorses.click();

    }

    public void clickMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(btnMenu));
        btnMenu.click();
    }

    public void clickSettings() {
        wait.until(ExpectedConditions.elementToBeClickable(btnSettings));
        btnSettings.click();

    }

    public void clickMessages(){
        wait.until(ExpectedConditions.elementToBeClickable(btnMessages));
        btnMessages.click();
    }

    public void clickCalendar(){
        wait.until(ExpectedConditions.elementToBeClickable(btnCalendar));
        btnCalendar.click();

    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        btnLogout.click();
    }

    public void closeMenu(){
        final PointerInput FINGER = new PointerInput(PointerInput.Kind.TOUCH, "FINGER");
        Point tapPoint = new Point(556, 944);
        Sequence tap = new Sequence(FINGER, 1);
        tap.addAction(FINGER.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
        tap.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(FINGER.createPointerMove(Duration.ofMillis(50),
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y));
        tap.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(tap));

    }

}
