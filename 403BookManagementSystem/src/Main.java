import java.util.Scanner;

public class Main {

	PostgreSQL	psql;

	public static void main(String[] args) {

		final Main m = new Main();
		m.modeChanger();
	}

	private void modeChanger() {

		final Scanner sc = new Scanner(System.in);
		System.out.print("403BMS: ");
		final String input = sc.next();
		final String keyword = sc.next();
		switch (input) {
		case "add":
			add(keyword);
			break;
		case "rm":
			remove(keyword);
			break;
		case "remove":
			remove(keyword);
			break;
		case "rmlist":
			final String[] list = null;
			removelist(list);
			break;
		case "b":
			borrowbook(keyword);
			break;
		case "borrow":
			borrowbook(keyword);
			break;
		case "r":
			returnbook(keyword);
			break;
		case "return":
			returnbook(keyword);
			break;
		case "s":
			search(keyword);
			break;
		case "search":
			search(keyword);
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

		psql = new PostgreSQL();
		psql.addBook(isbn);
		return true;
	}

	private boolean remove(String isbn) {

		return true;
	}

	private boolean removelist(String[] isbn) {

		return true;
	}

	private boolean borrowbook(String isbn) {

		return true;
	}

	private boolean returnbook(String isbn) {

		return true;
	}

	private boolean search(String isbn) {

		return true;
	}

	private boolean list() {

		return true;
	}

	private boolean status() {

		return true;
	}

	private void exit() {

	}
}
