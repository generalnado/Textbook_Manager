package com.gennard.textbookmanager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter (private var books: List<Book>,context: Context) :
    RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {

private val db: BooksDatabaseHelper = BooksDatabaseHelper(context)

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookIsbnTextView: TextView = itemView.findViewById(R.id.bookIsbnTextView)
        val bookTitleTextView: TextView = itemView.findViewById(R.id.bookTitleTextView)
        val bookAuthorTextView: TextView = itemView.findViewById(R.id.bookAuthorTextView)
        val courseTextView: TextView = itemView.findViewById(R.id.courseTextView)
        val updateSaveButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_entry, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int = books.size


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bookIsbnTextView.text = "ISBN: " + book.isbn
        holder.bookTitleTextView.text = "Book Title: " + book.bookTitle
        holder.bookAuthorTextView.text = "Book Author(s): " + book.bookAuthor
        holder.courseTextView.text = "Course: " + book.course

        holder.updateSaveButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateBookActivity::class.java).apply {
                putExtra( "book_id", book.id)
            }
            holder.itemView.context.startActivity(intent)

        }

        holder.deleteButton.setOnClickListener {
            db.deleteBook(book.id)
            refreshData(db.getAllBooks())
            Toast.makeText(holder.itemView.context,"Book Deleted", Toast.LENGTH_SHORT).show()
        }
    }



    fun refreshData(newBooks: List<Book>) {
    books = newBooks
    notifyDataSetChanged()
}



}


