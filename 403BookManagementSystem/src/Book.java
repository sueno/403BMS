public class Book {
	private String title;
	private String year;
	private String ISBN13;
	private String ISBN10;
	private int stock;
	private String[] author;
	private String genre;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getISBN13() {
		return ISBN13;
	}

	public void setISBN13(String iSBN13) {
		ISBN13 = iSBN13;
	}

	public String getISBN10() {
		return ISBN10;
	}

	public void setISBN10(String iSBN10) {
		ISBN10 = iSBN10;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String[] getAuthor() {
		return author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setAuthor(String[] authorList) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
