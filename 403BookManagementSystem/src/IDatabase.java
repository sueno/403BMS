public interface IDatabase {

	// ISBNを入力後、本の情報を表示し、登録
	boolean addBook(String ISBN);

	// ISBNから検索された本を削除する
	boolean rmBook(String ISBN);

	// 検索した一覧から削除する本を探す
	boolean rmBookList(String[] books);

	// ISBNと自分のIDを入力し、借りる
	boolean bBook(String ISBN);

	// ISBNと自分のIDを入力し、返す
	boolean rBook(String ISBN);

	// 検索キーワードにヒットした本の情報を表示する
	String[] searchDB(String key);

	// データベースで登録された本の一覧を表示する
	String[] listDB();

	// 貸し出し状況を表示する。
	String[] listStatus(int mode);
}
