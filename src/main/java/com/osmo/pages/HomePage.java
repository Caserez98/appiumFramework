package com.osmo.pages;

import com.appium.setup.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import lombok.Getter;
import org.openqa.selenium.WebElement;
@Getter
public class HomePage extends BasePage {
    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"2\"]")
    private WebElement btn2;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"0\"]")
    private WebElement btn0;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc=\"Convertir\"]")
    private WebElement btnSWAP;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Ver todas\"]")
    private WebElement btnAllAccounts;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Retirar\"]")
    private  WebElement btnWithdrawal;

    @AndroidFindBy(xpath = "(//android.widget.Button[@content-desc=\"Saldo total (USD)\"]/following-sibling::android.view.View)[1]")
    private WebElement lblBalance;




}
