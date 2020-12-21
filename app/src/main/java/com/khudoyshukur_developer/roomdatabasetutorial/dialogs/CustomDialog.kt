package com.khudoyshukur_developer.roomdatabasetutorial.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.khudoyshukur_developer.roomdatabasetutorial.R
import com.khudoyshukur_developer.roomdatabasetutorial.database.AppDatabase
import com.khudoyshukur_developer.roomdatabasetutorial.database.Student

class CustomDialog(context: Context): AlertDialog(context), View.OnClickListener {

    private lateinit var okBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var nameEdit: EditText
    private lateinit var phoneEdit: EditText
    private lateinit var listener: OnPositive
    private lateinit var title: TextView

    fun setOnPositiveClickListener(listener: OnPositive){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_dialog)
        setCancelable(false)

        loadViews()

        window!!.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )

        okBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
    }

    private fun loadViews() {
        okBtn = findViewById(R.id.ok_btn)
        cancelBtn = findViewById(R.id.cancel_btn)
        nameEdit = findViewById(R.id.nameEdit)
        phoneEdit = findViewById(R.id.phoneEdit)
        title = findViewById(R.id.dialog_title)
    }

    fun setTitle(text: String){
        title.text = text
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cancel_btn -> this.cancel()

            R.id.ok_btn -> addStudentToDatabase()
        }
    }

    private fun addStudentToDatabase() {
        if(nameEdit.text.toString().isBlank() or phoneEdit.text.toString().isBlank()){
            Toast.makeText(context, "Name or phone number is blank", Toast.LENGTH_SHORT).show()
        } else{
            val name = nameEdit.text.toString()
            val phone = phoneEdit.text.toString()
            listener.onClick(Student(name = name, phoneNumber = phone))

            this.cancel()
        }
    }
}

interface OnPositive{
    fun onClick(student: Student)
}