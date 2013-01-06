import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PostgreSQL implements IDatabase {

	AmazonSearch	amzn	= null;

	Connection		db		= null;
	ResultSet		result	= null;
	Scanner			sc		= null;

	String			sql		= null;
	Statement		st		= null;
	String			url		= "jdbc:postgresql:postgres";

	/**
	 * PostgreSQLに接続・初期化します。 ISBNを元にAmazonからデータを取得します
	 */
	public PostgreSQL() {

		try {
			db = DriverManager.getConnection(url, "postgres", "");
			st = db.createStatement();
			init();
		} catch (SQLException e) {
			System.out
					.println("データベースへの接続が失敗しました。PostgreSQLが起動しているかどうか確認してください。");
			System.out
					.println("psql -U postgres または、psql -h localhost -U postgresが実行できるか確認してください。");
		}
	}

	/**
	 * ISBNを元に本をデータベースに追加します。
	 * 
	 * @see IDatabase#addBook(java.lang.String)
	 * @param ISBN
	 *            13桁のISBN
	 * @return true 正常に追加完了,false 異常発生
	 */
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
			sql = "INSERT INTO bookshelf (title , author , isbn10 ,  isbn13 ,  picturl ,  detailurl ,  publisher ,  publicationdate ,  status ,  year ) VALUES('"
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
			st.execute(sql);
			System.out.println(b.getTitle() + "が追加されました");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * データベースにユーザーを登録します。
	 * 
	 * @param userName
	 *            ユーザー名
	 */
	public void addUser(String userName) {

		sql = "SELECT * FROM usertable where username='" + userName + "';";
		try {
			if (!findUser(userName)) {
				sql = "INSERT INTO usertable VALUES('";
				sql += userName;
				sql += "');";
				st.execute(sql);
				System.out.println("ユーザーを追加しました: " + userName);
			} else {
				System.out.println("ユーザーがすでに存在します");
			}
		} catch (SQLException e) {

		}
	}

	/**
	 * 本を借りる処理を行います
	 * 
	 * @see IDatabase#bBook(java.lang.String, java.lang.String)
	 * @param ISBN
	 *            13桁のISBN
	 * @param userName
	 *            ユーザー名
	 * @return true 正常終了,false 異常終了
	 */
	public boolean bBook(String ISBN, String userName) {

		try {
			// ユーザーIDが存在するか確認する
			if (findUser(userName)) {
				// 借りることのできる本を検索
				sql = "UPDATE bookshelf SET status=false WHERE id =(SELECT min(id) FROM bookshelf WHERE status = true AND isbn13 = '"
						+ ISBN + "');";
				if (st.executeUpdate(sql) == 1) {

					// ユーザーIDがどの本を借りたか登録する
					sql = "INSERT INTO userbooks VALUES(";
					sql += "'" + userName + "',";
					sql += "'" + ISBN + "'";
					sql += ");";
					st.execute(sql);

					return true;
				} else {
					// すべて借りられていた場合

					sql = "SELECT * FROM userbooks WHERE ";
					sql += "isbn13 =";
					sql += "'" + ISBN + "';";
					result = st.executeQuery(sql);
					while (result.next()) {
						System.out.println("この本は "
								+ result.getString(result
										.findColumn("username"))
								+ " にすでに借りられています。");
					}

					System.out.println("すべて借りられているか、本が登録されていません");
					return false;
				}
			} else {
				System.out.println("ユーザーが存在しません。adduserで登録してください");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * データベース一覧を表示します
	 * 
	 * @see IDatabase#listDB()
	 */
	public String listDB() {

		int bookcount = 0;
		String title;
		String jaStatus;
		sql = "SELECT * FROM bookshelf;";
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

	/**
	 * 本を返却します
	 * 
	 * @see IDatabase#rBook(java.lang.String, java.lang.String)
	 * @param ISBN
	 *            13桁のISBN
	 * @param userName
	 *            ユーザー名
	 * @return true 正常終了,false 異常終了
	 */
	public boolean rBook(String ISBN, String userName) {

		try {
			if (findUser(userName)) {
				sql = "UPDATE bookshelf SET status=true WHERE id =(SELECT min(id) FROM bookshelf WHERE status = false AND isbn13 = '";
				sql += ISBN;
				sql += "');";
				if (st.executeUpdate(sql) == 1) {
					sql = "DELETE FROM userbooks WHERE ";
					sql += "username = ";
					sql += "'" + userName + "'";
					sql += "AND isbn13='" + ISBN + "';";
					if (st.executeUpdate(sql) == 1) {
						return true;
					} else {
						sql = "UPDATE bookshelf SET status=false WHERE id =(SELECT min(id) FROM bookshelf WHERE status = true AND isbn13 = '";
						sql += ISBN;
						sql += "');";
						st.execute(sql);
						return false;
					}
				} else {
					System.out.println("返却できる本がありませんでした。貸出状況を確認してください");
					return false;
				}
			} else {
				System.out.println("ユーザーが存在しません。adduserで登録してください");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 本を削除します
	 * 
	 * @param ISBN
	 *            13桁のISBN
	 * @return true 正常終了,false 異常終了
	 */
	public boolean rmBook(String ISBN) {

		try {
			result = st.executeQuery("SELECT * FROM bookshelf WHERE ISBN13 ='"
					+ ISBN + "';");
			result.next();
			String resultstr = result.getString(result.findColumn("isbn13"));
			int index = resultstr.indexOf(ISBN);
			if (index != -1) {
				st.execute("DELETE FROM bookshelf WHERE id =(SELECT min(id) FROM bookshelf WHERE isbn13 ='"
						+ ISBN + "' AND status = true);");
			} else {
				return false;
			}
			return true;
		} catch (SQLException e) {
			System.err.println("\n削除できませんでした。\n検索をして本がデータベースに登録してあるか確認してください。");
			return false;
		}
	}

	@Override
	public boolean rmBookList(String[] books) {

		return false;
	}

	public void rmUser(String userName) {

		try {
			if (findUser(userName)) {
				sql = "DELETE FROM usertable ";
				sql += "WHERE username = ";
				sql += "'" + userName + "';";
				st.execute(sql);
				System.out.println("ユーザーを削除しました: " + userName);
			} else {
				System.out.println("ユーザーが存在しません");
			}
		} catch (SQLException e) {

		}
	}

	@Override
	public String[] searchDB(String key) {

		Book b;
		int bookcount = 0;
		try {
			sql = "SELECT * FROM bookshelf WHERE isbn13 ='";
			sql += key;
			sql += "';";
			result = st.executeQuery(sql);
			while (result.next()) {
				bookcount++;
			}
			if (bookcount != 0) {
				System.out.println("データベース上に " + bookcount + "件 の登録あり");
				bookcount = 0;
				sql = "SELECT * FROM bookshelf WHERE isbn13 ='";
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
	public String showStatus(int mode) {

		sql = "select bookshelf.title,bookshelf.isbn13,bookshelf.status,userbooks.username from bookshelf,userbooks where bookshelf.isbn13 = userbooks.isbn13;";
		try {
			result = st.executeQuery(sql);
			while (result.next()) {
				System.out.println(result.getString(result.findColumn("title"))
						+ "[" + result.getString(result.findColumn("isbn13"))
						+ "]  " + "貸出先: "
						+ result.getString(result.findColumn("username")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean findUser(String userName) {

		int count = 0;
		sql = "SELECT * FROM usertable where username=";
		sql += "'" + userName + "';";
		try {
			result = st.executeQuery(sql);
			while (result.next()) {
				count++;
			}
			if (count == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
	}

	private void init() throws SQLException {

		amzn = new AmazonSearch();
		sql = "SELECT * FROM bookshelf;";
		try {
			result = st.executeQuery(sql);
		} catch (SQLException e) {
			sql = "CREATE TABLE bookshelf(id SERIAL,title varchar(1000),author varchar(1000),isbn10 varchar(20),isbn13 varchar(20),picturl varchar(1000),detailurl varchar(1000),publisher varchar(1000),publicationdate varchar(50),status boolean,year varchar(10));";
			st.execute(sql);
			sql = "CREATE TABLE usertable(username varchar(20));";
			st.execute(sql);
			sql = "CREATE TABLE userbooks(username varchar(20),isbn13 varchar(15));";
			st.execute(sql);
		}
	}
}
