// IOnNewBookArrivedListener.aidl
package com.example.shaw.kotlinapp.aidl;
// Declare any non-default types here with import statements
import com.example.shaw.kotlinapp.aidl.Book;
interface IOnNewBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewBookArrived(in Book newBook);
}
