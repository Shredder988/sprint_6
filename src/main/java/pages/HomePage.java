package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Абстрактный базовый класс страницы.

abstract public class HomePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Локаторы
    private final By logoYandex = By.xpath("//img[@alt='Yandex']");
    private final By logoScooter = By.xpath("//img[@alt='Scooter']");
    private final By orderButton = By.xpath("//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");
    private final By statusOrderButton = By.className("Header_Link__1TAG7");
    private final By orderNumberField = By.xpath("//div[@class='Input_InputContainer__3NykH']/input[@placeholder='Введите номер заказа']");
    private final By goButton = By.xpath("//div[@class='Header_SearchInput__3YRIQ']/button[text()='Go!']");
    private final By cookieButton = By.xpath("//button[@class='App_CookieButton__3cvqF']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Приватный метод для поиска элемента с ожиданием видимости
    private WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void clickOrderButton() {
        findElement(orderButton).click();
    }

    public void clickCookiesButton() {
        findElement(cookieButton).click();
    }

    public void clickLogoYandex() {
        findElement(logoYandex).click();
    }

    public void clickLogoScooter() {
        findElement(logoScooter).click();
    }

    public void clickStatusOrderButton() {
        findElement(statusOrderButton).click();
    }

    public void setOrderNumber(String order) {
        findElement(orderNumberField).sendKeys(order);
    }

    public void clickGoButton() {
        findElement(goButton).click();
    }

    public boolean existMessage(By field) {
        try {
            driver.findElement(field);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
