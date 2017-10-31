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


import java.io.PrintStream;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;


public final class JdkDownload
{
	private enum GROUP {JDK9, JDK8};


	public static void main(String[] p_args)
	{
		if (p_args.length == 0)
			showUsageAndExit(System.err, 1);

		try
		{
			URL l_url;

			l_url = null;

			for (int l_iPos = 0; l_iPos < p_args.length - 1; l_iPos++)
			{
				if ("--wd-url".equals(p_args[l_iPos]))
				{
					if (l_iPos >= p_args.length - 1)
						showUsageAndExit(System.err, 1);

					l_url = new URL(p_args[++l_iPos]);
				}
				else if ("-h".equals(p_args[l_iPos]) || "--help".equals(p_args[l_iPos]))
				{
					showUsageAndExit(System.out, 0);
				}
			}

			if (l_url == null)
				showUsageAndExit(System.err, 1);

			switch (p_args[p_args.length - 1])
			{
				case "JDK9":
					getDownloadLinks(l_url, GROUP.JDK9);
					break;

				case "JDK8":
					getDownloadLinks(l_url, GROUP.JDK8);
					break;

				default:
					showUsageAndExit(System.err, 1);
					break;
			}

			System.exit(0);
		}
		catch (Exception l_e)
		{
			l_e.printStackTrace(System.err);

			System.exit(1);
		}
	}


	public static void showUsageAndExit(PrintStream p_out, int p_iExitCode)
	{
		p_out.println("automation.browser.JdkDownload --wd-url <url> JDK8|JDK9");
		p_out.println();
		p_out.println("  --wd-url   the web driver URL, e.g. http://127.0.0.1:4444/wd/hub");

		System.exit(p_iExitCode);
	}


	private static void getDownloadLinks(URL p_url, GROUP p_group)
		throws Exception
	{
		final BrowserMobProxy     l_proxy;
		final ChromeOptions       l_options;
		final DesiredCapabilities l_capabilities;
		final WebDriver           l_driver;

		l_proxy = new BrowserMobProxyServer();

		l_proxy.blacklistRequests(".*\\.truste-svc\\.net/.*", 200);
		l_proxy.blacklistRequests(".*\\.truste\\.com/.*", 200);
		l_proxy.start(0);

		l_options = new ChromeOptions();

		l_capabilities = DesiredCapabilities.chrome();
		l_capabilities.setCapability(ChromeOptions.CAPABILITY, l_options);
		l_capabilities.setCapability(CapabilityType.PROXY, ClientUtil.createSeleniumProxy(l_proxy));

		l_driver = new RemoteWebDriver(p_url, l_capabilities);

		try
		{
			final EntryPage    l_pageEntry;
			final AbstractPage l_pageJdk;
			final List<String> l_downloadUrls;

			l_pageEntry = new EntryPage(l_driver);

			l_pageEntry.goTo();

			switch (p_group)
			{
				case JDK9:
					l_pageJdk = l_pageEntry.getJdk9Page();
					break;

				case JDK8:
					l_pageJdk = l_pageEntry.getJdk8Page();
					break;

				default:
					throw new AssertionError(); // cannot occur
			}

			l_pageJdk.goTo();
			l_pageJdk.acceptAgreement();

			l_downloadUrls = l_pageJdk.getDownloadUrls();

			l_downloadUrls.stream().forEach(System.out::println);
		}
		finally
		{
			l_driver.quit();
		}
	}
}
