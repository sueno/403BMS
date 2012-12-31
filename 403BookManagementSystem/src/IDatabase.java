public interface IDatabase {

	boolean addBook(String ISBN);

	boolean rmBook(String ISBN);

	boolean rmBookList(String[] books);

	boolean bBook(String ISBN);

	boolean rBook(String ISBN);

	String[] searchDB(String key);

	String[] listDB();

	String[] listStatus(int mode);
}
