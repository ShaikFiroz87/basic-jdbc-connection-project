package org.firozit;

import org.firozit.beans.Book;
import org.firozit.dao.BooksDao;
import org.firozit.service.BooksService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

		// ------------------Validating Service Layer - Reading Books Data and Pass to DAO Layer ------------------------
		BooksService booksService = context.getBean(BooksService.class);
		booksService.processBooks();


		// ------------------Validating DAO Layer for MySQL Connection ------------------------
		/*List<Book> booksList = new ArrayList<Book>();

		Book book1 = new Book();
		book1.setBookId(100);
		book1.setBookName("Java");
		book1.setBookPrice(111.00);
		booksList.add(book1);

		Book book2 = new Book();
		book2.setBookId(101);
		book2.setBookName("Python");
		book2.setBookPrice(222.00);
		booksList.add(book2);

		BooksDao booksDao = context.getBean(BooksDao.class);
		booksDao.insertBooks(booksList);*/
	}

}
