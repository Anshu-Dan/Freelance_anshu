import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Newfreelance {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\eclipse-workspace\\Sample\\mydriver\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = null;
        try {
            // Initialize driver
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Login to the site
            login(driver);

            // Process updates
            processUpdates(driver, 2); // Specify duration in minutes
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure the driver quits even if there's an exception
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void login(WebDriver driver) throws InterruptedException {
        driver.get("https://www.freelancer.in/dashboard");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("emailOrUsernameInput")));
        emailInput.sendKeys("devilknight882@gmail.com");

        WebElement passwordInput = driver.findElement(By.id("passwordInput"));
        passwordInput.sendKeys("Alok1234@");

        WebElement loginButton = driver.findElement(By.xpath("//*[text()=' Log in ']"));
        loginButton.click();

        Thread.sleep(4000);

        // Handle optional popups
        try {
            WebElement noThanksButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=' No, thanks ']")));
            noThanksButton.click();
        } catch (Exception e) {
            System.out.println("No 'No, thanks' button found.");
        }

        try {
            WebElement closeButton = driver.findElement(By.xpath("//*[@aria-label='Close']"));
            closeButton.click();
        } catch (Exception e) {
            System.out.println("No close button found.");
        }

        try {
            WebElement minimizeInbox = driver.findElement(By.xpath("//*[@title='Minimize Inbox']"));
            minimizeInbox.click();
        } catch (Exception e) {
            System.out.println("No minimize button found.");
        }
    }

    public static void processUpdates(WebDriver driver, int durationMinutes) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long runDuration = durationMinutes * 60 * 1000;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        while (System.currentTimeMillis() - startTime < runDuration) {
            try {
                WebElement firstUpdate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='FeedItem ng-star-inserted'])[1]")));
                firstUpdate.click();

                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='SummaryColumn ng-star-inserted']//div[@class='ContentWrapper']//*[2]")));
                Thread.sleep(4000);

                int value = Integer.parseInt(element.getText().trim());
                Thread.sleep(4000);

                if (value < 20) {
                    System.out.println("Value is less than 20. Staying on the current page.");

                    WebElement promt = driver.findElement(By.xpath("//*[@class='ng-untouched ng-pristine ng-valid']/div[@class='ProjectDescription']"));
                    String description = promt.getText();
                    System.out.println("My description: " + description);

                    openai ai = new openai();
                    String AI_generated_description = ai.getOpenAIResponse(description);
                    System.out.println("UPDATED AI DESCRIPTION: " + AI_generated_description);

                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0, 1000);");

                    try {
                        WebElement bid = driver.findElement(By.xpath("//*[text()=' Describe your proposal (minimum 100 characters) ']"));
                        String elementtext = bid.getText().trim();
                        System.out.println(elementtext);

                        if (elementtext != null) {
                            WebElement bidtextbox = driver.findElement(By.xpath("(//*[@placeholder='What makes you the best candidate for this project?'])[2]"));
                            System.out.println("Bid not: " + bidtextbox);
                            Thread.sleep(8000);
                            bidtextbox.sendKeys(AI_generated_description);

                            Thread.sleep(4000);
                            js.executeScript("window.scrollBy(0, 1000);");
                            Thread.sleep(8000);
                            driver.findElement(By.xpath("//*[text()=' Place Bid ']"))
                                  .click();
                            Thread.sleep(30000);
                            driver.navigate().back();
                            Thread.sleep(4000);

                            js.executeScript("window.scrollTo(0, 0);");
                        } else {
                            System.out.println("Place bid is not available on the screen");
                            driver.navigate().back();
                        }
                    } catch (Exception e) {
                        System.out.println("'Place Bid' button not found. Navigating back and restarting the process." + e);
                        driver.navigate().back();
                    }
                } else {
                    System.out.println("Value is greater than 20. Navigating back to the previous page.");
                    Thread.sleep(10000);
                    driver.navigate().back();
                }
            } catch (Exception e) {
                System.out.println("An error occurred while processing updates: " + e.getMessage());
            }
        }
    }
}

// Placeholder class for OpenAI integration
class openai {
    public String getOpenAIResponse(String prompt) {
        // Simulate AI response generation
        return "AI-generated response based on: " + prompt;
    }
}
