import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Inventario extends BaseTest{

    @Test
    public void AgregarDesdeLista(){
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='username']")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='password']")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
        loginButton.click();

        // Verificar contador inicial (debe estar vacío)
        List<WebElement> cartBadge = driver.findElements(By.cssSelector(".shopping_cart_badge"));
        Assertions.assertTrue(cartBadge.isEmpty());

        // Hacer clic en "Add to cart"
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addToCartButton.click();

        // Verificar que el contador muestra "1"
        WebElement cartBadgeAfterAdd = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".shopping_cart_badge")));
        String badgeText = cartBadgeAfterAdd.getText();
        Assertions.assertEquals("1", badgeText);

        // Verificar que el boton cambió a "Remove"
        WebElement removeButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='remove-sauce-labs-backpack']")));
        String buttonText = removeButton.getText();
        Assertions.assertEquals("Remove", buttonText);
    }
}
