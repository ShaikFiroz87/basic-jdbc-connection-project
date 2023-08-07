package org.firozit.dao;

import org.firozit.beans.Book;

import java.util.List;

public interface BooksDao {
    public int insertBooks(List<Book> bookList);
}
