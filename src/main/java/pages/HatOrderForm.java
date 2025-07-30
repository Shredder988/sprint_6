package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Класс всплывающего окна с сообщением об успешном оформлении заказа "Заказ оформлен".

public class HatOrderForm {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final String HEADER_PAGE = "Заказ оформлен";

    // Кнопка "Посмотреть статус"
    private final By watchButton = By.xpath("//div[@class='Order_NextButton__1_rCA']//button[text()='Посмотреть статус']");

    // Заголовок формы заказа
    private final By orderHeader = By.className("Order_ModalHeader__3FDaJ");

    public HatOrderForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Получение текста заголовка формы заказа.
    // @return текст заголовка

    public String orderPageHeader() {
        return driver.findElement(orderHeader).getText();
    }

    // Нажатие по кнопке "Посмотреть статус".

    public void clickWatchButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(watchButton));
        element.click();
    }
}
