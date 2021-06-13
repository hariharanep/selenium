import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HelloSeleniumTest {

    WebDriver driver;
    Actions act;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        driver = new ChromeDriver();
        act = new Actions(driver);
    }

    @Test
    public void drop_down() {
        //drop down
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropDown = driver.findElement(By.id("dropdown"));
        WebElement option_one = driver.findElement(By.xpath("//*[@id=\"dropdown\"]/option[1]"));
        WebElement option_two = driver.findElement(By.xpath("//*[@id=\"dropdown\"]/option[2]"));
        act.contextClick(dropDown).build().perform();
        option_one.click();
        Assert.assertTrue(option_one.isSelected());
        Assert.assertTrue(option_two.isSelected() == false);

    }

    @Test
    public void hovers() {
        //hovers
        driver.get("https://the-internet.herokuapp.com/hovers");
        WebElement img = driver.findElement(By.cssSelector("#content > div > div:nth-child(3) > img"));
        act.moveToElement(img).build().perform();
        WebElement str = driver.findElement(By.cssSelector("#content > div > div:nth-child(3) > div > h5"));
        Assert.assertTrue(str.isDisplayed());
    }

    @Test
    public void context_menu() {
        //context_menu
        driver.get("https://the-internet.herokuapp.com/context_menu");
        WebElement img = driver.findElement(By.id("hot-spot"));
        act.contextClick(img).build().perform();
        driver.switchTo().alert().accept();
    }

    @Test
    public void right_key() {
        //right_key
        driver.get("https://the-internet.herokuapp.com/key_presses");
        WebElement s = driver.findElement(By.id("target"));
        act.moveToElement(s).build().perform();
        act.click(s).build().perform();
        act.sendKeys(Keys.ARROW_RIGHT).build().perform();
        WebElement str = driver.findElement(By.id("result"));
        Assert.assertTrue(str.getText().contains("RIGHT"));
    }

    @Test
    public void clickable_icon() {
        //clickable icon
        driver.get("https://ultimateqa.com/simple-html-elements-for-automation/");
        WebElement s = driver.findElement(By.linkText("Clickable Icon"));
        Assert.assertTrue(s.getAttribute("href").equals("https://ultimateqa.com/link-success/"));
        Assert.assertTrue(s.getCssValue("background-origin").equals("padding-box"));
    }

    @Test
    public void windowFrames() {
        driver.navigate().to("https://the-internet.herokuapp.com/windows");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('https://the-internet.herokuapp.com/windows/new')");

        String originalWindow = driver.getWindowHandle();
        Set handles = driver.getWindowHandles();
        handles.remove(originalWindow);

        String nextWindow = String.valueOf(handles.iterator().next());
        driver.switchTo().window(nextWindow);

        Assert.assertTrue(driver.getTitle().equals("New Window"));
        driver.close();
        driver.switchTo().window(originalWindow);
        Assert.assertTrue(driver.getTitle().equals("The Internet"));
    }

    @Test
    public void frames() {
        driver.navigate().to("https://the-internet.herokuapp.com/nested_frames");
        WebElement defaultFrame = driver.findElement(By.name("frame-top"));
        driver.switchTo().frame(1);
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().equals("BOTTOM"));

        driver.switchTo().parentFrame();
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText().equals("LEFT"));

        driver.switchTo().defaultContent();
        Assert.assertTrue(driver.findElement(By.name("frame-top")).getSize().height > 0);


    }

    @Test
    public void alerts() {
        driver.navigate().to("http://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//*[@onclick=\"jsAlert()\"]")).click();
        driver.switchTo().alert().dismiss();

        driver.findElement(By.xpath("//*[@onclick=\"jsConfirm()\"]")).click();
        driver.switchTo().alert().accept();

        driver.findElement(By.xpath("//*[@onclick=\"jsPrompt()\"]")).click();
        Alert input_alert = driver.switchTo().alert();
        String text = input_alert.getText();
        input_alert.sendKeys("Here");
        Assert.assertTrue(text.equals("I am a JS prompt"));
    }

    @Test
    public void exam() throws InterruptedException {
        driver.navigate().to("https://example.cypress.io/commands/actions");
        driver.findElement(By.cssSelector(".action-focus")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//*[@for=\"password1\"]")).getAttribute("style").contains("color: orange;"));

        driver.navigate().to("https://example.cypress.io/commands/cookies");
        driver.findElement(By.xpath("//*[@id=\"getCookie\"]/div/div[2]/div/button")).click();
        Cookie found = driver.manage().getCookieNamed("token");
        Assert.assertTrue(found.getValue().equals("123ABC"));

        driver.navigate().to("https://ultimateqa.com/complicated-page/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(1000);

        js.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
        Thread.sleep(1000);
    }





    /*@Test
    public void firstTest() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");

        //2. Navigate to the URL
        driver.get("https://www.saucedemo.com/");


        WebDriverWait wait = new WebDriverWait(driver, 10);
        //3. Find element
        //4. check the state
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user-name")));

        //5. take action
        //6. record the result
        Assert.assertTrue(element.isDisplayed());

    }*/

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /*@Test
    public void typesLocators()  {
        //2. Navigate to the URL
        driver.get("https://www.saucedemo.com/");

        WebElement element;
        element = driver.findElement(By.id("user-name"));
        //driver.findElement(By.className("input_error"));
        //driver.findElement(By.tagName("input"));
        //driver.findElement(By.cssSelector("#user-name"));
        //driver.findElement(By.xpath("//*[@id=\"user-name\"]"));
        //driver.findElement(By.linkText("Click Me Using This Link Text"));

    }*/

}
