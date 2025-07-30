import model.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.BottomPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Тесты для проверки текста вопросов и ответов в разделе "Вопросы о важном" на главной странице.

public class TextTest extends AbstractTest {

    @ParameterizedTest
    @DisplayName("Проверка текста вопросов и ответов в разделе \"Вопросы о важном\"")
    @CsvFileSource(resources = "/question.csv", delimiter = ';')
    public void checkTextFAQ(int i, String question, String answer) {
        BottomPage objMainPage = new BottomPage(driver);
        objMainPage.clickCookiesButton();

        Question faq = objMainPage.getAnswer(i);

        // В сообщениях выводим i+1, чтобы нумерация была с 1, а не с 0
        int displayIndex = i + 1;

        assertEquals(question, faq.getQuestion(), "Неверный вопрос под номером " + displayIndex);
        assertEquals(answer, faq.getAnswer(), "Неверный ответ под номером " + displayIndex);
    }
}
