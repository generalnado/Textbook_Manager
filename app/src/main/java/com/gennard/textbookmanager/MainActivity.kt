package com.gennard.textbookmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gennard.textbookmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: BooksDatabaseHelper
    private lateinit var booksAdapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = BooksDatabaseHelper(this)
        booksAdapter = BooksAdapter(db.getAllBooks(), this)

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.booksRecyclerView.adapter = booksAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddTextbookActivity::class.java)
            startActivity(intent)

        }

        }

        override fun onResume() {
            super.onResume()
            booksAdapter.refreshData(db.getAllBooks())
        }

    }