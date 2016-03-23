package com.linkedInTest.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.linkedInTest.util.PropertyLoader;

public class TestCore extends Page{
	
	public static Properties object = new Properties();
	public static Properties config = new Properties();
	public static WebDriver driver;
	public static String SCREENSHOT_FOLDER = "target/screenshots/";
	public static final String SCREENSHOT_FORMAT = ".png";
	private String testUrl;
	private String seleniumHub;
	private String seleniumHubPort;
	private String targetBrowser;
	private String currentTest;
	private String test_data_folder_path;
	private String screenshot_folder_path;
	private String exportDirectoryPath;
	private String OSVersion;
	private String username;
	private String password;
	
	
	
	/**
	 * Fetches suite-configuration from XML suite file.
	 * 
	 * @param testContext
	 */
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {
	
		try{
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//testProperties//config.properties");
			config.load(fis);
			fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//testProperties//object.properties");
			object.load(fis);
			
		}catch(Exception e){}
		
		testUrl = PropertyLoader.loadProperty("site.url");
		System.out.println("----------" + testUrl + "----------");
		seleniumHub = PropertyLoader.loadProperty("grid2.hub");
		targetBrowser = PropertyLoader.loadProperty("browser.name");
		OSVersion = PropertyLoader.loadProperty("OSVersion");
		
		
		//targetBrowser = "ie";
	} // End of Method
	
		/*	@BeforeMethod
			public void setup() throws  IOException, InterruptedException{
				
				if(driver==null){
					
					FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//testProperties//config.properties");
					config.load(fis);
					fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//resources//testProperties//object.properties");
					object.load(fis);
					
					if (config.getProperty("OS").toLowerCase().equals("windows")){
						
						if(config.getProperty("browser").toLowerCase().equals("firefox")){
							
							driver = new FirefoxDriver();
							driver.manage().window().maximize();
							log("Launching the firefox browser on windows ", ILogLevel.METHOD);
							
						}
						else if (config.getProperty("browser").toLowerCase().equals("chrome")){
							
							System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//src//main//resources//driver//chromedriver.exe");
							driver = new ChromeDriver();
							driver.manage().window().maximize();
							log("Launching the chrome browser on windows", ILogLevel.METHOD);	
							
						}
					}
						else if (config.getProperty("OS").toLowerCase().equals("mac")){
							
							if(config.getProperty("browser").toLowerCase().equals("firefox")){
								
								driver= new FirefoxDriver();
								driver.manage().window().maximize();
								log("Launching the firefox browser on mac", ILogLevel.METHOD);
							}
							else if (config.getProperty("browser").toLowerCase().equals("chrome")){
								
								System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/resources/driver_mac/chromedriver");
								driver = new ChromeDriver();
								driver.manage().window().maximize();
								log("Launching the chrome browser on mac ", ILogLevel.METHOD);
									
							}
							
							else if (config.getProperty("browser").toLowerCase().equals("safari")){
								driver = new SafariDriver();
								driver.manage().window().maximize();
								log("Launching the safari browser on mac", ILogLevel.METHOD);
								
							}
						}
					driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
					driver.get(config.getProperty("url"));
				}	
				
				
				Thread.sleep(3000);
			}
			
		
	
	
	*/
	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) throws IOException, InterruptedException {

		currentTest = method.getName(); // get Name of current test.

		// Screenshot
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();

		DesiredCapabilities capability = null;
		if (targetBrowser == null || targetBrowser.contains("firefox") || targetBrowser.contains("ff") ) { // firefox browser settings

			FirefoxProfile profile = new FirefoxProfile();

			profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.showWhenStarting",false);
			//profile.setPreference("browser.download.dir", exportDirectoryPath );
			//profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/jobExport");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk","*");
			profile.setPreference("extensions.update.enabled", false);
			profile.setPreference("app.update.enabled", false);
			profile.setPreference("app.update.auto", false);
			profile.setEnableNativeEvents(true);
			profile.setPreference("network.http.use-cache", false);
			//System.out.println("DOWNLOAD DIRECTORY: " + exportDirectoryPath);
		    		
			capability = DesiredCapabilities.firefox();
			capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);
		
			//System.out.println("----------Firefox browser----------");
			
			//this.driver = new FirefoxDriver(capability);
			driver = new FirefoxDriver(profile);
			
		} else if (targetBrowser.contains("ie8")) { // IE8 browser settings

			capability = DesiredCapabilities.internetExplorer();
			capability.setPlatform(Platform.ANY);
			capability.setBrowserName("internet explorer");
			// capability.setVersion("8.0");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
			capability.setJavascriptEnabled(true);
			
			//driver = new InternetExplorerDriver();
			System.out.println("----------IE8 browser----------");
			
		} else if (targetBrowser.contains("chrome")) { // chrome browser settings

			capability = DesiredCapabilities.chrome();
			System.setProperty("webdriver.chrome.driver","C:\\Users\\heena\\Desktop\\chromedriver_win32\\chromedriver.exe");
			/*
			 * driver = new RemoteWebDriver(new
			 * URL("http://localhost:4444/wd/hub"), capability);
			 */
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);

			this.driver = new ChromeDriver(capability);

			//System.out.println("----------Chorme browser----------");

		} else if (targetBrowser.contains("ie")) { // IE9 browser settings
			
			capability = DesiredCapabilities.internetExplorer();
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);

			capability.setJavascriptEnabled(true);
			
			if(OSVersion.contains("32bit")){
				
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\driver32bit\\IEDriverServer.exe");	
				
			}
			else if (OSVersion.contains("64bit")){
				
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\driver64bit\\IEDriverServer.exe");
			}
			
			
			capability.setCapability("ignoreZoomSetting", true);
			this.driver = new InternetExplorerDriver(capability);

			
		} else if (targetBrowser.contains("safari")) { // Safari browser settins

			// System.setProperty("webdriver.safari.driver","/Users/jesus/Desktop/SafariDriver.safariextz");
			// driver = new SafariDriver();
			SafariDriver profile = new SafariDriver();

			capability = DesiredCapabilities.safari();

			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			//capability.setCapability(SafariDriver.CLEAN_SESSION_CAPABILITY, profile);
			this.driver = new SafariDriver(capability);
			
			//System.out.println("----------Safari browser----------");
			
		}
		
		/*System.out.println("Remote Grid : " + remote_grid );
		// Instantiate RemoteWebDriver
		
		for (int count=1; count<=3; count++){
			try{
				driver = new RemoteWebDriver(remote_grid, capability);
				System.out.println("Driver : " + driver );
			}catch(Exception e){
				System.out.println(e.getMessage());
				Thread.sleep(1000);
			}
		}*/
		
		// Maximize browser window
		this.driver.manage().window().maximize();
	
		// Open test url
		this.driver.get(testUrl);
		
	} // End of Method

			
	@AfterMethod
	public void setScreenshot(ITestResult result) {
		if (!result.isSuccess()) { 
			
			try {
			    //WebDriver returned = new Augmenter().augment(webDriver);
			    if ( driver != null) {
			    	// Take screenshot of first browser window
			        File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			        try {
			        FileUtils.copyFile(f, new File(SCREENSHOT_FOLDER+  result.getName() + SCREENSHOT_FORMAT)
			        .getAbsoluteFile());
			        } catch (IOException e) { 
			        e.printStackTrace(); 
			     }
			    }

			   } catch (ScreenshotException se) {
			    se.printStackTrace();
			   }catch(Exception e){
			    e.printStackTrace();
			   }
			  }  

			  
			if (driver != null) {
			
			driver.close();
			driver.quit();
			
		}	
	}
}


