package com.heyhorse.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreateEventPage {

    protected AppiumDriver driver;
    public WebDriverWait wait;
    public CreateEventPage(AppiumDriver driver){
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AndroidFindBy(accessibility = "Organizer")
    protected WebElement lblOrganizer;

    @AndroidFindBy(accessibility="Add")
    protected WebElement btnAdd;
    @AndroidFindBy(accessibility="Save")
    protected WebElement btnSave;

    @AndroidFindBy(accessibility="Feed")
    protected WebElement btnFeed;

    @AndroidFindBy(accessibility="Lesson")
    protected WebElement btnLesson;

    @AndroidFindBy(accessibility="Training Ride")
    protected WebElement btnTrainingRide;

    @AndroidFindBy(accessibility="Turnout")
    protected WebElement btnTurnout;

    @AndroidFindBy(accessibility="Veterinarian")
    protected WebElement btnVeterinarian;

    @AndroidFindBy(xpath="(//android.widget.EditText)[1]")
    protected WebElement txtEventName;

    @AndroidFindBy(xpath="(//android.widget.EditText)[2]")
    protected WebElement txtEventLocation;

    @AndroidFindBy(className = "android.widget.CheckBox")
    protected WebElement btnAllDay;

    @AndroidFindBy(className="android.widget.Button")
    protected WebElement btnCloseModal;

    @AndroidFindBy(accessibility="Add participants")
    protected WebElement btnAddParticipants;

    @AndroidFindBy(accessibility="Add more")
    protected WebElement btnAddMore;

    @AndroidFindBy(accessibility="OK")
    protected WebElement btnOk;

    @AndroidFindBy(accessibility="Edit")
    protected WebElement btnEdit;

    @AndroidFindBy(accessibility="Participants")
    protected WebElement participantsList;

    @AndroidFindBy(accessibility="New event")
    protected WebElement btnNewEvent;

    @AndroidFindBy(xpath="(//android.view.View/android.widget.Button)[1]")
    protected WebElement btnBack;

    @AndroidFindBy(accessibility="Upcoming events")
    protected WebElement upEvents;

    @AndroidFindBy(accessibility="Add horses")
    protected WebElement btnAddHorses;

    @AndroidFindBy(xpath="(//android.view.View[@content-desc=\"Starts\"]/following-sibling::android.view.View)[1]")
    protected WebElement btnSelectDate;

    @AndroidFindBy(accessibility="Select Horses")
    protected WebElement btnSelectHorses;

    @AndroidFindBy(accessibility="View event")
    protected WebElement btnViewEvent;

    @AndroidFindBy(accessibility="Cancel event")
    protected WebElement btnCancelEvent;

    @AndroidFindBy(accessibility="Cancel Event")
    protected WebElement btnConfirmCancelEvent;

    @AndroidFindBy(accessibility="Edit event")
    protected WebElement btnEditEvent;

    @AndroidFindBy(accessibility="Add location")
    protected WebElement btnAddLocation;

    public WebElement selectFromParticipantList(){
        List<WebElement> elements = driver.findElements(By.xpath("//android.widget.CheckBox"));
        Random rand = new Random();
        return elements.get(rand.nextInt(elements.size()));
    }

    public WebElement selectFromHorsesList(){
        List<WebElement> elements = driver.findElements(By.xpath("//android.view.View[@content-desc=\"Dismiss\"]/android.view.View/android.view.View/android.view.View//android.view.View"));
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        return elements.get(0);
    }

    public void selectEventDate(){

        int today =  LocalDate.now().getDayOfMonth();
        // Get the last day of the current month
        int maxDay = LocalDate.now().lengthOfMonth();
        // Generate a random day between today and the last day of the current month
        int randomDay = getRandomDay(today, maxDay);
        String xpath=String.format("//android.widget.Button[contains(@content-desc, '%d')]",randomDay);
        WebElement btnDay = driver.findElement(By.xpath(xpath));
        btnDay.click();
        btnOk.click();
    }

    public int getRandomDay( int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);

        //android.widget.Button[contains(@content-desc, "30")]
    }

    public void clickGeneratedEvent(String name){
        String xpath = String.format("//android.view.View[contains(@content-desc, \"%s\")]//android.view.View",name);
        WebElement newEvent = driver.findElement(By.xpath(xpath));
        wait.until(ExpectedConditions.visibilityOf(newEvent));
        newEvent.click();
    }

    public void clickNewEvent(){
        wait.until(ExpectedConditions.elementToBeClickable(btnNewEvent));
        btnNewEvent.click();
    }

    public void waitForEvents(){
        try{
            wait.until(ExpectedConditions.visibilityOf(upEvents));
        }catch (StaleElementReferenceException e){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@content-desc='Upcoming events']")));
        }
    }

    public void selectCategory(String category){
        switch (category){
            case "Feed":
                wait.until(ExpectedConditions.elementToBeClickable(btnFeed));
                btnFeed.click();
                break;
            case "Lesson":
                wait.until(ExpectedConditions.elementToBeClickable(btnLesson));
                btnLesson.click();
                break;
            case "Training Ride":
                wait.until(ExpectedConditions.elementToBeClickable(btnTrainingRide));
                btnTrainingRide.click();
                break;
            case "Turnout":
                wait.until(ExpectedConditions.elementToBeClickable(btnTurnout));
                btnTurnout.click();
                break;
            case "Veterinarian":
                wait.until(ExpectedConditions.elementToBeClickable(btnVeterinarian));
                btnVeterinarian.click();
                break;
        }
    }

    public void setEventName(String name, String category){
        wait.until(ExpectedConditions.visibilityOf(txtEventName));
        txtEventName.click();
        txtEventName.sendKeys(category +name);
    }

    public void selectDate(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAllDay));
        btnAllDay.click();
        wait.until(ExpectedConditions.elementToBeClickable(btnSelectDate));
        btnSelectDate.click();
        wait.until(ExpectedConditions.elementToBeClickable(btnOk));
    }

    public void clickAddParticipants(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAddParticipants));
        btnAddParticipants.click();

    }

    public void clickAddMore(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAddMore));
        btnAddMore.click();

    }

    public void selectParticipants(){
        selectFromParticipantList().click();
    }

    public void clickAdd(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAdd));
        btnAdd.click();
    }

    public void clickAddHorses(){
        wait.until(ExpectedConditions.elementToBeClickable(btnAddHorses));
        btnAddHorses.click();
    }

    public void selectHorses(){
        wait.until(ExpectedConditions.elementToBeClickable(btnSelectHorses));
        btnSelectHorses.click();
        selectFromHorsesList().click();
        closeMenu();

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

    public void addLocation(String location){
        wait.until(ExpectedConditions.elementToBeClickable(btnAddLocation));
        btnAddLocation.click();
        wait.until(ExpectedConditions.visibilityOf(txtEventLocation));
        txtEventLocation.click();
        txtEventLocation.sendKeys(location);
    }

    public void clickSave(){
        wait.until(ExpectedConditions.elementToBeClickable(btnSave));
        btnSave.click();
    }

    public void clickEdit(){
        wait.until(ExpectedConditions.elementToBeClickable(btnEdit));
        btnEdit.click();

    }

    public void clickEditEvent(){
        wait.until(ExpectedConditions.elementToBeClickable(btnEditEvent));
        btnEditEvent.click();
        wait.until(ExpectedConditions.elementToBeClickable(btnAddParticipants));

    }

    public void clickCancel(){
        wait.until(ExpectedConditions.elementToBeClickable(btnCancelEvent));
        btnCancelEvent.click();
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirmCancelEvent));
        btnConfirmCancelEvent.click();
        wait.withTimeout(Duration.ofMillis(2000));

    }

    public void clickCloseModal(){
        wait.until(ExpectedConditions.visibilityOf(btnCancelEvent));
        wait.until(ExpectedConditions.elementToBeClickable(btnCloseModal));
        btnCloseModal.click();

    }

    public void viewEvent(){
        wait.until(ExpectedConditions.elementToBeClickable(btnViewEvent));
        btnViewEvent.click();
        wait.until(ExpectedConditions.elementToBeClickable(lblOrganizer));

    }

    public void clickBtnBack(){
        wait.until(ExpectedConditions.elementToBeClickable(btnBack));
        btnBack.click();

    }
}
