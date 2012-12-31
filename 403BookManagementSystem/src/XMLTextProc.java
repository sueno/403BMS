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

		final int beginIndex = xml.indexOf("<" + key + ">");
		final int endIndex = xml.indexOf("</" + key + ">");
		final String result = xml.substring((beginIndex + key.length() + 2),
				endIndex);
		return result;
	}

	public String searchSingleValue(String key, int start) {

		final int beginIndex = xml.indexOf("<" + key + ">", start);
		final int endIndex = xml.indexOf("</" + key + ">", start);
		// System.out.println(xml.substring((beginIndex + key.length() +
		// 2),endIndex));
		final String result = xml.substring((beginIndex + key.length() + 2),
				endIndex);
		return result;
	}

	public String searchMultiValues(final String key) {

		int start = 0;
		String result = "";
		final Pattern p = Pattern.compile("<" + key + ">");
		final Matcher m = p.matcher(xml);
		while (m.find(start)) {
			start = m.end();
			result += xml.substring(start, xml.indexOf("</", start)) + ",";
		}
		return result;
	}
}
