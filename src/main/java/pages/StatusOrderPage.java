package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

// Класс страницы с информацией о заказе.

public class StatusOrderPage extends HomePage {

    // Поле ввода номера заказа
    private final By orderNumberField = By.xpath("//div[@class='Track_Form__N4FE3']//input[@type='text']");

    // Блок с информацией о заказе
    private final By infoOrder = By.className("Track_OrderInfo__2fpDL");

    public StatusOrderPage(WebDriver driver) {
        super(driver);
    }

    // Проверяет наличие блока с информацией о заказе.
    // @return true, если блок найден, иначе false

    public boolean existInfoOrder() {
        try {
            driver.findElement(infoOrder);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Получает номер заказа из поля ввода.
    // @return номер заказа как строка

    public String getNumberOrder() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberField));
        return input.getAttribute("value");
    }
}
