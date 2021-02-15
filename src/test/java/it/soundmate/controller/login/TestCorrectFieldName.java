package it.soundmate.controller.login;

import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.database.dao.SoloDao;
import it.soundmate.database.dao.UserDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

//Matteo D'Alessandro
class TestCorrectFieldName {
    static private WebDriver driver;
    static private Map<String, Object> vars;
    static JavascriptExecutor js;
    static final UserDao userDao = new UserDao();
    static final SoloDao soloDao = new SoloDao(userDao);


    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriverOV.exe");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        RegisterSoloBean registerSoloBean = new RegisterSoloBean("pluto@gmail.com", "pluto", "Pluto", "Topolino", "Disneyland");
        soloDao.registerSolo(registerSoloBean);
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
        userDao.deleteAll();
    }
    @Test
    void testWelcomingField() {
        driver.get("http://localhost:8080/SoundmateFX_war_exploded/main/web/jsp/index.jsp");
        driver.manage().window().setSize(new Dimension(945, 1020));
        driver.findElement(By.id("email-field")).click();
        driver.findElement(By.xpath("//*[@id=\"email-field\"]")).sendKeys("pluto@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"password-field\"]")).sendKeys("pluto");
        driver.findElement(By.xpath("/html/body/div[3]/div/div/form[1]/input[3]")).click();
        Assertions.assertEquals("Welcome, Pluto Topolino", driver.findElement(By.xpath("//*[@id=\"welcomeUser\"]")).getText());
    }
}
