package com.heyhorse.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MessagesPage {

    protected AppiumDriver driver;
    public WebDriverWait wait;

    public MessagesPage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AndroidFindBy(accessibility="New message")
    protected WebElement btnMessage;

    @AndroidFindBy(className="android.widget.EditText")
    protected WebElement txtSearch;

    @AndroidFindBy(xpath="(//android.widget.Button)[3]")
    protected WebElement btnSend;

    @AndroidFindBy(xpath="(//android.widget.Button)[2]")
    protected WebElement btnImage;
    @AndroidFindBy(xpath="(//android.widget.Button)[1]")
    protected WebElement btnBack;

    public void sendNewMessage(){
        wait.until(ExpectedConditions.elementToBeClickable(btnMessage));
        btnMessage.click();
    }

    public void findUser(String user){
        wait.until(ExpectedConditions.visibilityOf(txtSearch));
        txtSearch.click();
        txtSearch.sendKeys(user);

    }

    public void clickUser(String user){
        String xpath= String.format("//android.widget.ImageView[contains(@content-desc, '%s')]",user);
        WebElement btnUser = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        btnUser.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message){
        wait.until(ExpectedConditions.visibilityOf(btnImage));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        txtSearch.click();
        txtSearch.sendKeys(message);
        btnSend.click();
    }
    public boolean isMessageSent(String message){
        String xpath= String.format("//android.view.View[contains(@content-desc,'%s')]",message);
        WebElement lblSentMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return lblSentMessage.isDisplayed();
    }
    public boolean isMessageReceived(String message, String sender){
        String xpath = String.format("//android.widget.ImageView[contains(@content-desc,'%s')]",sender);
        WebElement btnSender = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        btnSender.click();
        String xpath2 = String.format("//android.widget.ImageView[contains(@content-desc,'%s')]",message);
        WebElement lblMessageReceived =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath2)));
        return lblMessageReceived.isDisplayed();
    }

    public void clickBack(){
        wait.until(ExpectedConditions.elementToBeClickable(btnBack));
        btnBack.click();

    }



}
