import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestApp {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @Test
    public void test() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Андрей");
        elements.get(1).sendKeys("+79201234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameHyphen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79201234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameEnglish() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Boris Johnson");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79201234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testNameNumber() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Boris1 Johnson1");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79201234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    public void testPhoneNumberNoPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89201234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNumberTen() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7920123456");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNumberTwelve() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+792012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79201234567");
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckBoxAndNamePhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String actual = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void testNameNull() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+792012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String expected = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String actual = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void testPhoneNull() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[role='button']")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
