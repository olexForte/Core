package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.openqa.selenium.WebDriver;

@SuppressWarnings({ "unused", "deprecation" })
public class SaveFileUtils {

	public static void downloadDocument(String documentPathName) {
		@SuppressWarnings({ "resource" })
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);

		CookieStore cookieStore = new BasicCookieStore();
		Set<org.openqa.selenium.Cookie> cookies = WebBrowser.Driver().manage().getCookies();

		for (org.openqa.selenium.Cookie cookie : cookies) {
			BasicClientCookie2 cookie2 = new BasicClientCookie2(cookie.getName(), cookie.getValue());
			cookie2.setAttribute(ClientCookie.VERSION_ATTR, "1");
			cookie2.setAttribute(ClientCookie.DOMAIN_ATTR, cookie.getDomain());
			cookie2.setDomain(cookie.getDomain());
			cookie2.setPath(cookie.getPath());
			cookie2.setExpiryDate(cookie.getExpiry());
			cookieStore.addCookie(cookie2);
		}

		HttpContext httpContext = new BasicHttpContext();
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		try {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(WebBrowser.Driver().getCurrentUrl()),
					httpContext);
			InputStream inputStream = httpResponse.getEntity().getContent();

			FileOutputStream fos = new FileOutputStream(documentPathName.replaceAll("\\s+", "") + ".csv");

			byte[] buffer = new byte[2048];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}

			fos.close();
		} catch (ClientProtocolException e) {
			System.out.println("safdsafsadfsaf");
		} catch (IOException e) {
			System.out.println("safdsafsadfsaf");
		}
		httpClient.getConnectionManager().shutdown();
	}

}
