import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.soap.Node;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

//Amazon�A�N�Z�X�L�[ID AKIAIWAIBB22KSIA35AQ
//Amazon�V�[�N���b�g 9h7f1MiYvNKKL9BXdGhvXFE6thUk6Hg1T7JJN8/n

//Amazon�A�N�Z�X�L�[ID AKIAI7TKUP6SKXN4MXBA 
//AmazonSecret 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
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
					connection.getInputStream(), "UTF-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = fin.readLine()) != null)
				sb.append(line);
			return sb.toString();
		} catch (IOException e) {
			return "I/O Error: " + e.toString();
		}
	}

	public Book getBookInfoISBN(String str) {
		Book b = new Book();
		XMLTextProc xml = new XMLTextProc(str);
		
		return b;
	}
}