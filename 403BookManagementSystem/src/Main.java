import java.util.Scanner;

public class Main {

	PostgreSQL	psql;
	Scanner		sc;

	public static void main(String[] args) {

		Main m = new Main();
		m.initialize();
		while (true) {
			m.modeChanger();
		}
	}

	private void initialize() {

		psql = new PostgreSQL();
		psql.addLog("起動中...");
	}

	private void modeChanger() {

		System.out.print("403BMS: ");
		sc = new Scanner(System.in);
		String input = sc.next();
		switch (input) {
		case "add":
			add(sc.next());
			break;
		case "inf":
			inf();
			break;
		case "r,":
		case "rn":
			System.out.println("rm?");
		case "rm":
		case "remove":
			remove(sc.next());
			break;
		case "rmlist":
			String[] list = null;
			removelist(list);
			break;
		case "b":
		case "borrow":
			borrowbook(sc.next());
			break;
		case "r":
		case "return":
			returnbook(sc.next());
			break;
		case "s":
		case "search":
			search(sc.next());
			break;
		case ";s":
		case "ks":
			System.out.println("ls?");
		case "l":
		case "ll":
		case "ls":
		case "list":
			list();
			break;
		case "st":
		case "status":
			status();
			break;
		case "clear":
		case "cls":
			clear();
			break;
		case "help":
		case "h":
			help();
			break;
		case "exit":
		case "q":
			exit();
			break;
		default:
			System.out.println("認識・修正できないコマンドです: " + input);
			psql.addLog("認識できないコマンドが入力されました: " + input);
			break;
		}
	}

	private void add(String isbn) {

		if (psql.addBook(isbn)) {

		} else {
			System.err.println("エラーが発生しました。追加できませんでした。");
		}

	}

	private void inf() {

		System.out.println("---inf mode---(quit:q)");
		while (true) {
			String input = sc.next();
			if (input.equals("q")) {
				break;
			} else {
				add(input);
			}

		}
	}

	private void remove(String isbn) {

		if (psql.rmBook(isbn)) {
			System.out.println("削除されました。");
		} else {
			System.err.println("エラーが発生しました。削除されませんでした。");
		}

	}

	private void removelist(String[] isbn) {

	}

	private void borrowbook(String isbn) {

		if (psql.bBook(isbn)) {
			System.out.println("借りました");
		} else {
			System.out.println("借りられませんでした。");
		}

	}

	private void returnbook(String isbn) {

		if (psql.rBook(isbn)) {
			System.out.println("返しました");
		} else {
			System.out.println("返せませんでした");
		}

	}

	private void search(String isbn) {

		psql.searchDB(isbn);

	}

	private void list() {

		psql.listDB();

	}

	private void status() {

		psql.listStatus(0);

	}

	private void exit() {

		System.exit(0);
	}

	private void help() {

		clear();
		System.out.println("---403BMS Help Page---");
		System.out
				.println("add [IBSN]     :本を追加します       \tls             :データベースの内容を表示します");
		System.out.println("inf            :本を連続で追加します  \tlist");
		System.out
				.println("rm [ISBN]      :本を削除します       \ts              :貸出中の書籍の一覧を表示します");
		System.out.println("remove [ISBN]                     \tstatus");
		System.out
				.println("b [ISBN]       :本を借ります         \th              :このヘルプを表示します");
		System.out.println("borrow [ISBN]                     \thelp");
		System.out
				.println("r [ISBN]       :本を返します         \tcls            :画面をクリアします");
		System.out.println("return [ISBN]                     \tclear");
		System.out
				.println("q                                 \texit           :管理システムを終了します \t");

	}

	private void clear() {

		for (int i = 0; i < 300; i++) {
			System.out.println();
		}
	}
}
