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
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
        }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
/*        driver = new ChromeDriver();
        driver.get("http://localhost:9999/");*/

    }

    @Test
    public void test() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Андрей");
        elements.get(1).sendKeys("+79201234567");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button_view_extra")).click(); //Отправить
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void testNameHyphen() {
        driver.findElement(By.name("name")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.name("phone")).sendKeys("+79201234567");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void testNameEnglish() {
        driver.findElement(By.name("name")).sendKeys("Boris Johnson");
        driver.findElement(By.name("phone")).sendKeys("+79201234567");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void testNameNumber() {
        driver.findElement(By.name("name")).sendKeys("Boris1 Johnson1");
        driver.findElement(By.name("phone")).sendKeys("+79201234567");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        String text = driver.findElement(By.className("input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void testNumberPlus() {
        driver.findElement(By.name("name")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.name("phone")).sendKeys("89201234567");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));
        //System.out.println(elements.get(1).getText());

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);

    }

    @Test
    public void testNumberTen() {
        driver.findElement(By.name("name")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.name("phone")).sendKeys("+7920123456");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void testNumberTwelve() {
        driver.findElement(By.name("name")).sendKeys("Михаил Салтыков-Щедрин");
        driver.findElement(By.name("phone")).sendKeys("+792012345678");
        driver.findElement(By.className("checkbox__box")).click(); //чекбокс
        driver.findElement(By.className("button__text")).click(); // Отправить
        List<WebElement> elements = driver.findElements(By.className("input__sub"));

        String expected = elements.get(1).getText();
        String actual = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

}
