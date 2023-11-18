package com.gennard.textbookmanager

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BooksDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "textbook_manager_app.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "myLibrary"
        private const val COLUMN_ID = "id"
        private const val COLUMN_ISBN = "isbn"
        private const val COLUMN_TITLE = "bookTitle"
        private const val COLUMN_AUTHOR = "bookAuthor"
        private const val COLUMN_COURSE = "course"
    }

        override fun onCreate(db: SQLiteDatabase?) {
            val createTableQuery =
                "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_ISBN TEXT, $COLUMN_TITLE TEXT, $COLUMN_AUTHOR TEXT, $COLUMN_COURSE TEXT)"
            db?.execSQL(createTableQuery)

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
            db?.execSQL(dropTableQuery)
            onCreate(db)
        }

        fun insertBook(book: Book) {
            val db = writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_ISBN, book.isbn)
                put(COLUMN_TITLE, book.bookTitle)
                put(COLUMN_AUTHOR, book.bookAuthor)
                put(COLUMN_COURSE, book.course)
            }
            db.insert(TABLE_NAME, null, values)
            db.close()
        }
    fun getAllBooks():List<Book> {
        val booksList = mutableListOf<Book>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val isbn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))
            val bookTitle= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val bookAuthor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
            val course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE))

            val book = Book(id, isbn, bookTitle, bookAuthor, course)
            booksList.add(book)
        }
        cursor.close()
        db.close()
        return booksList

    }

    fun updateBook(book: Book){
        val   db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, book.id)
            put(COLUMN_ISBN, book.isbn)
            put(COLUMN_TITLE, book.bookTitle)
            put(COLUMN_AUTHOR, book.bookAuthor)
            put(COLUMN_COURSE, book.course)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(book.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }
    fun getBookByID(bookId: Int): Book{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $bookId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val isbn = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ISBN))
        val bookTitle = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val bookAuthor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
        val course = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COURSE))

        cursor.close()
        db.close()
        return Book(id, isbn, bookTitle, bookAuthor, course)

    }

    fun deleteBook(bookId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(bookId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()

    }


    }
