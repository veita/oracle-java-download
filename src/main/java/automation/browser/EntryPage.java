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


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;


public final class EntryPage extends AbstractPage
{
	private static final String URL =
		"http://www.oracle.com/technetwork/java/javase/downloads/index.html";

	@FindBy(name = "JDK8")
	private WebElement m_jdk8;

	@FindBy(name = "JDK9")
	private WebElement m_jdk9;


	public EntryPage(WebDriver p_driver)
	{
		super(p_driver, new WebDriverWait(p_driver, 30), URL);
	}


	public WebElement getJdk8Url()
	{
		return m_jdk8;
	}


	public Jdk8Page getJdk8Page()
	{
		return new Jdk8Page(this, getJdk8Url().getAttribute("href"));
	}


	public WebElement getJdk9Url()
	{
		return m_jdk9;
	}


	public Jdk9Page getJdk9Page()
	{
		return new Jdk9Page(this, getJdk9Url().getAttribute("href"));
	}
}
