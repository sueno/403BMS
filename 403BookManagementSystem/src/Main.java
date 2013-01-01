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

		System.exit(0);
	}
	
	private void help(){
		clear();
		System.out.println("---403BMS Help Page---");
		System.out.println("add [IBSN]");
		System.out.println("inf");
		System.out.println("rm [ISBN]");
		System.out.println("remove [ISBN]");
		System.out.println("b [ISBN]");
		System.out.println("borrow [ISBN]");
		System.out.println("r [ISBN]");
		System.out.println("return [ISBN]");
		System.out.println("ls");
		System.out.println("list");
		System.out.println("s");
		System.out.println("status");
		System.out.println("h");
		System.out.println("help");
		System.out.println("cls");
		System.out.println("clear");
		System.out.println("q");
		System.out.println("exit");

	}
	
	private void clear(){
		for(int i=0;i<300;i++){
			System.out.println();
		}
	}
}
