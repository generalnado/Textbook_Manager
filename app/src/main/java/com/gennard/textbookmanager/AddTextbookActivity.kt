package com.gennard.textbookmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gennard.textbookmanager.databinding.ActivityAddTextbookBinding

class AddTextbookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTextbookBinding
    private lateinit var db: BooksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTextbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val isbn = binding.isbn.text.toString()
            val bookTitle = binding.bookTitle.text.toString()
            val bookAuthor = binding.bookAuthor.text.toString()
            val course = binding.course.text.toString()
            val book = Book(0, isbn, bookTitle, bookAuthor, course)
            db.insertBook(book)
            finish()
            Toast.makeText(this, "Textbook Saved", Toast.LENGTH_SHORT).show()
        }
    }
}