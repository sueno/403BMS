public interface IDatabase {

	boolean addBook(String ISBN);

	boolean bBook(String ISBN, String userID);

	String listDB();

	boolean rBook(String ISBN, String userID);

	boolean rmBook(String ISBN);

	boolean rmBookList(String[] books);

	String[] searchDB(String key);

	String showStatus(int mode);
}
