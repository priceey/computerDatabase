package Test;

import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import Objects.ComputerDetails;
import PageFactory.AddComputerPage;
import PageFactory.ComputerDatabaseHomePage;
import PageFactory.EditComputerPage;
import Utilities.DateUtils;

public class BackbaseTestSuite {

	WebDriver driver;

	ComputerDatabaseHomePage comHomPage;
	AddComputerPage addComPage;
	ComputerDetails comDetObj;
	EditComputerPage editComPage;

	@BeforeTest
	public void setup() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Kirsty Price\\Documents\\Steve\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://computer-database.herokuapp.com/computers");
	}

	@Test(priority = 1)
	public void TestCreateNewComputer() throws Throwable {

		// Create a new instance of the home page
		comHomPage = new ComputerDatabaseHomePage(driver);

		// Create an instance of the object used to store all computer data
		comDetObj = new ComputerDetails();

		// Find out and assert how many computers are found - using this to establish we
		// are on the correct page
		String numCompFound = comHomPage.GetComutersFoundText();
		Assert.assertTrue(numCompFound.toLowerCase().contains("computers found"), "Error: Not on the home page");

		// store the amount of computers found
		int origComputersFound = Integer.parseInt(numCompFound.replace(" computers found", ""));

		// Navigates to the add computer page
		addComPage = comHomPage.ClickAddComputerButton();

		// Ensure we are on the correct page
		String headerTxt = addComPage.GetHeaderText();
		Assert.assertTrue(headerTxt.toLowerCase().contains("add a computer"), "Error: Not on the Addd a computer page");

		// Generate a random computer name
		comDetObj.setName(Utilities.StringUtils.GenerateRandomString(8));

		// populate the new computer fields
		addComPage.PopulateComuterNameField(comDetObj.getName());

		// Store data in the computer object
		comDetObj.setIntroDate(Utilities.DateUtils.GetRandomDate(""));
		comDetObj.setDiscoutinuedDate(Utilities.DateUtils.GetRandomDate(comDetObj.getIntroDate()));

		// Populate fields
		addComPage.PopulateIntroductionDate(comDetObj.getIntroDate())
				.PopulateDiscontinuedDate(comDetObj.getDiscoutinuedDate());

		comDetObj.setCompany(addComPage.PopulateRandomCompany());

		comHomPage = addComPage.ClickAddNewComputerButton();

		// get the computers found header and parse it to an it
		int newComputersFound = Integer.parseInt(comHomPage.GetComutersFoundText().replace(" computers found", ""));

		// Assess the value has increased by 1
		Assert.assertEquals(newComputersFound, origComputersFound + 1, "Error: Computer count has not increased");

		// Assess that the alert text shows that a computer has been created
		String alertTxt = comHomPage.GetAlertMessageText();
		Assert.assertTrue(alertTxt.contains("Done! Computer " + comDetObj.getName() + " has been created"),
				"Error: Alert message missing for computer created.");

	}

	@Test(priority = 2)
	public void TestReadComputerData() throws Throwable {

		// using saved values search for the computer under test
		comHomPage.SearchForComputerName(comDetObj.getName());
		String computerFoundResult = comHomPage.GetComutersFoundText();

		// Assert only one computer has been found from the header text
		Assert.assertTrue(computerFoundResult.contains("One computer found"),
				"Error: Not matching text for one computer");

		// parse the table into an array
		String computerData[] = comHomPage.ParseComuterTable();

		// assert the correct name has been found
		Assert.assertTrue(computerData[0].contains(comDetObj.getName()),
				"Error: Returned computer name not matching stored name");

		// convert the date to the table format and assert the correct date has been
		// returned
		String convertedDateString = DateUtils.DateFormatter(comDetObj.getIntroDate(), "yyyy-MM-dd", "dd MMM yyyy");
		Assert.assertTrue(computerData[1].contains(convertedDateString),
				"Error: Returned introduced date date not matching stored value");

		// convert the date to the table format and assert the correct date has been
		// returned
		convertedDateString = DateUtils.DateFormatter(comDetObj.getDiscoutinuedDate(), "yyyy-MM-dd", "dd MMM yyyy");
		Assert.assertTrue(computerData[2].contains(convertedDateString),
				"Error: Discontinued date not matching stored date");

		// Assert the correct company name has been returned
		Assert.assertTrue(computerData[3].contains(comDetObj.getCompany()),
				"Error: Company name returned not matching stored value");

		// Select the computer to check the Edit page has the correct details
		editComPage = comHomPage.LocatateAndClickTableElement(comDetObj.getName());

		String headerTxt = editComPage.GetHeaderText();

		// Assert we are on the right page
		Assert.assertTrue(headerTxt.contains("Edit computer"), "Error: not on the Edit Computer page");

		// Get the computer name string and compare it to the saved string
		String returnCompName = editComPage.GetComputerName();
		Assert.assertTrue(returnCompName.contains(comDetObj.getName()),
				"Error: Returned computer name and saved name do not match");

		// Get the intro date and confirm they match
		String introDate = editComPage.GetIntroDate();
		Assert.assertTrue(introDate.contains(comDetObj.getIntroDate()),
				"Error: Returned intro date and saved date do not match");

		// Get the discontinued date and check it it against the saved value
		String discontinuedDate = editComPage.GetDiscontinuedDate();
		Assert.assertTrue(discontinuedDate.contains(comDetObj.getDiscoutinuedDate()),
				"Error: Returned discontinued date and saved date do not match");

		// Get the discontinued date and check it it against the saved value
		String companyName = editComPage.GetCompanyName();
		Assert.assertTrue(companyName.contains(comDetObj.getCompany()),
				"Error: Returned company name and saved company name do not match");
	}

	@Test(priority = 3)
	public void TestUpadateRecord() throws ParseException {

		// Click the home link so we can start this test fresh
		comHomPage.ClickHomeLink();

		// Find out and assert how many computers are found - using this to establish we
		// are on the correct page
		String numCompFound = comHomPage.GetComutersFoundText();
		// store the amount of computers found
		int origComputersFound = Integer.parseInt(numCompFound.replace(" computers found", ""));

		// using saved values search for the computer under test
		comHomPage.SearchForComputerName(comDetObj.getName());
		String computerFoundResult = comHomPage.GetComutersFoundText();

		// Assert only one computer has been found from the header text
		Assert.assertTrue(computerFoundResult.contains("One computer found"),
				"Error: Text not matching one computer found");

		editComPage = comHomPage.LocatateAndClickTableElement(comDetObj.getName());

		String headerTxt = editComPage.GetHeaderText();

		// Assert we are on the right page
		Assert.assertTrue(headerTxt.contains("Edit computer"), "Error: not on the Edit Computer page");

		// Store the original name and generate a random computer name
		String orignalName = comDetObj.getName();
		comDetObj.setName(Utilities.StringUtils.GenerateRandomString(8));

		// populate the new computer fields
		editComPage.PopulateComuterNameField(comDetObj.getName());

		// Store data in the computer object
		comDetObj.setIntroDate(Utilities.DateUtils.GetRandomDate(""));
		comDetObj.setDiscoutinuedDate(Utilities.DateUtils.GetRandomDate(comDetObj.getIntroDate()));

		// Populate fields
		editComPage.PopulateIntroductionDate(comDetObj.getIntroDate())
				.PopulateDiscontinuedDate(comDetObj.getDiscoutinuedDate());

		// populate and save a company name
		comDetObj.setCompany(editComPage.PopulateRandomCompany());

		// Save updated details.
		comHomPage = editComPage.ClickSaveComputerButton();

		// Find out and assert how many computers are found - using this to establish we
		// are on the correct page
		String newNumCompFound = comHomPage.GetComutersFoundText();

		// confirm the number of computers found has not changed.
		Assert.assertTrue(newNumCompFound.contains(numCompFound), "Error: Computers found text not matching");

		// Assess that the alert text shows that a computer has been updated
		String alertTxt = comHomPage.GetAlertMessageText();
		Assert.assertTrue(alertTxt.contains("Done! Computer " + comDetObj.getName() + " has been updated"),
				"Error: Alert text box not stating computer has been updated");

		String newComputersFound = comHomPage.GetComutersFoundText();
		// store the amount of computers found
		int computersFoundInt = Integer.parseInt(newComputersFound.replace(" computers found", ""));

		// Assess the value has increased by 1
		Assert.assertEquals(computersFoundInt, origComputersFound, "Error: Number of computers foound not matching");

		// Search for the old name to ensure it has been removed
		// using saved values search for the computer under test
		comHomPage.SearchForComputerName(orignalName);

		// Find out and assert how many computers are found - None should be found as we
		// updated the computer name
		String hdrTxt = comHomPage.GetComutersFoundText();
		Assert.assertTrue(hdrTxt.contains("No computers found"), "Error: No computers found text not matched");

		// Search for the new computer name
		comHomPage.SearchForComputerName(comDetObj.getName());
		computerFoundResult = comHomPage.GetComutersFoundText();

		// Assert only one computer has been found from the header text
		Assert.assertTrue(computerFoundResult.contains("One computer found"), "Error: One computer text not matched");

		// parse the table into an array
		String computerData[] = comHomPage.ParseComuterTable();

		// assert the correct name has been found
		Assert.assertTrue(computerData[0].contains(comDetObj.getName()),
				"Error: Computer name returned not matching stored value");

		// convert the date to the table format and assert the correct date has been
		// returned
		String convertedDateString = DateUtils.DateFormatter(comDetObj.getIntroDate(), "yyyy-MM-dd", "dd MMM yyyy");
		Assert.assertTrue(computerData[1].contains(convertedDateString),
				"Error: Returned introduced date not matching stored value");

		// convert the date to the table format and assert the correct date has been
		// returned
		convertedDateString = DateUtils.DateFormatter(comDetObj.getDiscoutinuedDate(), "yyyy-MM-dd", "dd MMM yyyy");
		Assert.assertTrue(computerData[2].contains(convertedDateString),
				"Error: Discontinued date returned not matching stored value");

		// Assert the correct company name has been returned
		Assert.assertTrue(computerData[3].contains(comDetObj.getCompany()),
				"Error: Company name returned  not matching stored value");

	}

	@Test(priority = 4)
	public void TestDeleteRecord() {

		// Click the home link so we can start this test fresh
		comHomPage.ClickHomeLink();

		// Find out and assert how many computers are found - using this to establish we
		// are on the correct page
		String numCompFound = comHomPage.GetComutersFoundText();

		// store the amount of computers found
		int origComputersFound = Integer.parseInt(numCompFound.replace(" computers found", ""));

		// using saved values search for the computer under test
		comHomPage.SearchForComputerName(comDetObj.getName());
		String computerFoundResult = comHomPage.GetComutersFoundText();

		// Assert only one computer has been found from the header text
		Assert.assertTrue(computerFoundResult.contains("One computer found"),
				"Error: One computer found next not matching");

		editComPage = comHomPage.LocatateAndClickTableElement(comDetObj.getName());

		// Assert we are on the right page
		String headerTxt = editComPage.GetHeaderText();
		Assert.assertTrue(headerTxt.contains("Edit computer"), "Error: Not on the Edit Computer page");

		// Delete the computer
		comHomPage = editComPage.ClickDeleteComputerBtn();

		String newNumCompFound = comHomPage.GetComutersFoundText();
		// store the amount of computers found
		int newComputersFoundInt = Integer.parseInt(newNumCompFound.replace(" computers found", ""));
		// Assess the value has decreased by 1
		Assert.assertEquals(newComputersFoundInt, origComputersFound - 1,
				"Error: Number of computers found has not decreased");

	}

	@Test(priority = 5)
	public void TestNameValidation() {

		// Click the home link so we can start this test fresh
		comHomPage.ClickHomeLink();

		// Navigates to the add computer page
		addComPage = comHomPage.ClickAddComputerButton();
		
		// Ensure we are on the correct page
		String headerTxt = addComPage.GetHeaderText();
		Assert.assertTrue(headerTxt.toLowerCase().contains("add a computer"), "Error: Not on the Addd a computer page");
		
		// Click the add computer button to generate the error highlighting
		addComPage.ClickAddNewComputerButton();
		
		// Confirm the highlighting field is displayed
		Assert.assertTrue(addComPage.IsNameErrorFieldDisplayed());
	}

	@AfterTest
	public void terminateBrowser() {
		driver.close();
	}
}
