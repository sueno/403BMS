import static com.rosaloves.bitlyj.Bitly.shorten;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.rosaloves.bitlyj.Bitly;

class AmazonSearch {

	private String getBookXML(String str) {
		
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put("AWSAccessKeyId", "AKIAIWAIBB22KSIA35AQ");
		keyMap.put("AssociateTag", "nokok-22");
		keyMap.put("Version", "2008-08-19");
		keyMap.put("Operation", "ItemLookup");
		keyMap.put("ItemId", str);
		keyMap.put("ResponseGroup", "Medium");
		keyMap.put("Service", "AWSECommerceService");
		keyMap.put("IdType", "ISBN");
		keyMap.put("SearchIndex", "Books");
		SignedRequestsHelper signedRequestsHelper = new SignedRequestsHelper();
		String urlStr = signedRequestsHelper.sign(keyMap);
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			BufferedReader fin = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = fin.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			return "I/O Error: " + e.toString();
		}
	}

	public Book getBookInfoISBN(String str) {

		Book b = new Book();
		XMLTextProc xml = new XMLTextProc(this.getBookXML(str));
		if (xml.searchCheck()) {
			b.setTitle(xml.searchSingleValue("Title"));
			b.setAuthor(xml.searchMultiValues("Author"));
			b.setISBN10(xml.searchSingleValue("ISBN"));
			b.setISBN13(str);
			b.setPublisher(xml.searchSingleValue("Publisher"));
			XMLTextProc picChild = new XMLTextProc(
					xml.searchSingleValue("MediumImage"));
			try {
				String beforeUrlPict = picChild.searchSingleValue("URL");
				String beforeUrlDetail = xml.searchSingleValue("DetailPageURL");
				String picUrl = Bitly
						.as("nokok", "R_52ef6d95b7182dd697e69c7e2dbee19e")
						.call(shorten(beforeUrlPict)).getShortUrl();
				String detailUrl = Bitly
						.as("nokok", "R_52ef6d95b7182dd697e69c7e2dbee19e")
						.call(shorten(beforeUrlDetail)).getShortUrl();
				b.setPictURL(picUrl);
				b.setDetailURL(detailUrl);
				b.setPublicationDate(xml.searchSingleValue("PublicationDate"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			
		}
		return b;
	}
}
