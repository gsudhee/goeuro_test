package goeuro.travel.test;

import java.util.ArrayList;
import java.util.Collections;

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
	private static String fromCity = "Berlin, Germany";
	private static String toCity = "Prague, Czech Republic";
	private static String travelFrom = "from_filter";
	private static String travelTo = "to_filter";
	private static String travelSearch = "search-form__submit-btn";
	private static String selectButton = "Select";
	private static String baseXpath = "//div[@id='results-train']/div[@class='custom']/div";
	private static String currencyXpath = "/descendant::span[@class='currency-beforecomma']";

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
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			driver.close();
		}

		System.out.println("Test " + (result ? "passed." : "failed."));
	}

	private static boolean CheapestTrainFare() {
		ArrayList<Integer> fareList = new ArrayList<>();
		ArrayList<Integer> fareListSorted = new ArrayList<>();

		// travel from
		driver.findElement(By.id(travelFrom)).sendKeys(fromCity);

		// travel to
		driver.findElement(By.id(travelTo)).sendKeys(toCity);

		WebElement elementSearch = wait.until(ExpectedConditions
				.elementToBeClickable(By.id(travelSearch)));

		// click search
		driver.findElement(By.id(travelSearch)).sendKeys(Keys.ENTER);

		// Wait for search to complete
		WebElement elementSelect = wait.until(ExpectedConditions
				.elementToBeClickable(By.linkText(selectButton)));

		for (int i = 1; i < 25; i++) {
			String fareXpath = baseXpath + "[" + i + "]" + currencyXpath;
			try {
				fareList.add(Integer.parseInt(driver.findElement(
						By.xpath(fareXpath)).getText()));
			} catch (Exception ex) {
				break;
			}
		}
		fareListSorted.addAll(fareList);
		Collections.sort(fareListSorted);

		int fareIndex = 1;
		for (int fare : fareList)
			System.out.println("Fare " + fareIndex++ + ": " + fare);

		// Compare fares
		if (fareList.size() > 0)
			return fareList.equals(fareListSorted);
		else
			return false;
	}
}
