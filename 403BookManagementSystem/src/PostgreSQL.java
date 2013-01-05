import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

		try {
			db = DriverManager.getConnection(url, "postgres", "");
			st = db.createStatement();
			addLog("データベースの接続に成功");
			init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void init() {

		amzn = new AmazonSearch();
		addLog("AmazonSearch初期化完了");
	}

	@Override
	public boolean addBook(String ISBN) {

		Book b = amzn.getBookInfoISBN(ISBN);
		String author = b.getAuthor();
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
			System.out.print(b.getTitle() + "が追加されました");
			addLog("次の本が追加されました。" + ISBN);
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			addLog("本を追加できませんでした。" + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rmBook(String ISBN) {

		try {
			result = st.executeQuery("SELECT * FROM bookshelf where ISBN13 ='"
					+ ISBN + "';");
			result.next();
			String resultstr = result.getString(result.findColumn("isbn13"));
			int index = resultstr.indexOf(ISBN);
			if (index != -1) {
				st.execute("DELETE FROM bookshelf where id =(select min(id) from bookshelf where isbn13 ='"
						+ ISBN + "' AND status = true);");
			} else {
				addLog("本を削除できませんでした。本が存在しません。");
				return false;
			}
			addLog("本を削除しました。");
			return true;
		} catch (SQLException e) {
			// e.printStackTrace();
			System.err
					.println("\nエラーが発生し、削除できませんでした。\n検索をして本がデータベースに登録してあるか確認してください。");
			addLog("本が削除できませんでした。" + e.toString());
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

		try {
			sql = "update bookshelf set status=false where id =(select min(id) from bookshelf where status = true AND isbn13 = '";
			sql += ISBN;
			sql += "');";
			if (st.executeUpdate(sql) == 1) {
				addLog(ISBN + " のステータスを貸出中に変更しました。");
				return true;
			} else {
				addLog(ISBN + " を借りられませんでした。すでに借りられているか、本が存在しません。");
				return false;
			}
		} catch (SQLException e) {
			addLog("SQL文実行中にエラーが発生しました。 " + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean rBook(String ISBN) {

		try {
			sql = "update bookshelf set status=true where id =(select min(id) from bookshelf where status = false AND isbn13 = '";
			sql += ISBN;
			sql += "');";
			if (st.executeUpdate(sql) == 1) {
				addLog(ISBN + " のステータスを貸出可に変更しました。");
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			addLog("SQL文実行中にエラーが発生しました。 " + e.toString());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] searchDB(String key) {

		Book b;
		int bookcount = 0;
		try {
			sql = "SELECT * FROM bookshelf where isbn13 ='";
			sql += key;
			sql += "';";
			result = st.executeQuery(sql);
			while (result.next()) {
				bookcount++;
			}
			if (bookcount != 0) {
				System.out.println("データベース上に " + bookcount + "件 の登録あり");
				bookcount = 0;
				sql = "SELECT * FROM bookshelf where isbn13 ='";
				sql += key;
				sql += "' AND status = true";
				sql += ";";
				result = st.executeQuery(sql);
				while (result.next()) {
					bookcount++;
				}
				if (bookcount != 0) {
					System.out.println(bookcount + "冊 貸し出し可能");
				} else {
					System.out.println("***すべて貸出中です***");
				}
			} else {
				System.out.println("この本はデータベースに登録されていません");
			}

			b = amzn.getBookInfoISBN(key);
			System.out.println();
			System.out.println("タイトル\t: " + b.getTitle());
			System.out.println("著者\t: " + b.getAuthor());
			System.out.println("詳細URL\t: " + b.getDetailURL());
			System.out.println("出版日時\t: " + b.getPublicationDate());
			System.out.println("出版社\t: " + b.getPublisher());
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String listDB() {

		int bookcount = 0;
		String title;
		String jaStatus;
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
				title = result.getString(result.findColumn("title"));
				System.out.println(title + "\t\t[" + jaStatus + "]");
				bookcount++;
			}
			System.out.println("結果:" + bookcount + "冊");
			return result.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String listStatus(int mode) {

		String jaStatus;
		sql = "select * from bookshelf where status = false;";
		try {
			result = st.executeQuery(sql);
			while (result.next()) {
				if (result.getString(result.findColumn("status")).startsWith(
						"t")) {
					jaStatus = "貸出可";
				} else {
					jaStatus = "貸出中";
				}
				System.out.println(result.getString(result.findColumn("title"))
						+ "[" + result.getString(result.findColumn("isbn13"))
						+ "] : " + jaStatus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addLog(String logmessage) {

		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
		sql = "insert into logtable (message) values(";
		sql += "'";
		sql += "[" + df.format(d) + "]" + "  " + logmessage;
		sql += "'";
		sql += ");";
		try {
			st.execute(sql);
		} catch (SQLException e) {

		}
	}
}
