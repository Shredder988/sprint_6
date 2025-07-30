package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//Класс всплывающего окна с сообщением "Хотите оформить заказ?"

public class HatConsentForm {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final String HEADER_PAGE = "Хотите оформить заказ?";

    // Кнопка "Да"
    private final By yesButton = By.xpath("//div[@class='Order_Buttons__1xGrp']//button[text()='Да']");

    // Заголовок формы заказа
    private final By orderHeader = By.className("Order_ModalHeader__3FDaJ");

    public HatConsentForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Ожидание загрузки заголовка формы заказа

    public void waitDownLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderHeader));
    }

    // Получение текста заголовка формы заказа
    //@return текст заголовка

    public String orderPageHeader() {
        return driver.findElement(orderHeader).getText();
    }

    // Нажатие по кнопке "Да"

    public void clickYesButton() {
        driver.findElement(yesButton).click();
    }

}
