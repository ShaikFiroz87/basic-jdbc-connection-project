package org.firozit.service;

import org.firozit.beans.Book;
import org.firozit.dao.BooksDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BooksServiceImpl implements BooksService{

    @Value("${booksFilePath}")
    private String booksFilePath;

    @Autowired
    private BooksDao booksDao;
    @Override
    public void processBooks() {

        List<Book> bookList= readBooksFromFile(booksFilePath);
        int totalNumberOfRecords = bookList.size();
        int noOfRecordsInserted = booksDao.insertBooks(bookList);

        System.out.println(" ********************** Final Status **********************");
        if(noOfRecordsInserted == totalNumberOfRecords) {
            System.out.println("------------- JOB SUCCESS : All Records Inserted Successfully -----------");
        }
        else {
            System.out.println("------------- JOB FAILED : Failed To Process Records -----------");
        }

        System.out.println("Total Number of Records Processed : "+bookList.size());
        System.out.println("Number of Records Success         : "+noOfRecordsInserted);
        System.out.println("Number of Records Failed          : "+(totalNumberOfRecords - noOfRecordsInserted));

        // ----------------- Validating Books List
       /* bookList.forEach(list -> {
            System.out.print(list.getBookId()+ ",");
            System.out.print(list.getBookName()+ ",");
            System.out.println(list.getBookPrice());
        });*/
    }

    public List<Book> readBooksFromFile(String booksFilePath){
        List<Book> bookList = new ArrayList<>();
        try {
            Stream<String> lines = Files.lines(Paths.get(booksFilePath));
            lines.forEach(line -> {
                Book book = new Book();
                String values[] = line.split(",");
                book.setBookId(Integer.parseInt(values[0]));
                book.setBookName(values[1]);
                book.setBookPrice(Double.parseDouble(values[2]));
                bookList.add(book);
            });
            return bookList;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
