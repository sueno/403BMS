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
			sql = "insert into bookshelf values(" + bookID + ",'" + title
					+ "','" + author + "','" + ISBN10 + "','" + ISBN13 + "','"
					+ pictURL + "','" + detailURL + "','" + publisher + "','"
					+ publicationDate + "'," + status + ",'" + year + "');";
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

		sql = "update bookshelf set status=false where isbn13=" + "'" + ISBN
				+ "'" + ";";
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

		sql = "update bookshelf set status=true where isbn13=" + "'" + ISBN
				+ "'" + ";";
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

		sql = "select * from bookshelf;";
		try {
			result = st.executeQuery(sql);
			while (result.next()) {
				System.out.println(result.getString(2) + " | "
						+ result.getString(result.findColumn("author")) + " | "
						+ result.getString(result.findColumn("stock")) + " | "
						+ result.getString(result.findColumn("status")) + " | "
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
