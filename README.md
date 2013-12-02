<h1>Firefly</h1>
<h3>Firefly is a UI automation framework.</h3>
<h2>Features</h2>
<ul>
   <li>Support for both Selenium and WebDriver.</li>
   <li>Automatic screenshot digests for Selenium actions and WebDriver for every testcase.</li>
   <li>Detailed step by step logging for each user interaction on page.</li>
   <li>Automatic generation of HAR files for pages.</li>
   <li>Support for multiple browsers.</li>
   <li>Includes support for native app testing for iOS and Android.</li>
</ul>
<h2>Getting Started</h2>
<ol>
   <li>Clone the git repo. <br/>
<small>Note: Step 2 & 3 is only for those who want to run tests remotely or using Selenium Server. If you want to run standalone tests you can skip these. Similarly step 4 & 5 are required if you are on mac and want to run sample iOS tests. You can skip these tests otherwise.</small></li>
   <li>Download the latest version of selenium-server from <a href="http://selenium.googlecode.com/files/selenium-server-standalone-2.35.0.jar">here</a>(This is link to v2.35.0. You may want to use an updated version).</li>
   <li>
      Run the selenium server 
      <pre>java -jar selenium-server-standalone-2.35.0.jar</pre>
   </li>
<li>Download and install Appium from <a href="http://appium.io">here</a>. </li>
<li>Run appium server from command line using<pre>appium &</pre></li>
   <li>
      Go to the ui-test-commons directory and run 
      <pre>mvn clean test</pre>
   </li>
   <li>
      This should run the sample tests in parallel.
<br/>
      <i><small>Note: The sample tests contain an example iOS test that will only run on Mac with Xcode. You may exclude the test from the <code>tesng.xml</code> file otherwise.</small></i>
      <ul>
         <li>The result file should be available in <code>/target/surefire-reports/html/index.html</code>.</li>
         <li>The screenshots should be available in <code>/target/screenshots</code> folder. These will be in separate test-wise folders.</li>
         <li>The step logs are in <code>/target/steplog.log</code> file. Currently you need to sort the file to get a test-by-test report. At linux/mac terminal simply do a <code>sort steplog.log</code> to get the desired output.</li>
         </li>
      </ul>
   </li>
</ol>
<h2>Facing Problems?</h2>
<ol>
   <li>
      Getting following error -
      <pre>org.openqa.selenium.remote.UnreachableBrowserException: Could not start a new session.
Possible causes are invalid address of the remote server or browser start-up failure.
...........</pre>
If running tests via Selenium server probably the server is not running or running on incorrect host and port. By default tests are configured to run on standalone mode and do not need the server to be running. You can change this by setting <pre>Options.useRemoteWebDriver(true/false);</pre> in your <code>@BeforeSuite</code> method.<br/>You may also see this error if your are trying to run Appium test and the server is not started.
   </li>
   <li>
      Getting following error -
      <pre>org.openqa.selenium.WebDriverException:
Error 500 Java heap space
HTTP ERROR: 500
Java heap space
RequestURI=/wd/hub/session
Powered by Jetty://</pre>
      This means that assigned Java heap space is insufficient. Usually this occurs when running the tests with Firefox as the profile loads few extensions to report the performance metrics. To solve this start the selenium server with more heap space.
      <pre>java <em>-Xms1024m -Xmx2048m</em>  -jar selenium-server-standalone-2.33.0.jar</pre>
   </li>
</ol>
<h2>Write Your First Firefly Test</h2>
<p>You can write vanilla Selenium and WebDriver tests and Firefly wont complain. But to take advantage of all that Firefly provides we recommend a certain test style based on well accepted page based pattern.</p>
<ol>
   <li>The classes in <code>/src/main/java/com/inmobi/qa/firefly/pages</code> are for demo purpose and can be deleted once you have had a look at them.</li>
   <li>Also the tests in <code>/src/test/java/com/inmobi/qa/firefly/tests</code> are example tests that can be deleted as well.<br/><small>Note: The <code>SetUpTest.java</code> file also contains a <code>@BeforeTest</code> method that deletes the existing screenshots folder. You can modify/reuse as required.</small></li>
   <li>
      <h3>Here is the 2-step test creation.</h3>
      Here we are going to create a login test for <a href="ciui.corp.inmobi.com/user/login.html">ciui</a>
      <ol>
         <li>
            In the <code>/src/main/java/com/inmobi/qa/firefly/pages</code> folder create a new class <code>LoginPage.java</code>. You can create them anywhere. Enter the following content in it.
            <pre>
package com.inmobi.qa.firefly.pages;

import org.openqa.selenium.WebDriver;

import com.inmobi.qa.firefly.annotations.Locate;
import com.inmobi.qa.firefly.core.Firefly;
import com.inmobi.qa.firefly.core.FireflyElement;
import com.inmobi.qa.firefly.core.FireflyPage;
import com.inmobi.qa.firefly.enums.Strategy;

