import java.util.Scanner;

public class Main {

	PostgreSQL	psql;

	public static void main(String[] args) {

		Main m = new Main();
		m.initialize();
		while (true) {
			m.modeChanger();
		}
	}

	private void initialize() {

		psql = new PostgreSQL();
	}

	@SuppressWarnings("resource")
	private void modeChanger() {

		System.out.print("403BMS: ");
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		switch (input) {
		case "add":
			add(sc.next());
			break;
		case "rm":
			remove(sc.next());
			break;
		case "remove":
			remove(sc.next());
			break;
		case "rmlist":
			String[] list = null;
			removelist(list);
			break;
		case "b":
			borrowbook(sc.next());
			break;
		case "borrow":
			borrowbook(sc.next());
			break;
		case "r":
			returnbook(sc.next());
			break;
		case "return":
			returnbook(sc.next());
			break;
		case "s":
			search(sc.next());
			break;
		case "search":
			search(sc.next());
			break;
		case "ls":
			list();
			break;
		case "list":
			list();
			break;
		case "st":
			status();
			break;
		case "status":
			status();
			break;
		case "exit":
			exit();
			break;
		case "q":
			exit();
			break;
		}
	}

	private boolean add(String isbn) {

		if (psql.addBook(isbn)) {
			System.out.println("Added.");
		} else {
			System.err.println("Error.Add failed.");
		}
		return true;
	}

	private boolean remove(String isbn) {

		if (psql.rmBook(isbn)) {
			System.out.println("Removed.");
		} else {
			System.err.println("Error.Remove failed.");
		}
		return true;
	}

	private boolean removelist(String[] isbn) {

		return true;
	}

	private boolean borrowbook(String isbn) {

		psql.bBook(isbn);
		return true;
	}

	private boolean returnbook(String isbn) {

		psql.rBook(isbn);
		return true;
	}

	private boolean search(String isbn) {

		return true;
	}

	private boolean list() {

		psql.listDB();
		return true;
	}

	private boolean status() {

		return true;
	}

	private void exit() {

	}
}
