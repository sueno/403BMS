import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLTextProc {
	String xml = null;
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
		String result = xml
				.substring((beginIndex + key.length() + 2), endIndex);
		result = removeMissingChar(result);
		return result;
	}

	public String searchSingleValue(String key, int start) {
		int beginIndex = xml.indexOf("<" + key + ">", start);
		int endIndex = xml.indexOf("</" + key + ">", start);
		//System.out.println(xml.substring((beginIndex + key.length() + 2),endIndex));
		String result = xml
				.substring((beginIndex + key.length() + 2), endIndex);
		result = removeMissingChar(result);
		return result;
	}

	public String searchMultiValues(final String key) {
		int start = 0;
		String tmp = "";
		Pattern p = Pattern.compile("<" + key + ">");
		Matcher m = p.matcher(xml);
		while (m.find(start)) {
			start = m.end();
			tmp += xml.substring(start, xml.indexOf("</", start)) + ",";
		}
		tmp = removeMissingChar(tmp);
		return tmp;
	}

	private String removeMissingChar(String str) {
		str = str.replace('~',' ');
		str = str.replaceAll("\\?", "");
		str = str.replaceAll("　", "");
		str = str.replaceAll("　\\?", "");
		if (str.endsWith(",")) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
}
