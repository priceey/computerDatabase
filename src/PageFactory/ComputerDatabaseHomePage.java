package PageFactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ComputerDatabaseHomePage {

	WebDriver driver;

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	@FindBy(xpath = "/html[1]/body[1]/section[1]/h1[1]")
	WebElement computerCountTxt;

	@FindBy(id = "searchbox")
	WebElement searchBoxTxtBox;

	@FindBy(id = "searchsubmit")
	WebElement filterByNameBtn;

	@FindBy(id = "add")
	WebElement addComputerBtn;

	@FindBy(xpath = "//div[@class='alert-message warning']")
	WebElement alertMessage;

	@FindBy(xpath = "//table[@class='computers zebra-striped']")
	WebElement computersFoundTable;
	
	@FindBy(xpath = "//a[contains(text(),'Play sample application')]")
	WebElement homeLink;

	public ComputerDatabaseHomePage(WebDriver driver) {
		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	/**
	 * Clears, populates and clicks the computer search field
	 * @param computerName
	 * @return
	 */
	public ComputerDatabaseHomePage SearchForComputerName(String computerName) {

		searchBoxTxtBox.clear();
		searchBoxTxtBox.sendKeys(computerName);
		filterByNameBtn.click();

		return this;
	}

	/**
	 * Click the add computer button
	 * @return
	 */
	public AddComputerPage ClickAddComputerButton() {

		addComputerBtn.click();
		return new AddComputerPage(driver);
	}


	/**
	 * Get amount of computers found text
	 * @return
	 */
	public String GetComutersFoundText() {

		return computerCountTxt.getText();
	}

	/**
	 * Gets the alert message text
	 * @return
	 */
	public String GetAlertMessageText() {

		return alertMessage.getText();
	}

	/**
	 * Returns an array of the elements from the computer page
	 * @return
	 */
	@SuppressWarnings("null")
	public String[] ParseComuterTable() {

		// To locate rows of table.
		List<WebElement> rows_table = computersFoundTable.findElements(By.tagName("tr"));

		// To calculate no of rows In table.
		int rows_count = rows_table.size();

		// Array to store the values in the table
		String[] computerDetails = new String[5];

		for (int row = 0; row < rows_count; row++) {

			// To locate columns(cells) of that specific row.
			List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));

			// To calculate no of columns (cells). In that specific row.
			int columns_count = Columns_row.size();

			// Loop will execute till the last cell of that specific row.
			for (int column = 0; column < columns_count; column++) {

				// Store the cell value to an array for parsing later.
				computerDetails[column] = Columns_row.get(column).getText();
			}
		}

		return computerDetails;
	}

	/**
	 * Searches the table for the computer name and clicks the element
	 * @param computerName
	 * @return EditComputerPage
	 */
	public EditComputerPage LocatateAndClickTableElement(String computerName) {

		List<WebElement> allrows = computersFoundTable.findElements(By.tagName("tr"));
		
		for(WebElement row: allrows){
		    List<WebElement> Cells = row.findElements(By.tagName("td"));
		    for(WebElement Cell:Cells){
		        if (Cell.getText().contains(computerName))
		            Cell.findElement(By.partialLinkText(computerName)).click();
		        break;
		    }
		}
		
		return new EditComputerPage(driver);
	}
	
	/**
	 * Click the link at the top to return to the home page
	 * @return ComputerDatabaseHomePage
	 */
	public ComputerDatabaseHomePage ClickHomeLink() {
		
		homeLink.click();
		
		return this;
	}
}
