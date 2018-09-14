// IBookManager.aidl
package com.example.shaw.kotlinapp.aidl;

// Declare any non-default types here with import statements
import com.example.shaw.kotlinapp.aidl.Book;
import com.example.shaw.kotlinapp.aidl.IOnNewBookArrivedListener;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     List<Book> getBookList();
     void addBook(in Book book);
     void registerListener(IOnNewBookArrivedListener listener);
     void unRegisterListener(IOnNewBookArrivedListener listener);
}
