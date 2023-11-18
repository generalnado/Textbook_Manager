package com.gennard.textbookmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gennard.textbookmanager.databinding.ActivityUpdateBookBinding

class UpdateBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBookBinding
    private lateinit var db: BooksDatabaseHelper
    private var bookId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        bookId = intent.getIntExtra("book_id",  -1)
        if (bookId == -1){
            finish()
            return
        }

        val book = db.getBookByID(bookId)
        binding.updateIsbn.setText(book.isbn)
        binding.updateBookTitle.setText(book.bookTitle)
        binding.updateBookAuthor.setText(book.bookAuthor)
        binding.updateCourse.setText(book.course)

        binding.updateSaveButton.setOnClickListener {
            val newIsbn = binding.updateIsbn.inputType.toString()
            val newTitle = binding.updateBookTitle.text.toString()
            val newAuthor = binding.updateBookAuthor.text.toString()
            val newCourse = binding.updateCourse.text.toString()
            val updatedBook = Book(bookId, newIsbn, newTitle, newAuthor, newCourse)
            db.updateBook(updatedBook)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()

        }

    }
}