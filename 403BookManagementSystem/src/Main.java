public class Main {
	public static void main(String[] args) {
		AmazonSearch amzn = new AmazonSearch();
		Book b = amzn.getBookInfoISBN("9784774145020");
		System.out.println("Title: " + b.getTitle());
		System.out.println("Author: " + b.getAuthor());
	}
}
