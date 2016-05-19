package goeuro.travel.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * GoEuro Travel Planner: Berlin - Prague
 *
 * @author Sudheendra Rao
 */
public class Planner {
    private static String fare1Xpath = "//div[@id='results-train']/div[@class='custom']/div[1]/descendant::span[@class='currency-beforecomma']";
    private static String fare2Xpath = "//div[@id='results-train']/div[@class='custom']/div[2]/descendant::span[@class='currency-beforecomma']";
    private static String fare3Xpath = "//div[@id='results-train']/div[@class='custom']/div[3]/descendant::span[@class='currency-beforecomma']";
    private static String fare4Xpath = "//div[@id='results-train']/div[@class='custom']/div[4]/descendant::span[@class='currency-beforecomma']";
    private static String fare5Xpath = "//div[@id='results-train']/div[@class='custom']/div[5]/descendant::span[@class='currency-beforecomma']";
    private static String fare6Xpath = "//div[@id='results-train']/div[@class='custom']/div[6]/descendant::span[@class='currency-beforecomma']";
    private static String fare7Xpath = "//div[@id='results-train']/div[@class='custom']/div[7]/descendant::span[@class='currency-beforecomma']";

    static WebDriver driver;
    static Wait<WebDriver> wait;

    public static void main(String[] args) {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://goeuro.com/");

        boolean result;
        try {
            result = CheapestTrainFare();
	    System.out.println("Result: " + result);
        } catch(Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            driver.close();
        }

        System.out.println("Test " + (result? "passed." : "failed."));
        if (!result) {
            System.exit(1);
        }
    }

    private static boolean CheapestTrainFare() {
        //travel from
        driver.findElement(By.id("from_filter")).sendKeys("Berlin, Germany");

        //travel to
        driver.findElement(By.id("to_filter")).sendKeys("Prague, Czech Republic");

        WebElement elementSearch = wait.until(ExpectedConditions.elementToBeClickable(By.id("search-form__submit-btn")));

        // click search
        driver.findElement(By.id("search-form__submit-btn")).sendKeys(Keys.ENTER);

        // Wait for search to complete
        WebElement elementSelect = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Select")));

	int fare1 = Integer.parseInt(driver.findElement(By.xpath(fare1Xpath)).getText());
	int fare2 = Integer.parseInt(driver.findElement(By.xpath(fare2Xpath)).getText());
	int fare3 = Integer.parseInt(driver.findElement(By.xpath(fare3Xpath)).getText());
	int fare4 = Integer.parseInt(driver.findElement(By.xpath(fare4Xpath)).getText());
	int fare5 = Integer.parseInt(driver.findElement(By.xpath(fare5Xpath)).getText());
	int fare6 = Integer.parseInt(driver.findElement(By.xpath(fare6Xpath)).getText());
	int fare7 = Integer.parseInt(driver.findElement(By.xpath(fare7Xpath)).getText());

	System.out.println("fare1: "+ fare1);
	System.out.println("fare2: "+ fare2);
	System.out.println("fare3: "+ fare3);
	System.out.println("fare4: "+ fare4);
	System.out.println("fare5: "+ fare5);
	System.out.println("fare6: "+ fare6);
	System.out.println("fare7: "+ fare7);

        // Compare fares
        return ((fare1 <= fare2) &&  (fare2 <= fare3) && (fare3 <= fare4) && (fare4 <= fare5) && (fare5 <= fare6) && (fare6 <= fare7));
    }
}
