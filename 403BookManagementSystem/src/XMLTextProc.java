import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLTextProc {

	String	xml	= null;

	public XMLTextProc(String xml) {

		this.xml = xml;
	}

	public boolean searchCheck() {

		if (xml.indexOf("ItemAttributes") != -1) {
			return true;
		}
		return false;
	}

	public String searchSingleValue(String key) {

		int beginIndex = xml.indexOf("<" + key + ">");
		int endIndex = xml.indexOf("</" + key + ">");
		if (beginIndex == -1) {
			return null;
		}
		String result = xml
				.substring((beginIndex + key.length() + 2), endIndex);
		return result;
	}

	public String searchSingleValue(String key, int start) {

		int beginIndex = xml.indexOf("<" + key + ">", start);
		int endIndex = xml.indexOf("</" + key + ">", start);
		if (beginIndex == -1) {
			return null;
		}
		// System.out.println(xml.substring((beginIndex + key.length() +
		// 2),endIndex));
		String result = xml
				.substring((beginIndex + key.length() + 2), endIndex);
		return result;
	}

	public String searchMultiValues(String key) {

		int start = 0;
		String result = "";
		Pattern p = Pattern.compile("<" + key + ">");
		Matcher m = p.matcher(xml);
		while (m.find(start)) {
			start = m.end();
			result += xml.substring(start, xml.indexOf("</", start)) + ",";
		}
		return result;
	}
}
