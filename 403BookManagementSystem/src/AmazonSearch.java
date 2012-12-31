import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class AmazonSearch {

	private String getBookXML(String str) {

		final Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("AWSAccessKeyId", "AKIAIWAIBB22KSIA35AQ");
		keyMap.put("AssociateTag", "nokok-22");
		keyMap.put("Version", "2008-08-19");
		keyMap.put("Operation", "ItemLookup");
		keyMap.put("ItemId", str);
		keyMap.put("ResponseGroup", "Medium");
		keyMap.put("Service", "AWSECommerceService");
		keyMap.put("IdType", "ISBN");
		keyMap.put("SearchIndex", "Books");
		final SignedRequestsHelper signedRequestsHelper = new SignedRequestsHelper();
		final String urlStr = signedRequestsHelper.sign(keyMap);
		try {
			final URL url = new URL(urlStr);
			final HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			final BufferedReader fin = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "UTF8"));
			String line;
			final StringBuilder sb = new StringBuilder();
			while ((line = fin.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (final IOException e) {
			return "I/O Error: " + e.toString();
		}
	}

	public Book getBookInfoISBN(String str) {

		final Book b = new Book();
		final XMLTextProc xml = new XMLTextProc(this.getBookXML(str));
		if (xml.searchCheck()) {
			b.setTitle(xml.searchSingleValue("Title"));
			b.setAuthor(xml.searchMultiValues("Author"));
			b.setISBN10(xml.searchSingleValue("ISBN"));
			b.setISBN13(str);
			b.setPublisher(xml.searchSingleValue("Publisher"));
		} else {
			System.out.println("本が見つかりませんでした");
		}
		return b;
	}
}
