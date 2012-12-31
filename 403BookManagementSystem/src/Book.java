public class Book {

	private String	author;
	private String	bookID;
	private String	genre;
	private String	ISBN10;
	private String	ISBN13;
	private String	pictURL;
	private String	publisher;
	private boolean	status = true;
	private int		stock;
	private String	title;
	private String	year;

	public String getAuthor() {

		return author;
	}

	public String getBookID() {

		return bookID;
	}

	public String getGenre() {

		return genre;
	}

	public String getISBN10() {

		return ISBN10;
	}

	public String getISBN13() {

		return ISBN13;
	}

	public String getPictURL() {

		return pictURL;
	}

	public String getPublisher() {

		return publisher;
	}

	public boolean getStatus() {

		return status;
	}

	public int getStock() {

		return stock;
	}

	public String getTitle() {

		return title;
	}

	public String getYear() {

		return year;
	}

	public void setAuthor(String authorList) {

		this.author = authorList;
	}

	public void setBookID(String bookID) {

		this.bookID = bookID;
	}

	public void setGenre(String genre) {

		this.genre = genre;
	}

	public void setISBN10(String iSBN10) {

		ISBN10 = iSBN10;
	}

	public void setISBN13(String iSBN13) {

		ISBN13 = iSBN13;
	}

	public void setPictURL(String pictURL) {

		this.pictURL = pictURL;
	}

	public void setPublisher(String publisher) {

		this.publisher = publisher;
	}

	public void setStatus(boolean status) {

		this.status = status;
	}

	public void setStock(int stock) {

		this.stock = stock;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setYear(String year) {

		this.year = year;
	}

}
