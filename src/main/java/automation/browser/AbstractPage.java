/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


package automation.browser;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


abstract class AbstractPage
{
	private final WebDriver m_driver;

	private final WebDriverWait m_wait;

	private final String m_strUrl;


	public AbstractPage(AbstractPage p_other, String p_strUrl)
	{
		this(p_other.m_driver, p_other.m_wait, p_strUrl);
	}


	public AbstractPage(WebDriver p_driver, WebDriverWait p_wait, String p_strUrl)
	{
		m_driver = p_driver;
		m_wait   = p_wait;
		m_strUrl = p_strUrl;

		PageFactory.initElements(m_driver, this);
	}


	public WebDriver driver()
	{
		return m_driver;
	}


	protected WebDriverWait driverWait()
	{
		return m_wait;
	}


	public String goTo()
	{
		driver().get(m_strUrl);

		return driver().getCurrentUrl();
	}


	public void acceptAgreement()
	{
		driver().findElements(By.tagName("input")).stream()
			.filter(this::isAcceptAgreement)
			.forEach(p_elt -> p_elt.click());
	}


	protected boolean isAcceptAgreement(WebElement p_elt)
	{
		final String l_strName;
		final String l_strOnClick;

		if (!p_elt.isDisplayed())
			return false;

		if (!p_elt.isEnabled())
			return false;

		l_strName = p_elt.getAttribute("name");

		if (l_strName.contains("demo"))
			return false;

		l_strOnClick = p_elt.getAttribute("onclick");

		if (l_strOnClick == null)
			return false;

		return l_strOnClick.startsWith("acceptAgreement");
	}


	public List<String> getDownloadUrls()
	{
		return driver().findElements(By.tagName("a")).stream()
			.map(p_elt -> p_elt.getAttribute("href"))
			.filter(this::isDownloadUrl)
			.collect(Collectors.toList());
	}


	protected boolean isDownloadUrl(String p_strHref)
	{
		return p_strHref != null && p_strHref.contains("://download.oracle.com/");
	}


	public void takeScreenShot(Path p_file)
		throws IOException
	{
		final TakesScreenshot l_takeScreenshot;
		final File            l_fileScreenshot;

		if (driver() instanceof TakesScreenshot)
			l_takeScreenshot = (TakesScreenshot)driver();
		else
			l_takeScreenshot = (TakesScreenshot)new Augmenter().augment(driver());

		l_fileScreenshot = l_takeScreenshot.getScreenshotAs(OutputType.FILE);

		Files.copy(l_fileScreenshot.toPath(), p_file, StandardCopyOption.REPLACE_EXISTING);
	}
}
