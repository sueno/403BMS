public class XMLTextProc {
	String xml = null;
	public XMLTextProc(String xml) {
		this.xml = xml;
	}

	public void searchValue(String key) {

		int beginIndex = xml.indexOf("<" + key + ">");
		int endIndex = xml.indexOf("</" + key + ">");
		String result = xml.substring(beginIndex + key.length(), endIndex);
	}
}
