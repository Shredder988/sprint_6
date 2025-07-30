import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest extends AbstractTest {

    // Набор тестовых данных
    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(1, "Иван", "Иванов", "Кукуева, д.7, кв.1", "Лубянка", "+79889994444",
                        "сутки", LocalDateTime.now().plusDays(1).format(DATE_FORMATTER), Set.of("black"), "позвонить"),
                Arguments.of(2, "Зинаида", "Кудаедова", "Тюленина, д.1, кв.9", "Динамо", "+77778883333",
                        "двое суток", LocalDateTime.now().plusDays(10).format(DATE_FORMATTER), Set.of("grey"), "не звонить")
        );
    }

    @ParameterizedTest
    @DisplayName("Оформление заказа")
    @MethodSource("testData")
    public void checkMakeOrder(Integer button,
                               String name,
                               String lastName,
                               String address,
                               String subway,
                               String phone,
                               String expiration,
                               String deliveryDate,
                               Set<String> color,
                               String comment) {
        // Инициализация главной страницы и закрытие куки
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();

        // Выбор кнопки "Заказать" в зависимости от параметра
        switch (button) {
            case 1:
                objMainPage.clickOrderButton();
                break;
            case 2:
                objMainPage.clickOrderDownButton();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный номер кнопки: " + button);
        }

        // Заполнение формы "Для кого самокат"
        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает с ожидаемым");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);
        objCustomerForm.clickNextButton();

        // Заполнение формы аренды
        RentForm objRentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, objRentForm.orderPageHeader(),
                "Заголовок формы аренды не совпадает с ожидаемым");
        objRentForm.setExpirationField(expiration);
        objRentForm.setDeliveryDateField(deliveryDate);
        objRentForm.setColorField(color);
        objRentForm.setCommentField(comment);
        objRentForm.clickOrderEndButton();

        // Подтверждение заказа
        HatConsentForm objPopupConfirmationForm = new HatConsentForm(driver);
        objPopupConfirmationForm.waitDownLoad();
        assertThat(objPopupConfirmationForm.orderPageHeader()).contains(HatConsentForm.HEADER_PAGE);
        objPopupConfirmationForm.clickYesButton();

        // Проверка окна "Заказ оформлен"
        HatOrderForm objPopupOrderForm = new HatOrderForm(driver);
        assertThat(objPopupOrderForm.orderPageHeader()).contains(HatOrderForm.HEADER_PAGE);
        objPopupOrderForm.clickWatchButton();
    }
}
