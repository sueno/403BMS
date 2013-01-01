import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSQL implements IDatabase {

	Connection		db		= null;
	Statement		st		= null;
	ResultSet		result	= null;

	String			url		= "jdbc:postgresql:postgres";
	String			sql		= null;
	AmazonSearch	amzn	= null;

	/*
	 * create table bookshelf( id SERIAL, title varchar(1000), author
	 * varchar(1000), isbn10 varchar(20), isbn13 varchar(20), picturl
	 * varchar(1000), detailurl varchar(1000), publisher varchar(1000),
	 * publicationdate varchar(50), status boolean, year varchar(10));
	 */

	public PostgreSQL() {

		init();
		try {
			db = DriverManager.getConnection(url, "postgres", "");
			st = db.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void init() {

		amzn = new AmazonSearch();
	}

	@Override
	public boolean addBook(String ISBN) {

		Book b = amzn.getBookInfoISBN(ISBN);
		String author = b.getAuthor();
		String bookID = b.getBookID(ISBN);
		String ISBN10 = b.getISBN10();
		String ISBN13 = b.getISBN13();
		String pictURL = b.getPictURL();
		String detailURL = b.getDetailURL();
		String publisher = b.getPublisher();
		String publicationDate = b.getPublicationDate();
		boolean status = b.getStatus();
		String title = b.getTitle();
		String year = b.getYear();

		if (title == null) {
			return false;
		}

		try {
			sql = "insert into bookshelf (title , author , isbn10 ,  isbn13 ,  picturl ,  detailurl ,  publisher ,  publicationdate ,  status ,  year ) values('"
					+ title
					+ "','"
					+ author
					+ "','"
					+ ISBN10
					+ "','"
					+ ISBN13
					+ "','"
					+ pictURL
					+ "','"
					+ detailURL
					+ "','"
					+ publisher
					+ "','"
					+ publicationDate
					+ "',"
					+ status
					+ ",'"
					+ year
					+ "');";
			System.out.print("New ");
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rmBook(String ISBN) {

		try {
			result = st.executeQuery("SELECT * FROM bookshelf where ISBN13 ='"
					+ ISBN + "';");
			if (!result.toString().isEmpty()) {
				st.execute("DELETE FROM bookshelf where ISBN13='" + ISBN + "';");
			} else {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rmBookList(String[] books) {

		return false;
	}

	@Override
	public boolean bBook(String ISBN) {

		// 借りられていない同じ本を探すSQL
		// select * from bookshelf where id =(select min(id) from bookshelf
		// where status = true AND isbn13 = 'ISBN');

		sql = "update bookshelf set status=false where id =(select min(id) from bookshelf where status = true AND isbn13 = '";
		sql += ISBN;
		sql += "');";
		try {
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rBook(String ISBN) {

		sql = "update bookshelf set status=true where id =(select min(id) from bookshelf where status = false AND isbn13 = '";
		sql += ISBN;
		sql += "');";
		try {
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] searchDB(String key) {

		return null;
	}

	@Override
	public String listDB() {

		String jaStatus;
		String author;
		sql = "select * from bookshelf;";
		try {
			result = st.executeQuery(sql);
			while (result.next()) {
				if (result.getString(result.findColumn("status")).startsWith(
						"t")) {
					jaStatus = "貸出可";
				} else {
					jaStatus = "貸出中";
				}
				author = result.getString(result.findColumn("author"));
				if(author.length() > 10){
					author = author.substring(0, 10) + "...";
				}
				System.out.println(result.getString(2) + " | "
						+ author + " | "
						+ jaStatus + " | "
						+ result.getString(result.findColumn("isbn13")));
			}
			return result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String[] listStatus(int mode) {

		return null;
	}
}
