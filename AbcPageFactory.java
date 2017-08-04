package ABC;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.File;
import java.util.List;
import java.util.Stack;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class AbcPageFactory {

	@FindBy(xpath = "//li[@id='n-justin']/a[text()='Just In']")
	WebElement justin;

	@FindBy(xpath = "//div[@id='rn-navigation']//a[text()='Programs']")
	WebElement programs;

	@FindBy(xpath = "//ul[@id='rn-programindex']//a[text()='Big Ideas']")
	WebElement pgm_big_ideas;

	@FindBy(id = "search-simple-input-query")
	WebElement radio_search;

	@FindBy(id = "search-simple-input-submit")
	WebElement radio_search_submit;

	@FindBy(xpath = "//div[@id='content']//a[text()='Download audio']")
	WebElement audio_download;

	@FindBy(xpath = "//div[@id=\"content\"]//div[@class='fb-share-button fb_iframe_widget']")
	WebElement fb_share;

	@FindBy(id = "homelink")
	WebElement fb_home;

	@FindBy(id = "twitter-widget-0")
	WebElement twitter_share;
	
	@FindBy(xpath="//div[@id='header']//a[text()='Twitter']")
	WebElement twitter_home;
	
	static WebDriver driver;
	private int invalidImageCount;
	public static WebElement getElementWithIndex(By by, int index) {
		List<WebElement> elements = driver
				.findElements(By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/div[2]/a"));
		return elements.get(index);
	}

	public AbcPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isNewsPageLoaded() {
		String my_title = driver.getTitle();
		String expected_title = "ABC News (Australian Broadcasting Corporation)";
		if (my_title.equals(expected_title)) {
			System.out.println("Page loaded successfully");
			return true;
		} else {
			System.out.println("Unsuccessful in loading the page");
			return false;
		}
	}

	public boolean isNewsBannerLoaded() {
		if (driver.findElement(By.id("header")) != null) {
			System.out.println("News banner loaded successfully");
			return true;
		} else {
			System.out.println("Unsuccessfull in loading the News banner");
			return false;
		}

	}

	public void navigateToJustIn() {
		justin.click();
	}

	public void isVideoLoaded() {
		driver.get("http://www.abc.net.au/news/2017-02-09/weatherill-promises-to-intervene-dramatically/8254908");
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			// Click on play button
			jse.executeScript("jwplayer().play();");
			Thread.sleep(2000);
			// Stop the player
			jse.executeScript("jwplayer().stop()");
			Thread.sleep(2000);
			System.out.println("Video loaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unsuccessful in loading the video");
		}

	}

	public boolean isImgGalleryLoaded() {
		driver.get(" http://www.abc.net.au/news/2017-02-10/abc-open-pic-of-the-week/8256256");
		try {
			invalidImageCount = 0;
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			System.out.println("Total no. of images are " + imagesList.size());
			for (WebElement imgElement : imagesList) {
				if (imgElement != null) {
					verifyimageActive(imgElement);
				}
			}
			System.out.println("Total no. of invalid images are " + invalidImageCount);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		if (invalidImageCount == 0) {
			System.out.println("Image Gallery loaded successfully");
			return true;
		} else {
			System.out.println("Unsuccessful in loading Image Gallery");
			return false;
		}

	}

	public void verifyimageActive(WebElement imgElement) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			// verifying response code he HttpStatus should be 200 if not,
			// increment as invalid images count
			if (response.getStatusLine().getStatusCode() != 200)
				invalidImageCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void navigateToAPgm() throws Exception {
		Actions action = new Actions(driver);
		action.moveToElement(programs).perform();
		Thread.sleep(2000);
		action.moveToElement(pgm_big_ideas).click().perform();

	}

	public void searchAPgm(String search_string) throws Exception {
		radio_search.click();
		radio_search.sendKeys(search_string);
		Thread.sleep(2000);
		radio_search_submit.click();
	}

	public void verifySocialMediaFbShare() throws Exception {
		driver.get("http://www.abc.net.au/radionational/programs/bigideas/a-fortunate-universe/8076406");
		String parentWindowHandler = driver.getWindowHandle(); // Store the parent window
		String subWindowHandler = null;
		fb_share.click();
		java.util.Set<String> handles = driver.getWindowHandles(); // get all window handles
		java.util.Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler); // switch to pop up window
		if (driver.findElement(By.id("homelink")) != null) {
			System.out.println("Pop up successful to Facebook Page");
		} else {
			System.out.println("Pop up unsucessful to Facebook Page");
			Thread.sleep(2000);
			driver.close();
		}
		driver.switchTo().window(parentWindowHandler); // switch back to parent window
														
	}

	public void verifySocialMediaTwitterShare() throws Exception {
		driver.get("http://www.abc.net.au/radionational/programs/bigideas/a-fortunate-universe/8076406");
		String parentWindowHandler = driver.getWindowHandle(); // Store the parent window
		String subWindowHandler = null;
		twitter_share.click();
		java.util.Set<String> handles = driver.getWindowHandles(); // get all window handles
		java.util.Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler); // switch to pop up window
		if (driver.findElement(By.xpath("//div[@id='header']//a[text()='Twitter']")) != null) {
			System.out.println("Pop up successful to Twitter Page");
		} else {
			System.out.println("Pop up unsucessful to Twitter Page");
			Thread.sleep(2000);
			driver.close();
		}
		driver.switchTo().window(parentWindowHandler); // switch back to parent window
														
	}

	public void verifyAudioDownload() throws Exception {
		driver.get("http://www.abc.net.au/radionational/programs/bigideas/a-fortunate-universe/8076406");
		audio_download.click();
		String target_url = driver.getCurrentUrl();
		if (target_url.endsWith(".mp3"))
			System.out.println("Redirected to a mp3(audio) file");
		else
			System.out.println("Redirected to a non-mp3 file ");
		Thread.sleep(2000);
		driver.navigate().back();

	}
	
	public void navigateToLastPgm(){
		
		WebElement rightid = driver.findElement(By.id("right-arrow"));

		Stack<WebElement> stack = new Stack<WebElement>();
		boolean isEndReached = false;
		while (!isEndReached) {
			List<WebElement> elements = driver
					.findElements(By.xpath("//div[@id=\"content\"]//div[@class='on-air-wrapper']/div[2]/ul/li"));
			for (WebElement element : elements) {
				// System.out.println(element.getText());

				if (element.getText().toString().equals("View full program guide")) {
					isEndReached = true;
					break;
				} else
					stack.push(element);

			}
			if (!isEndReached)
				rightid.click();
			else {

				stack.pop().click();
				
			}

		}


	}
	
	public static boolean validate() {
		boolean result = true;

		try {
			List<WebElement> newsArticles = driver
					.findElements(By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/ul/li"));

			int countOfArticles = newsArticles.size();

			for (int i = 1; i <= countOfArticles; i++) {

				String title = driver
						.findElement(By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/ul/li[" + i + "]/h3/a"))
						.getText();
				//System.out.println(title);

				String postedtimeStamp = driver
						.findElement(
								By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/ul/li[" + i + "]/p[1]/span[1]"))
						.getText();
				//System.out.println(postedtimeStamp);

				/*
				 * String updatedtimeStamp = driver .findElement( By.xpath(
				 * "//div[@id=\"main_content\"]//div[@class='article-index section']/ul/li[" + i +
				 * "]/p[1]/span[2]")) .getText();
				 * System.out.println(updatedtimeStamp);
				 */
				String content = driver
						.findElement(By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/ul/li[" + i + "]/p[2]"))
						.getText();

			//	System.out.println(content);
				
				if(title==null||postedtimeStamp==null||content==null||title.isEmpty()||postedtimeStamp.isEmpty()||content.isEmpty()){
					return false;
				}

			}
		} catch (Exception e) {

			result = false;
		}
		return result;

	}
	
	public boolean validateContentsOfArticle() {
		driver.get("http://www.abc.net.au/news/justin/");
		List<WebElement> elements = driver
				.findElements(By.xpath("//div[@id=\"main_content\"]//div[@class='article-index section']/div[2]/a"));

		int no_pages = elements.size();
		// System.out.println(no_pages);

		if (!validate()) {
			return false;
		}
		for (int index = 0; index < no_pages - 1; index++) {

			getElementWithIndex(By.tagName("a"), index).click();

			if (!validate()) {
				return false;
			}

			driver.navigate().back();

		}

		System.out.println("Article contents loaded successfully");
		return true;

	}
	
}
