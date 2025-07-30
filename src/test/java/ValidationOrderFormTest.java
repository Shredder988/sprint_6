import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import pages.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

// Класс тестов для валидации полей формы заказа.

public class ValidationOrderFormTest extends AbstractTest {
    // Позитивные тестовые данные для заполнения формы
    private final String name = "Иван";
    private final String lastName = "Иванов";
    private final String address = "Ленина, д.1, кв.1";
    private final String subway = "Сокол";
    private final String phone = "+79994444333";
    private final String expiration = "сутки";
    private final String deliveryDate = LocalDateTime.now().plusDays(1).format(DATE_FORMATTER);
    private final Set<String> color = Set.of("black");
    private final String comment = "позвонить";

    // Тесты валидации поля "* Имя" с разными входными данными.

    @ParameterizedTest
    @CsvSource({
            "'', false",          // пустое имя - негативный кейс
            "Ива, true",          // валидное короткое имя
            "ИвановИванИванович, true", // валидное длинное имя (граничное)
            "Иван3, false"       // имя с цифрой - негативный кейс
    })
    @DisplayName("Валидация поля \"* Имя\"")
    public void checkNameField(String nameT, Boolean isPassed) {
        // Инициализация главной страницы и закрытие окна с куки
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        // Заполнение формы "Для кого самокат"
        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(nameT);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);

        // Проверка наличия/отсутствия сообщения об ошибке в поле имени
        assertEquals(!isPassed, objCustomerForm.existNameMessage());

        // Переход к следующей форме
        objCustomerForm.clickNextButton();

