package TestCases;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.AfterGroups;
import org.testng.asserts.SoftAssert;

//Q26. What's the hierarchy of TesNG annotations? or Explain me about annotation hierarchy and execution order.
//Q32. Can we run group of test case using TestNG?--->Yes, using @Test attribute groups={"regressionTest","smokeTest"}
//Q39. How do you set the execution order? Can you explain that?
//Q63. Tell some TestNG Annotations.
//Q145. Can you tell me the use of TestNG soft assertion?

@SuppressWarnings("unused")
public class TestNGAnnotations {
	
	public WebDriver driver;
	public String url="https://jqueryui.com/droppable/";
	
	@BeforeSuite
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Data\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		System.out.println("The setup process in completed");
	
	}
	
	@BeforeTest
	public void profileSetup() {
		driver.manage().window().maximize();
		System.out.println("The profile setup part is completed");
	}
	
	@BeforeClass
	public void appSetup() {
		driver.get(url);
		System.out.println("The app setup process is completed");
		
	}
	
	//@BeforeMethod annotation in TestNG runs before every @test annotated method. We can implement it to check the database connections before executing our test or to check certain login pre-requisites before proceeding to @test method
	//Since login was not needed @BeforeMethod was not implemented here
	
	@Test(priority=1, alwaysRun=true,/*dependsOnMethods=<any method it depends on>,*/timeOut=10000,groups={"dragAndDrop"/*,"<AnyOtherTestMethod to group together for execution>"*/},description="This test validates the drag and drop action on webpage")
	public void dragAndDrop() {
		
		//Implementing Soft Assertion/Verify using SoftAssert class in TestNg
		String actualURL="www.google.com";//driver.getCurrentUrl();
		SoftAssert verify=new SoftAssert();
		verify.assertEquals(actualURL, url);//Mismatch in actual vs expected values in soft assertion will still continue the execution till the end of the test and passes it. 
		
		//Assert.assertEquals(actualURL, url);//Mismatch in actual vs expected values in hard assertion will fail the whole test
		
		
		WebElement iframe=driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(iframe);
		//Thread.sleep(3000);
		WebElement draggable=driver.findElement(By.id("draggable"));
		System.out.println(draggable.getText());
		WebElement droppable=driver.findElement(By.id("droppable"));
		System.out.println(droppable.getText());
		
		//Thread.sleep(3000);
		
		Actions dnd=new Actions(driver);
		dnd.dragAndDrop(draggable, droppable).build().perform();
		//Thread.sleep(3000);
		
		//Verification of successful drag and drop
		String textDropped=droppable.getText();
		if (textDropped.equalsIgnoreCase("dropped!")){
			System.out.println("Drag and Drop passed.");
		}
		else {
			System.out.println("Drag and Drop failed.");
		}
		System.out.println("Test method execution completed");
		
		
	}
	
	@AfterMethod
	public void takeScreenshot() throws IOException {
		File dndscreenshot=(File)((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);//TakesScreenshot is a built in selelnium class and we are referring getScreenshotAs method of this class to take screenshot
		FileUtils.copyFile(dndscreenshot, new File("C:\\Users\\hp\\eclipse-workspace\\TestNGReference\\src\\TestNG_DNDScreenshot.png"));//Assigning location for screenshot file
		System.out.println("Screenshot of the test is taken"); 
	}
	
	@AfterClass
	public void closeUp() {
		driver.close();
		System.out.println("The close_up process is completed");
	}
	
	@AfterTest
	public void reportReady() {
		System.out.println("Report is ready to be shared, with screenshots of tests");
	}
	
	@AfterSuite
	public void cleanUp() {
		
		System.out.println("All close up activities completed");
	}
	
	@BeforeGroups("urlValidation")
	public void setUpSecurity() {
		System.out.println("url validation test starting");
	}
	
	@AfterGroups("urlValidation")
	public void tearDownSecurity() {
		System.out.println("url validation test finished");
	}
	

}
