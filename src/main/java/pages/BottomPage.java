package pages;

import model.Question;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

// Класс главной страницы с нижним блоком
public class BottomPage extends HomePage {

    // Кнопка "Заказать" в нижней центральной части страницы
    private final By orderDownButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Заголовок раздела "Вопросы о важном"
    private final By headerFAQSection = By.xpath(".//div[@class='Home_FourPart__1uthg']/div[@class='Home_SubHeader__zwi_E']");

    // Шаблоны XPath для вопроса и ответа по индексу
    private final String questionPath = ".//div[@class='Home_FAQ__3uVm4']//div[@role='button' and @id='accordion__heading-%d']";
    private final String answerPath = ".//div[@class='Home_FAQ__3uVm4']//div[@aria-labelledby='accordion__heading-%d']";

    public BottomPage(WebDriver driver) {
        super(driver);
    }

    // Прокрутка страницы к элементу
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    // Выполнить клик по кнопке "Заказать" внизу страницы
    public void clickOrderDownButton() {
        WebElement orderButton = driver.findElement(orderDownButton);
        scrollToElement(orderButton);
        orderButton.click();
    }

    // Получить вопрос и ответ по заданному индексу
    public Question getAnswer(int index) {
        WebElement faqSection = driver.findElement(headerFAQSection);
        scrollToElement(faqSection);

        WebElement questionElement = driver.findElement(By.xpath(String.format(questionPath, index)));
        String questionText = questionElement.getText();
        questionElement.click();

        WebElement answerElement = driver.findElement(By.xpath(String.format(answerPath, index)));
        String answerText = answerElement.getText();

        return new Question(questionText, answerText);
    }
}