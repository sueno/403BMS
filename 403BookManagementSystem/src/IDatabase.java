public interface IDatabase {

	boolean addBook(String ISBN);

	boolean rmBook(String ISBN);

	boolean rmBookList(String[] books);

	boolean bBook(String ISBN, String userID);

	boolean rBook(String ISBN, String userID);

	String[] searchDB(String key);

	String listDB();

	String showStatus(int mode);
}
