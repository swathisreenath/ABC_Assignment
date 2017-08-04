package ABC;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;


public class AbcTest {
	private WebDriver driver;
	private String abcnews_url;
	private String abcradio_url;
	
	AbcPageFactory page;
  @BeforeClass
  @Parameters("webdriver_chrome_driver")
  public void beforeClass(String webdriver_chrome_driver) {
	  
		driver = new FirefoxDriver();
		abcnews_url = "http://www.abc.net.au/news/";
		abcradio_url = "http://www.abc.net.au/radionational/";
		page = new AbcPageFactory(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  
	  }
  @Test
  //Section(a) Test1: Verify that the page loads successfully 
  public void Testa1() {
	  driver.get(abcnews_url);
	  page.isNewsPageLoaded();
  }
  
  @Test
  //Section(a) Test2: Verify that News banner loads
  public void Testa2() {
	  driver.get(abcnews_url);
	  page.isNewsBannerLoaded();
  }
  
  @Test
  //Section(a) Test3: Verify can navigate to the ‘Just In’ page via the link on the primary navigation
  public void Testa3() {
	  driver.get(abcnews_url);
	  page.navigateToJustIn();
  }
  
  @Test
  public void Testa4(){
	  page.validateContentsOfArticle();
  }
  @Test
  //Section(a) Test5: Verify that a video loads and appears successfully 
  public void Testa5(){
	  page.isVideoLoaded();
  }
  
  @Test
  //Section(a) Test6: Verify that the Image Gallery successfully loads and images appear correctly
  public void Testa6() {
	  page.isImgGalleryLoaded();
  }
  
  @Test
  //Section(b) Test1: Verify can navigate to a ‘Program’ (e.g. ‘Big Ideas’) from the Programs sub-menu
  public void Testb1() throws Exception{
	  driver.get(abcradio_url);
	  page.navigateToAPgm();
  }
  
  @Test
  //Section(b) Test2: Navigate to the last item in the ‘Program guide’ banner and select the last program
  public void Testb2() throws Exception{
	  driver.get(abcradio_url);
	  page.navigateToLastPgm();
  }
  
  @Test
  //Section(b) Test3: Verify can search for content in the search bar and that content is returned
  public void Testb3() throws Exception{
	  driver.get(abcradio_url);
	  page.searchAPgm("A Big Country");
  }
  
  @Test
  //Section(b) Test4a: Verify you can click on Social media ‘Share’ icon and the correct pop-up appears
  //Part a: Check for FaceBook 
  public void Testb4a() throws Exception{
	  page.verifySocialMediaFbShare();
  }
  
  @Test
//Section(b) Test4a: Verify you can click on Social media ‘Share’ icon and the correct pop-up appears
  //Part b: Check for twitter
  public void Testb4b() throws Exception{
	  page.verifySocialMediaTwitterShare();
  }
  
  @Test
  //Section(b) Test5: Verify that when you click on ‘Download audio’ you are directed to the mp3 file 
  public void Testb5() throws Exception{
	  page.verifyAudioDownload();
  }
  
  @AfterClass
  public void afterClass() throws Exception {
	  Thread.sleep(3000);
		driver.quit();
  }

}
