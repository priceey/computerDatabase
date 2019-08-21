/**
 * 
 */
package PageFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

/**
 * @author Steven Price
 *
 */
public class EditComputerPage {

	WebDriver driver;

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	@FindBy(xpath = "/html[1]/body[1]/section[1]/h1[1]")
	WebElement headerTxt;

	@FindBy(id = "name")
	WebElement nameTxtBox;

	@FindBy(id = "introduced")
	WebElement introducedTxtBox;

	@FindBy(id = "discontinued")
	WebElement discontinuedTxtBox;

	@FindBy(id = "company")
	WebElement companyCbBox;

	@FindBy(xpath = "//input[@class='btn primary']")
	WebElement saveThisComputerBtn;

	@FindBy(xpath = "//input[@class='btn danger']")
	WebElement deleteComputerBtn;

	public EditComputerPage(WebDriver driver) {

		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	/**
	 * Gets the text of the header of the page
	 * 
	 * @return headerTxt
	 */
	public String GetHeaderText() {

		return headerTxt.getText();
	}

	/**
	 * Clears and populates the computer name field
	 * 
	 * @param compName
	 */
	public void PopulateComuterNameField(String compName) {

		nameTxtBox.clear();
		nameTxtBox.sendKeys(compName);
	}

	/**
	 * Clears and populates the intro date field
	 * 
	 * @param introDate
	 * @return EditComputerPage
	 */
	public EditComputerPage PopulateIntroductionDate(String introDate) {

		introducedTxtBox.clear();
		introducedTxtBox.sendKeys(introDate);
		return this;
	}

	/**
	 * Clears and populates the discontinued date field
	 * 
	 * @param disconDate
	 * @return EditComputerPage
	 */
	public EditComputerPage PopulateDiscontinuedDate(String disconDate) {
		discontinuedTxtBox.clear();
		discontinuedTxtBox.sendKeys(disconDate);
		return this;
	}

	/**
	 * populates a random company from the drop down and returns the value as a
	 * string
	 * 
	 * @return String containing the selected company
	 */
	public String PopulateRandomCompany() {

		Select dropDownSelect = new Select(companyCbBox);

		// Get a list of all the available drop down values for company name
		List<WebElement> optionsList = dropDownSelect.getOptions();

		// Check there to make sure that company names have been returned.
		Assert.assertTrue(optionsList.size() > 2, "Error: The list of companies is too small");

		// Generate a random number with in range
		// using 1 as the base to avoid selecting the default value
		int randNumber = ThreadLocalRandom.current().nextInt(1, optionsList.size());

		// select a value and return the selected name
		dropDownSelect.selectByIndex(randNumber);
		WebElement optionElement = optionsList.get(randNumber);
		String optionValue = optionElement.getText();

		return optionValue;
	}

	/**
	 * Clicks the save computer button and navigates back to the home page
	 * 
	 * @return ComputerDatabaseHomePage
	 */
	public ComputerDatabaseHomePage ClickSaveComputerButton() {

		saveThisComputerBtn.click();

		return new ComputerDatabaseHomePage(driver);
	}

	/**
	 * Clicks the delete button and navigates back to the home page
	 * 
	 * @return ComputerDatabaseHomePage
	 */
	public ComputerDatabaseHomePage ClickDeleteComputerBtn() {

		deleteComputerBtn.click();

		return new ComputerDatabaseHomePage(driver);
	}

	/**
	 * Read the text of the name field and return a string
	 * 
	 * @return computer name field text
	 */
	public String GetComputerName() {

		return nameTxtBox.getAttribute("value");
	}

	/**
	 * Gets the introduced date
	 * 
	 * @return a string containing the date
	 */
	public String GetIntroDate() {

		return introducedTxtBox.getAttribute("value");
	}

	/**
	 * Gets the discontinued date
	 * 
	 * @return a string containing the date
	 */
	public String GetDiscontinuedDate() {

		return discontinuedTxtBox.getAttribute("value");
	}

	/**
	 * Gets the company name
	 * 
	 * @return a string containing the company name
	 */
	public String GetCompanyName() {
		Select select = new Select(companyCbBox);
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}
}
