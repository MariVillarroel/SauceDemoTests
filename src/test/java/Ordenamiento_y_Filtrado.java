import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Ordenamiento_y_Filtrado extends BaseTest{

    @Test
    public void cambiaOrdenAZaZANoAlteraEstadoAddRemove() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='username']")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='password']")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
        loginButton.click();

        // Agregar producto al carrito
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addToCartButton.click();

        // Solo verificar que el botón de Remove está presente
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='remove-sauce-labs-backpack']")));

        // Cambiar ordenamiento a Z-A
        WebElement sortDropdown = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='product-sort-container']")));

        Select select = new Select(sortDropdown);
        select.selectByVisibleText("Name (Z to A)");

        // **SOLUCIÓN: Esperar y re-localizar el elemento después del ordenamiento
        WebElement removeButtonAfterSort = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='remove-sauce-labs-backpack']")));

        String removeButtonTextAfterSort = removeButtonAfterSort.getText();
        Assertions.assertEquals("Remove", removeButtonTextAfterSort);
    }

    @Test
    public void ResetAppCambiaBotonesaAddToCart() {
        // Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='username']")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='password']")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
        loginButton.click();

        // Agregar productos al carrito
        WebElement addBackpackButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addBackpackButton.click();

        WebElement addBikeLightButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']")));
        addBikeLightButton.click();

        // Hacer clic en el menu
        WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
        menuButton.click();

        // **ESPERAR a que el menu esté visible y el link sea interactable
        WebElement resetLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='reset-sidebar-link']")));
        resetLink.click();

        // **ESPERAR a que el botón de cerrar esté disponible y sea clickeable
        WebElement closeMenuButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("react-burger-cross-btn")));
        closeMenuButton.click();

        // Verificar que los botones Remove ya NO están presentes
        List<WebElement> removeBackpackButtons = driver.findElements(By.cssSelector("[data-test='remove-sauce-labs-backpack']"));
        Assertions.assertTrue(removeBackpackButtons.isEmpty());

        List<WebElement> removeBikeLightButtons = driver.findElements(By.cssSelector("[data-test='remove-sauce-labs-bike-light']"));
        Assertions.assertTrue(removeBikeLightButtons.isEmpty());

        // Verificar que los botones Add to Cart están presentes nuevamente
        WebElement addBackpackButtonAfterReset = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        Assertions.assertTrue(addBackpackButtonAfterReset.isDisplayed());

        WebElement addBikeLightButtonAfterReset = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']")));
        Assertions.assertTrue(addBikeLightButtonAfterReset.isDisplayed());
    }
}
