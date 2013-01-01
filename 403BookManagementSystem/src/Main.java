import java.util.Scanner;

public class Main {

	PostgreSQL	psql;
	Scanner sc;
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
		sc = new Scanner(System.in);
		String input = sc.next();
		switch (input) {
		case "add":
			add(sc.next());
			break;
		case "inf":
			inf();
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

	private void add(String isbn) {

		if (psql.addBook(isbn)) {
			System.out.println("Added.");
		} else {
			System.err.println("Error.Add failed.");
		}

	}

	private void inf() {
		System.out.println("---inf mode---(quit:q)");
		while(true){
			String input = sc.nextLine();
			if(input == "q"){
				break;
			}
			add(input);
		}
	}

	private void remove(String isbn) {

		if (psql.rmBook(isbn)) {
			System.out.println("Removed.");
		} else {
			System.err.println("Error.Remove failed.");
		}

	}

	private void removelist(String[] isbn) {

	}

	private void borrowbook(String isbn) {

		psql.bBook(isbn);

	}

	private void returnbook(String isbn) {

		psql.rBook(isbn);

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

	}
}
