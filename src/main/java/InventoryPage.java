import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {
    private final WebDriver driver;
    public InventoryPage (WebDriver driver) {
        this.driver = driver;

    }

    public void goToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
    }
}