public class LoginPage extends FireflyPage {
	@Locate(strategy = Strategy.NAME, locator = "email")
	public FireflyElement emailElement;

	@Locate(strategy = Strategy.NAME, locator = "password")
	public FireflyElement passwordElement;

	@Locate(strategy = Strategy.XPATH, locator = "//button[@type='submit']")
	public FireflyElement submitElement;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@Override
	public LoginPage get(String url) {
		driver.get(Options.getBaseUrl() + url);
		return new LoginPage(driver);
	}

	public HomePage login(String email, String password) {
		emailElement.sendKeys(email);
		passwordElement.sendKeys(password);
		submitElement.click();

		return new HomePage(driver);
	}

}
</pre>
            Also create a stub class for <code>HomePage.java</code>. Put the following in there.
            <pre>
package com.inmobi.qa.firefly.pages;

import org.openqa.selenium.WebDriver;
import com.inmobi.qa.firefly.core.FireflyPage;

public class HomePage extends FireflyPage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

}
</pre>
            Every page extends <code>com.inmobi.qa.firefly.core.FireflyPage</code>.<br/><br/>
            All the elements on the page are of type <code>com.inmobi.qa.firefly.core.FireflyElement</code> which is a wrapper around  standard <code>org.openqa.selenium.WebElement</code> with certain hooks for Firefly.<br/><br/>
            All the locators are in the page and are annotated with <code>@com.inmobi.qa.firefly.annotations.Locate</code>.<br/>
            Each <code>Locate</code> has a <code>strategy</code> and <code>locator</code> value which are used to locate the element.<br/><br/>
            You don't need to explicitly initialize any <code>FireflyElement</code> defined as members of <code>FireflyPage</code>. They are implicitly initialized every-time a <code>FireflyPage</code> is constructed.
         </li>
         <li>
            Create a <code>LoginPageTest.java</code> in <code>/src/test/java/com/inmobi/qa/firefly/tests/</code> that uses the <code>LoginPage</code> class. Add the following code.
            <pre>
package com.inmobi.qa.firefly.tests;

import org.testng.Assert;
import org.testng.annotations.*;

import com.inmobi.qa.firefly.core.Firefly;
import com.inmobi.qa.firefly.pages.InboxPage;
import com.inmobi.qa.firefly.pages.LoginPage;

public class LoginPageTest {

	@Test
	public void createCampaignTest() {
		Firefly firefly = new Firefly();
		LoginPage loginPage = new LoginPage(firefly.getDriver());
		loginPage = loginPage.get("/user/login.html");

		HomePage inboxPage = loginPage.login("vihaane2e@inmobi.com", "welcome123");
	}
}

</pre>
            That's it. You have authored a test from scratch. Go on and do a <code>mvn test</code> on the project. <br/><br/>
<section>A sample Appium test for testing native apps can also be created in the same way. A sample app is included in the project folder to try out the Appium tests. See the <code>CalculatorScreen.java</code> for the app screen that extends <code>FireflyPage</code> like a normal webpage would.</section> A sample testclass using the same page based framework is <code>AppiumTest.java</code>.
         </li>

      </ol>
</ol>

<h2>If you are wondering ..</h2>
<dl>
<dt>.. what <code>firebug.xpi</code> and <code>netExport.xpi</code>are doing in this ui-automation framework?</dt>
<dd>They are used by Firefly to generate HAR files on page load.</dd>

<dt>.. where is your good old <code>testng.xml</code>?</dt>
<dd>Taking the maven surefire route you can either put configurations directly in <code>maven-surefire-plugin</code> or mention a testng.xml file.
<br/>
Using configuration under <code>maven-surefire-plugin</code> directly
<pre>
            &lt;plugin&gt;
				&lt;artifactId&gt;maven-surefire-plugin&lt;/artifactId&gt;
				&lt;configuration&gt;
					&lt;useSystemClassLoader&gt;false&lt;/useSystemClassLoader&gt;
					&lt;parallel&gt;methods&lt;/parallel&gt;
					&lt;threadCount&gt;1&lt;/threadCount&gt;
					&lt;source&gt;1.6&lt;/source&gt;
					&lt;target&gt;1.6&lt;/target&gt;
					&lt;groups&gt;${testng.groups}&lt;/groups&gt;
					&lt;excludedGroups&gt;${testng.exclude.groups}&lt;/excludedGroups&gt;
					&lt;argLine&gt;-Xmx512m&lt;/argLine&gt;
					&lt;!-- &lt;properties&gt; --&gt;
					&lt;!-- &lt;property&gt; --&gt;
					&lt;!-- &lt;name&gt;usedefaultlisteners&lt;/name&gt; --&gt;
					&lt;!-- &lt;value&gt;true&lt;/value&gt; --&gt;
					&lt;!-- &lt;/property&gt; --&gt;
					&lt;!-- &lt;property&gt; --&gt;
					&lt;!-- &lt;name&gt;listener&lt;/name&gt; --&gt;
					&lt;!-- &lt;value&gt;org.uncommons.reportng.HTMLReporter,org.uncommons.reportng&lt;/value&gt; --&gt;
					&lt;!-- &lt;/property&gt; --&gt;
					&lt;!-- &lt;/properties&gt; --&gt;
				&lt;/configuration&gt;
			&lt;/plugin&gt;
