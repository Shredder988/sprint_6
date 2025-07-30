package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderForm extends HomePage {
    public static final String HEADER_PAGE = "Для кого самокат";

    private final By orderHeader = By.className("Order_Header__BZXOb");

    private final By nameField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Имя']");
    private final By nameMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректное имя']");

    private final By lastNameField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Фамилия']");
    private final By lastNameMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректную фамилию']");

    private final By addressField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By addressMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректный адрес']");

    private final By subwayField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Станция метро']");
    private final By subwaySelect = By.xpath("//div[@class='Order_Form__17u6u']//li[1]/button");
    private final By subwayMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[text()='Выберите станцию']");

    private final By phoneField = By.xpath("//div[@class='Order_Form__17u6u']//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By phoneMessage = By.xpath("//div[@class='Order_Form__17u6u']//div[contains(@class, 'Input_Visible___syz6') and text()='Введите корректный номер']");

    private final By nextButton = By.xpath("//div[@class='Order_NextButton__1_rCA']//button[text()='Далее']");

    public OrderForm(WebDriver driver) {
        super(driver);
    }

    // Приватный метод с ожиданием видимости элемента
    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Получение текста заголовка формы заказа
    public String orderPageHeader() {
        return findElement(orderHeader).getText();
    }

    // Заполнение поля "* Имя"
    public void setName(String name) {
        WebElement input = findElement(nameField);
        input.clear();
        input.sendKeys(name);
    }

    // Заполнение поля "* Фамилия"
    public void setLastName(String lastName) {
        WebElement input = findElement(lastNameField);
        input.clear();
        input.sendKeys(lastName);
    }

    // Заполнение поля "* Адрес"
    public void setAddress(String address) {
        WebElement input = findElement(addressField);
        input.clear();
        input.sendKeys(address);
    }

    // Заполнение поля "* Станция метро"
    public void setSubway(String subway) {
        WebElement input = findElement(subwayField);
        input.clear();
        input.sendKeys(subway);
        findElement(subwaySelect).click();
    }

    // Заполнение поля "* Телефон"
    public void setPhone(String phone) {
        WebElement input = findElement(phoneField);
        input.clear();
        input.sendKeys(phone);
    }

    // Клик вне поля телефона (смена фокуса)
    public void changeFocusPhoneField() {
        findElement(phoneField).sendKeys(Keys.TAB);
    }

    // Клик по кнопке "Далее"
    public void clickNextButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        button.click();
    }

    // Проверка наличия сообщения о некорректном имени
    public boolean existNameMessage() {
        return existMessage(nameMessage);
    }

    // Проверка наличия сообщения о некорректной фамилии
    public boolean existLastNameMessage() {
        return existMessage(lastNameMessage);
    }

    // Проверка наличия сообщения о некорректном адресе
    public boolean existAddressMessage() {
        return existMessage(addressMessage);
    }

    // Проверка наличия сообщения о некорректном телефоне
    public boolean existPhoneMessage() {
        return existMessage(phoneMessage);
    }

    // Проверка наличия сообщения о некорректной станции метро
    public boolean existSubwayMessage() {
        return existMessage(subwayMessage);
    }
}
