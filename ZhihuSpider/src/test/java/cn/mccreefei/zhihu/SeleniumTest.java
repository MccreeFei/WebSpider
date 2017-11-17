package cn.mccreefei.zhihu;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-17 13:30
 */
public class SeleniumTest {
    private ResourceBundle resource;
    @Before
    public void initContext(){
        resource = ResourceBundle.getBundle("zhihu");
    }
    @Test
    public void testSelenium(){
        String chrome_driver_path = resource.getString("CHROME_DRIVER_PATH");
        System.setProperty("webdriver.chrome.driver", chrome_driver_path);
        WebDriver driver = new ChromeDriver();

        driver.get("http://www.mccreefei.cn");

        WebElement html = driver.findElement(By.tagName("html"));
        String content = html.getAttribute("outerHTML");
        System.out.println(content);

        //Close the browser
        driver.quit();
    }
}