</pre>
<br/>
Using <code>testng.xml</code> file
<pre>
            &lt;plugin&gt;
				&lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
				&lt;artifactId&gt;maven-surefire-plugin&lt;/artifactId&gt;
				&lt;version&gt;2.5&lt;/version&gt;
				&lt;configuration&gt;
					&lt;suiteXmlFiles&gt;
						&lt;suiteXmlFile&gt;${testngfile}&lt;/suiteXmlFile&gt;
					&lt;/suiteXmlFiles&gt;
					&lt;properties&gt;
						&lt;property&gt;
							&lt;name&gt;usedefaultlisteners&lt;/name&gt;
							&lt;value&gt;false&lt;/value&gt;
						&lt;/property&gt;
						&lt;property&gt;
							&lt;name&gt;listener&lt;/name&gt;
							&lt;value&gt;org.uncommons.reportng.HTMLReporter&lt;/value&gt;
						&lt;/property&gt;
					&lt;/properties&gt;
				&lt;/configuration&gt;
			&lt;/plugin&gt;
</pre> where <code>testngfile</code> is defined in <code>pom</code> as
<pre>
    &lt;properties&gt;
		...
        ...
		&lt;testngfile&gt;testng.xml&lt;/testngfile&gt;
	&lt;/properties&gt;
</pre>
</dd>

<dt>.. what is the <code>@Required</code> annotation</dt>
<dd>
This annotation applies to <code>FireflyElement</code> in <code>FireflyPage</code>'s subclasses. Present of any such element would be asserted in the page initialization failing which the test would fail. It is supposed to be a check that we are on the right page before proceeding forward. Hence only those elements that will always be present on the page (before and after any ajax action) should be annotated with <code>@Required</code>.
</dd>
</dl>
<h2>Configuring your tests</h2>
<section>Most of the test features are configurable using static members of <code>Options</code> class. Many have sensible defaults but many need to be changed on per use basis. As they are static they are on run level and recommended to set in <code>@BeforeSuite</code> method. You can also parameterize your setup methods to accept command line arguments for these setting.<br/>
<dl>
<dt><code>setBaseurl(String baseURL)</code></dt>
<dd>Same as selenium baseurl. You <strong>must</strong> set it.</dd>
<dt><code>setBrowser(String browserName)</code></dt>
<dd>Browser on which the tests would be run. Accepts <code>CHROME</code><code>FIREFOX</code><code>IE</code><code>ANDROID</code>. Default is <i><code>FIREFOX</code></i></dd>
<dt><code>setHost(String host)</code></dt>
<dd>Host where Selenium server is running. Required only if the tests are not stand alone. Default is <i><code>localhost</code></i>.</dd>
<dt><code>setPort(String port)</code></dt>
<dd>Selenium server port. Default is <i><code>4444</code></i>.</dd>
<dt><code>useRemoteWebDriver(Boolean flag)</code></dt>
<dd>Let the tests use Selenium server. If false would run the standalone mode. Default is <i><code>false</code></i>.</dd>
<dt><code>setScreenshotFolder(String folderPath)</code></dt>
<dd>The folder to which screenshots would be saved to. Default is <i><code>target</code></i>.</dd>
<dt><code>setTestClassPrefix(String testClassPrefix)</code></dt>
<dd>Should contain the package to your tests. You must set this to get proper reporting and screenshots. Default is <code><i>com.inmobi.qa.firefly.tests</i></code>.
</dd>
<dt><code>setExtensionPath(String extensionPath)</code></dt>
<dd>The folder where the firefox extensions are present. Default is <i><code>""</code></i> as they are present in project root.</dd>
<dt><code>setHarDumpLocation(String harDumpLocation)</code></dt>
<dd>The folder to which HAR files would be saved to. Default is <i><code>/tmp/perf-logs</code></i>.</dd>
<dt><code>enablePerformanceLogging(Boolean flag)</code></dt>
<dd>Determines whether the HAR data would be collected. Currently only used if the browser is set to <code>FIREFOX</code>. Since collecting the data will load the firefox extensions it is recommended to keep the parallel thread count less to prevent out of heap memory scenarios.</dd>
</dl>
</section>
Contact <a href="mailto:Rishu.Mehrotra@inmobi.com">Rishu</a> or <a href="mailto:priyadarshi.kunal@inmobi.com">Kunal</a> for any bug/enhancement-request/suggestion/kudos ;)