        // Проверка перехода на форму аренды в зависимости от валидности имени
        RentForm objRentForm = new RentForm(driver);
        assertEquals(isPassed, RentForm.HEADER_PAGE.equals(objRentForm.orderPageHeader()));
    }

    // Тесты валидации поля "* Фамилия" с разными входными данными.

    @ParameterizedTest
    @CsvSource({
            "'', false",              // пустая фамилия - негативный кейс
            "Босяков, true",          // валидная фамилия
            "Босяков3, false"         // фамилия с цифрой - негативный кейс
    })
    @DisplayName("Валидация поля \"* Фамилия\"")
    public void checkLastNameField(String lastNameT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastNameT);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);

        // Проверка наличия/отсутствия сообщения об ошибке в поле фамилии
        assertEquals(!isPassed, objCustomerForm.existLastNameMessage());

        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(isPassed, RentForm.HEADER_PAGE.equals(objRentForm.orderPageHeader()));
    }

    // Тесты валидации поля "* Адрес" с разными входными данными.

    @ParameterizedTest
    @CsvSource({
            "'', false",                           // пустой адрес - негативный кейс
            "Гагарина, д.3, кв.18, true",         // валидный адрес
            "Гагарина проспект 3, true",          // валидный адрес с пробелом
            "Гагарина 3/18, true",                 // валидный адрес с символом '/'
            "Гагарина 3@, false"                   // адрес с недопустимым символом
    })
    @DisplayName("Валидация поля \"* Адрес: куда привезти заказ\"")
    public void checkAddressField(String addressT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(addressT);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);

        // Проверка наличия/отсутствия сообщения об ошибке в поле адреса
        assertEquals(!isPassed, objCustomerForm.existAddressMessage());

        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(isPassed, RentForm.HEADER_PAGE.equals(objRentForm.orderPageHeader()));
    }

    // Тест проверки валидного значения поля "* Станция метро".

    @Test
    @DisplayName("Валидация поля \"* Станция метро\" - существующее значение")
    public void checkExistSubwayField() {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);

        // Проверка отсутствия сообщения об ошибке
        assertFalse(objCustomerForm.existSubwayMessage());

        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, objRentForm.orderPageHeader());
    }

    // Тест проверки пустого поля "* Станция метро".

    @Test
    @DisplayName("Валидация поля \"* Станция метро\" - пустое поле")
    public void checkEmptySubwayField() {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setPhone(phone);

        objCustomerForm.clickNextButton();

        // Проверка существования сообщения об ошибке
        assertTrue(objCustomerForm.existSubwayMessage());

        RentForm objRentForm = new RentForm(driver);
        // Проверка, что переход на следующую форму не произошёл
        assertNotEquals(RentForm.HEADER_PAGE, objRentForm.orderPageHeader());
    }

    // Тесты валидации поля "* Телефон".

    @ParameterizedTest
    @CsvSource({
            "89994444333, true",
            "8999444422, false",
            "+79994444333, true",
            "+7999444422, false",
            "1111, false",
            "'', false"
    })
    @DisplayName("Валидация поля \"* Телефон: на него позвонит курьер\"")
    public void checkPhoneField(String phoneT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phoneT);
        objCustomerForm.changeFocusPhoneField();

        // Проверка наличия/отсутствия сообщения об ошибке
        assertEquals(!isPassed, objCustomerForm.existPhoneMessage());

        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(isPassed, RentForm.HEADER_PAGE.equals(objRentForm.orderPageHeader()));
    }

    // Тесты валидации поля "* Срок аренды".

    @ParameterizedTest
    @CsvSource({
            "сутки, true",
            "двое суток, true",
            "семеро суток, true",
            "'', false",
            "месяц, false"
    })
    @DisplayName("Валидация поля \"* Срок аренды\"")
    public void checkExpirationField(String expirationT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);
        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, objRentForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objRentForm.setExpirationField(expirationT);
        objRentForm.setDeliveryDateField(deliveryDate);
        objRentForm.setColorField(color);
        objRentForm.setCommentField(comment);

        // Проверка наличия/отсутствия сообщения об ошибке
        assertEquals(!isPassed, objRentForm.existExpirationMessage());

        objRentForm.clickOrderEndButton();

        HatConsentForm objPopupConfirmationForm = new HatConsentForm(driver);
        boolean actual = objPopupConfirmationForm.orderPageHeader().contains(HatConsentForm.HEADER_PAGE);
        assertEquals(isPassed, actual);
    }

    // Тесты валидации поля "* Когда привезти самокат".

    private static Stream<Arguments> testDeliveryDateData() {
        return Stream.of(
                Arguments.of(LocalDateTime.now().plusDays(-1).format(DATE_FORMATTER), false),
                Arguments.of(LocalDateTime.now().format(DATE_FORMATTER), false),
                Arguments.of(LocalDateTime.now().plusDays(1).format(DATE_FORMATTER), true),
                Arguments.of(LocalDateTime.now().plusDays(2).format(DATE_FORMATTER), true),
                Arguments.of("", false)
        );
    }

    @ParameterizedTest
    @DisplayName("Валидация поля \"* Когда привезти самокат\"")
    @MethodSource("testDeliveryDateData")
    public void checkDeliveryDateField(String deliveryDateT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);
        objCustomerForm.clickNextButton();

        RentForm objRentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, objRentForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objRentForm.setExpirationField(expiration);
        objRentForm.setDeliveryDateField(deliveryDateT);
        objRentForm.setColorField(color);
        objRentForm.setCommentField(comment);

        // Проверка наличия/отсутствия сообщения об ошибке
        assertEquals(!isPassed, objRentForm.existDeliveryDateMessage());

        objRentForm.clickOrderEndButton();

        HatConsentForm objPopupConfirmationForm = new HatConsentForm(driver);
        boolean actual = objPopupConfirmationForm.orderPageHeader().contains(HatConsentForm.HEADER_PAGE);
        assertEquals(isPassed, actual);
    }

    // Тесты валидации поля "Цвет самоката".

    private static Stream<Arguments> testColorData() {
        return Stream.of(
                Arguments.of(Set.of("black"), true),
                Arguments.of(Set.of("grey"), true),
                Arguments.of(Set.of("black", "grey"), true),
                Arguments.of(Set.of(), true)
        );
    }

    @ParameterizedTest
    @MethodSource("testColorData")
    @DisplayName("Валидация поля \"Цвет самоката\"")
    public void checkColorField(Set<String> colorT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);
        objCustomerForm.clickNextButton();

        RentForm rentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, rentForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        rentForm.setExpirationField(expiration);
        rentForm.setDeliveryDateField(deliveryDate);
        rentForm.setColorField(colorT);
        rentForm.setCommentField(comment);

        // Проверка наличия/отсутствия сообщения об ошибке
        assertEquals(!isPassed, rentForm.existColorMessage());

        rentForm.clickOrderEndButton();

        HatConsentForm popupConfirmationForm = new HatConsentForm(driver);
        boolean actual = popupConfirmationForm.orderPageHeader().contains(HatConsentForm.HEADER_PAGE);
        assertEquals(isPassed, actual);
    }

    // Тесты валидации поля "Комментарий для курьера".

    @ParameterizedTest
    @CsvSource({
            "'', true",
            "привезти, true",
            "привезти в течении часа, true",
            "привезти1, true",
            "bring it to me, false",
            "привезти!, false"
    })
    @DisplayName("Валидация поля \"Комментарий для курьера\"")
    public void checkCommentField(String commentT, Boolean isPassed) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();
        objMainPage.clickOrderButton();

        OrderForm objCustomerForm = new OrderForm(driver);
        assertEquals(OrderForm.HEADER_PAGE, objCustomerForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        objCustomerForm.setName(name);
        objCustomerForm.setLastName(lastName);
        objCustomerForm.setAddress(address);
        objCustomerForm.setSubway(subway);
        objCustomerForm.setPhone(phone);
        objCustomerForm.clickNextButton();

        RentForm rentForm = new RentForm(driver);
        assertEquals(RentForm.HEADER_PAGE, rentForm.orderPageHeader(),
                "Заголовок формы не совпадает");
        rentForm.setExpirationField(expiration);
        rentForm.setDeliveryDateField(deliveryDate);
        rentForm.setColorField(color);
        rentForm.setCommentField(commentT);

        // Проверка наличия/отсутствия сообщения об ошибке
        assertEquals(!isPassed, rentForm.existCommentMessage());

        rentForm.clickOrderEndButton();

        HatConsentForm popupConfirmationForm = new HatConsentForm(driver);
        boolean actual = popupConfirmationForm.orderPageHeader().contains(HatConsentForm.HEADER_PAGE);
        assertEquals(isPassed, actual);
    }
}
