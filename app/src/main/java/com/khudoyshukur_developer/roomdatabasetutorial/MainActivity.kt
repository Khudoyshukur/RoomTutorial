package com.khudoyshukur_developer.roomdatabasetutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.khudoyshukur_developer.roomdatabasetutorial.adapters.RecyclerAdapter
import com.khudoyshukur_developer.roomdatabasetutorial.database.AppDatabase
import com.khudoyshukur_developer.roomdatabasetutorial.database.Student
import com.khudoyshukur_developer.roomdatabasetutorial.dialogs.CustomDialog
import com.khudoyshukur_developer.roomdatabasetutorial.dialogs.OnPositive

class MainActivity : AppCompatActivity(), OnPositive {

    private lateinit var addButton: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadViews()

        addButton.setOnClickListener {
            val dialog = CustomDialog(this)

            dialog.setOnPositiveClickListener(this)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                dialog.create()
            }

            dialog.show()
        }

        adapter = RecyclerAdapter(this)
        adapter.update()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    private fun loadViews() {
        addButton = findViewById(R.id.addButton)
        recycler = findViewById(R.id.recycler)
    }

    override fun onClick(student: Student) {
        AppDatabase.getInstance(this).dao().insertStudent(student)
        adapter.update()
    }
}