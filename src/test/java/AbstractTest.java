import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.format.DateTimeFormatter;

abstract public class AbstractTest {
    protected WebDriver driver;
    protected static final String HOST_TEST = "https://qa-scooter.praktikum-services.ru/";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    public void setUp() {
        // Инициализация драйвера Chrome
        driver = new ChromeDriver();
        // Переход на тестовый сайт
        driver.get(HOST_TEST);
    }

    @AfterEach
    public void teardown() {
        // Закрытие браузера, если драйвер инициализирован
        if (driver != null) {
            driver.quit();
        }
    }
}
