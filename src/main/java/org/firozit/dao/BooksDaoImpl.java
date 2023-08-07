package org.firozit.dao;

import org.firozit.beans.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BooksDaoImpl implements BooksDao{

    @Value("${DB_URL}")
    private String DB_URL;
    @Value("${DB_UNAME}")
    private String DB_UNAME;
    @Value("${DB_PWD}")
    private String DB_PWD;
    @Value("${DB_DRIVER}")
    private String DB_DRIVER;

    @Override
    public int insertBooks(List<Book> bookList) {

        int flagSuccess = 0;
        int flagFailed = 0;

        int countflag = 0;
        for (Book book : bookList){
            countflag = insertIndividualBook(book);
            if (countflag >= 1) {
                System.out.println("<< " + book.getBookId() + " >> Record Inserted Successfully.......");
                flagSuccess++;
            }
            else {
                System.out.println("Error : << " + book.getBookId() + " >> Record NOT Inserted.......");
                flagFailed++;
            }
        }

        return flagSuccess;
    }

    public int insertIndividualBook(Book book){
        int count = 0;
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PWD)){
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO BOOKS VALUES (?,?,?)");
            pstmt.setInt(1, book.getBookId());
            pstmt.setString(2,book.getBookName());
            pstmt.setDouble(3, book.getBookPrice());
            count = pstmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}

// "try with Resources" concept. try block with Connection variable
// Don't have to use Class.forName() method.
// Driver Connection is auto closed when go with Try with Resources concept after jdbc 4.0 version
//            Class.forName(DB_DRIVER);  -- This command is optional
//            Connection connection = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PWD);