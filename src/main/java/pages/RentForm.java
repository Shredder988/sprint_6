package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

// Класс формы "Про аренду"

public class RentForm extends HomePage {
    public static final String HEADER_PAGE = "Про аренду";

    // Локатор заголовка формы заказа
    private final By orderHeader = By.className("Order_Header__BZXOb");

    // Локатор поля ввода "* Когда привезти самокат"
    private final By deliveryDateField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Когда привезти самокат']");
    // Локатор сообщения об ошибке в поле даты доставки
    private final By deliveryDateMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректную дату доставки']");

    // Локатор поля выбора "* Срок аренды"
    private final By expirationField = By.xpath("//div[contains(@class,'Dropdown-root')]");
    // Локатор списка опций срока аренды
    private final By expirationListField = By.className("Dropdown-option");
    // Локатор сообщения об ошибке в поле срока аренды
    private final By expirationMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректный срок аренды']");

    // Локаторы чекбоксов цвета самоката
    private final By colorBlackField = By.xpath("//div[contains(@class,'Order_Checkboxes__3lWSI')]//label[@for='black']");
    private final By colorGreyField = By.xpath("//div[contains(@class,'Order_Checkboxes__3lWSI')]//label[@for='grey']");
    // Локатор сообщения об ошибке в поле цвета самоката
    private final By colorMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректный цвет самоката']");

    // Локатор поля ввода комментария для курьера
    private final By commentField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='Комментарий для курьера']");
    // Локатор сообщения об ошибке в поле комментария
    private final By commentMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректный комментарий']");

    // Локатор кнопки "Заказать"
    private final By orderEndButton = By.xpath("//div[@class = 'Order_Buttons__1xGrp']/button[text()='Заказать']");

    public RentForm(WebDriver driver) {
        super(driver);
    }


    // Вспомогательный метод для ожидания и поиска элемента по локатору
    // @param locator локатор элемента
    // @return найденный видимый элемент

    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Получение текста заголовка формы заказа
    // @return текст заголовка

    public String orderPageHeader() {
        return findElement(orderHeader).getText();
    }

    // Заполнение поля "* Когда привезти самокат"
    // @param data дата доставки

    public void setDeliveryDateField(String data) {
        WebElement input = findElement(deliveryDateField);
        input.clear();
        input.sendKeys(data);
    }

    // Выбор значения "* Срок аренды" из выпадающего списка
    // @param expiration срок аренды

    public void setExpirationField(String expiration) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(expirationField));
        dropdown.click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(expirationListField));
        List<WebElement> options = driver.findElements(expirationListField);

        boolean found = false;
        for (WebElement option : options) {
            if (expiration.equals(option.getText())) {
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Опция срока аренды '" + expiration + "' не найдена");
        }
    }

    // Выбор цвета самоката (черный и/или серый)
    // @param colors набор цветов

    public void setColorField(Set<String> colors) {
        for (String color : colors) {
            switch (color.toLowerCase()) {
                case "black":
                    wait.until(ExpectedConditions.elementToBeClickable(colorBlackField)).click();
                    break;
                case "grey":
                    wait.until(ExpectedConditions.elementToBeClickable(colorGreyField)).click();
                    break;
                default:
                    throw new IllegalArgumentException("Неизвестный цвет самоката: " + color);
            }
        }
    }

    // Заполнение поля "Комментарий для курьера"
    // @param comment комментарий

    public void setCommentField(String comment) {
        WebElement input = findElement(commentField);
        input.clear();
        input.sendKeys(comment);
    }

    // Нажатие по кнопке "Заказать"

    public void clickOrderEndButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderEndButton));
        button.click();
    }

    // Проверка наличия сообщения об ошибке в поле "* Когда привезти самокат"
    // @return true если сообщение отображается

    public boolean existDeliveryDateMessage() {
        return existMessage(deliveryDateMessage);
    }

    // Проверка наличия сообщения об ошибке в поле "* Срок аренды"
    // @return true если сообщение отображается

    public boolean existExpirationMessage() {
        return existMessage(expirationMessage);
    }

    // Проверка наличия сообщения об ошибке в поле "Цвет самоката"
    // @return true если сообщение отображается

    public boolean existColorMessage() {
        return existMessage(colorMessage);
    }

    // Проверка наличия сообщения об ошибке в поле "Комментарий для курьера"
    // @return true если сообщение отображается

    public boolean existCommentMessage() {
        return existMessage(commentMessage);
    }
}
