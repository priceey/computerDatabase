package PageFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class AddComputerPage {

	WebDriver driver;
	
    /**
     * All WebElements are identified by @FindBy annotation
     */
	
    @FindBy(xpath="/html[1]/body[1]/section[1]/h1[1]")
    WebElement headerTxt;
    
    @FindBy(id="name")
    WebElement nameTxtBox;
    
    @FindBy(id="introduced")
    WebElement introducedTxtBox;
    
    @FindBy(id="discontinued")
    WebElement discontinuedTxtBox;
    
    @FindBy(id="company")
    WebElement companyCbBox;
    
    @FindBy(xpath="//input[@class='btn primary']")
    WebElement createThisComputerBtn;
    
    @FindBy(xpath="//div[@class='clearfix error']//div[@class='input']")
    WebElement nameErrorMessageField;
     
	public AddComputerPage(WebDriver driver) {
		
        this.driver = driver;

        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
	}
	
	/**
	 * Get header text
	 * 
	 * @return string containing header text
	 */
	public String GetHeaderText() {
		
		return headerTxt.getText();
	}
	
	/**
	 * Populates the computer search field text box
	 * @param compName
	 */
	public void PopulateComuterNameField(String compName) {
		
		nameTxtBox.sendKeys(compName);
	}
	
	/**
	 * Populates the introduced date
	 * @param introDate
	 * @return AddComputerPage
	 */
	public AddComputerPage PopulateIntroductionDate(String introDate) {
		
		introducedTxtBox.sendKeys(introDate);
		return this;
	}
	
	/**
	 * Populates the discontinued date
	 * @param disconDate
	 * @return AddComputerPage
	 */
	public AddComputerPage PopulateDiscontinuedDate(String disconDate) {
		
		discontinuedTxtBox.sendKeys(disconDate);
		return this;
	}
	
	/**
	 * Populates a random company name and returns the value selected
	 * @return String containing company name
	 */
	public String PopulateRandomCompany() {
		
		Select dropDownSelect = new Select(companyCbBox);

		// Get a list of all the available drop down values for company name
		List<WebElement> optionsList = dropDownSelect.getOptions();
		
		// Check there to make sure that company names have been returned.
		Assert.assertTrue(optionsList.size()> 2, "Error: Company drop down does not contain enough values");
		
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
	 * Clicks the new computer button and navigates to the home page
	 * @return ComputerDatabaseHomePage
	 */
	public ComputerDatabaseHomePage ClickAddNewComputerButton() {
		
		createThisComputerBtn.click();
		
		return new ComputerDatabaseHomePage(driver);
	}
	
	/**
	 * Checks to see if the error highlighting is displayed
	 * @return boolean
	 */
	public Boolean IsNameErrorFieldDisplayed() {
		Boolean isDisplayed = nameErrorMessageField.isDisplayed();
		return isDisplayed;
	}
}
