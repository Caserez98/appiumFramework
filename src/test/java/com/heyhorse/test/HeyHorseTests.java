package com.heyhorse.test;

import com.appium.setup.AppTestCase;
import com.github.javafaker.Faker;
import com.heyhorse.pages.*;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HeyHorseTests extends AppTestCase {
    private final String className = this.getClass().getSimpleName();
    Faker faker = new Faker();
    String horseName = faker.name().firstName();
    String horseLocation = faker.country().capital();
    String horseBarn = "Fake Barn";
    String message = faker.leagueOfLegends().summonerSpell();
    String sender = "Ivy Sterling";
    String receiver = "Leon Dash";


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        initializeAppTest(className, "Android");
    }

    @Test
    public void TC01_Login(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.isPageLoaded());
        loginPage.clickSignIn();
        common.logScreen("Login form displayed");
        loginPage.enterCredentials("ngcorrea4a@gmail.com","abcd1234");
        loginPage.attemptToLogin();
        common.log("Login was successfully");
    }

  //  @Test
//    public void TC02_AddNewHorse(){
//        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
//        HomePage homePage = new HomePage(driver);
//        homePage.clickHorses();
//
//        HorsesPage horsesPage = new HorsesPage(driver);
//        horsesPage.addNewHorse();
//
//        AddHorsesPage addHorsesPage = new AddHorsesPage(driver);
//        addHorsesPage.enterName(horseName);
//        addHorsesPage.enterBarn(horseBarn);
//        addHorsesPage.enterLocation(horseLocation);
//        addHorsesPage.setBirthdate();
//        addHorsesPage.scrollDown();
//        addHorsesPage.scrollDown();
//        addHorsesPage.selectBreed();
//        addHorsesPage.selectGender();
//        addHorsesPage.clickAddHorse();
//        addHorsesPage.clickHorseProfile();
//        common.log("Horse was created successfully");
//    }

    @Test
    public void TC03_SendMessage(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();

        HomePage homePage = new HomePage(driver);
        homePage.closeMenu();

        MessagesPage messagesPage = new MessagesPage(driver);

        homePage.clickMessages();

        messagesPage.sendNewMessage();

        messagesPage.findUser(receiver);
        messagesPage.clickUser(receiver);
        messagesPage.sendMessage(message);
        Assert.assertTrue(messagesPage.isMessageSent(message));
    }

    @Test
    public void TC04_Logout(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
        MessagesPage messagesPage = new MessagesPage(driver);
        messagesPage.clickBack();
        HomePage homePage = new HomePage(driver);
        homePage.clickMenu();
        homePage.clickSettings();
        common.logScreen("Attempt to click Logout");
        homePage.clickLogout();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageLoaded());

    }

    @Test
    public void TC_05_LoginAndValidateReceivedMessage(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
        LoginPage loginPage = new LoginPage(driver);

        //Assert.assertTrue(loginPage.isPageLoaded());
        loginPage.clickSignIn();
        loginPage.enterCredentials("alexttitocornejo@gmail.com","123456");
        loginPage.attemptToLogin();
        HomePage homePage = new HomePage(driver);
        homePage.closeMenu();


        homePage.clickMessages();

        MessagesPage messagesPage = new MessagesPage(driver);

        Assert.assertTrue(messagesPage.isMessageReceived(message,sender));
        common.logScreen("Message was received");


    }

    @Test
    public void TC_06_CreateNewEvent(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
        CreateEventPage createEventPage = new CreateEventPage(driver);
        HomePage homePage= new HomePage(driver);
        MessagesPage messagesPage = new MessagesPage(driver);
        //messagesPage.clickBack();
        homePage.clickCalendar();
        createEventPage.clickNewEvent();
        common.log("New Event creation layout is displayed");
        createEventPage.selectCategory("Feed");
        createEventPage.setEventName("Test","Feed");
        createEventPage.selectDate();
        createEventPage.selectEventDate();
        createEventPage.clickAddParticipants();
        createEventPage.clickAddMore();
        createEventPage.selectParticipants();
        createEventPage.selectParticipants();
        createEventPage.clickAdd();
        createEventPage.scrollDown();
        createEventPage.scrollDown();
        createEventPage.clickAddHorses();
        createEventPage.selectHorses();
        //createEventPage.addLocation("Narnia");
        createEventPage.clickSave();
        createEventPage.waitForEvents();
        createEventPage.scrollDown();
        common.logScreen("Even was scheduled");
        createEventPage.clickGeneratedEvent("FeedTest");
    }
    @Test
    public void TC_07_EditAndDeleteNewEvent(){
        AndroidDriver driver = (AndroidDriver) AppTestCase.getActionDriver().getAppiumDriver();
        CreateEventPage createEventPage = new CreateEventPage(driver);
        createEventPage.clickEditEvent();
        createEventPage.clickAddParticipants();
        createEventPage.clickAddMore();
        createEventPage.selectParticipants();
        createEventPage.clickAdd();
        createEventPage.clickSave();
        common.log("Event was edited successfully");
        createEventPage.waitForEvents();
        common.logScreen("Event displayed in list");
        createEventPage.clickGeneratedEvent("FeedTest");
        createEventPage.viewEvent();
        common.logScreen("Event screen is displayed");
        //createEventPage.clickGeneratedEvent("FeedTest");
        createEventPage.clickCancel();
        createEventPage.clickCloseModal();
        createEventPage.waitForEvents();
        common.logScreen("Event was cancelled");

    }

    @Override
    protected void cleanPageObjects() {

    }
}
