import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BottomPage;
import pages.StatusOrderPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

// Тесты для проверки элементов на главной странице.

public class HomeTransitionTest extends AbstractTest {
    private BottomPage objMainPage;

    @BeforeEach
    public void beforeEach() {
        // Инициализация объекта главной страницы
        objMainPage = new BottomPage(driver);
        // Закрытие окна с куки
        objMainPage.clickCookiesButton();
    }

    @Test
    @DisplayName("Нажатие на логотип \"Самоката\"")
    public void checkClickLogoScooter() {
        objMainPage.clickLogoScooter();
        assertEquals(HOST_TEST, driver.getCurrentUrl(), "Открылась не главная страница \"Самоката\"");
    }

    @Test
    @DisplayName("Нажатие на логотип \"Яндекса\"")
    public void checkClickLogoYandex() {
        objMainPage.clickLogoYandex();

        // Переключение на новую вкладку браузера
        String currentHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        assertEquals("https://ya.ru/", driver.getCurrentUrl(), "Открылась не главная страница \"Яндекса\"");
    }

    @Test
    @DisplayName("Проверка статуса несуществующего заказа со значением 0")
    public void checkStatusNotExistOrder() {
        objMainPage.clickStatusOrderButton();
        final String number = "0";
        objMainPage.setOrderNumber(number);
        objMainPage.clickGoButton();

        // Инициализация страницы статуса заказа
        StatusOrderPage objStatusOrderPage = new StatusOrderPage(driver);

        // Проверка, что номер заказа совпадает с введённым
        assertEquals(number, objStatusOrderPage.getNumberOrder(), "Номер заказа должен быть " + number);

        // Проверка отсутствия информации о заказе
        assertFalse(objStatusOrderPage.existInfoOrder(), "Информация о заказе не должна отображаться");
    }
}