import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Checkout extends BaseTest {

    @Test
    public void StepOneBloqueaAvance (){
        // 1. Login
        WebElement userNameTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='username']")));
        userNameTextBox.sendKeys("standard_user");

        WebElement passwordTextBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='password']")));
        passwordTextBox.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
        loginButton.click();

        // 2. Agregar producto al carrito
        WebElement addToCartButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")));
        addToCartButton.click();

        // 3. Ir al carrito
        WebElement cartIcon = driver.findElement(By.cssSelector(".shopping_cart_link"));
        cartIcon.click();

        // 4. Iniciar checkout
        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='checkout']")));
        checkoutButton.click();

        // 5. Verificar que estamos en checkout (usando presencia de elementos como en base)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='firstName']")));

        // 6. Intentar continuar SIN completar ningún campo
        WebElement continueButton = driver.findElement(By.cssSelector("[data-test='continue']"));
        continueButton.click();

        // 7. Verificar que aparece mensaje de error
        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));

        Assertions.assertTrue(errorMessage.isDisplayed());

        // 8. Completar solo First Name
        WebElement firstNameField = driver.findElement(By.cssSelector("[data-test='firstName']"));
        firstNameField.sendKeys("Juan");

        continueButton.click();

        // 9. Verificar que sigue mostrando error
        errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        Assertions.assertTrue(errorMessage.isDisplayed());

        // 10. Completar Last Name
        WebElement lastNameField = driver.findElement(By.cssSelector("[data-test='lastName']"));
        lastNameField.sendKeys("Perez");

        continueButton.click();

        // 11. Verificar que sigue mostrando error
        errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='error']")));
        Assertions.assertTrue(errorMessage.isDisplayed());

        // 12. Completar Postal Code y verificar que AVANZA (el error desaparece)
        WebElement postalCodeField = driver.findElement(By.cssSelector("[data-test='postalCode']"));
        postalCodeField.sendKeys("12345");

        continueButton.click();

        // 13. Verificar que avanzó (usando presencia de elementos de la siguiente página)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-test='finish']")));
    }
}
