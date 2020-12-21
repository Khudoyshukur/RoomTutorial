package com.khudoyshukur_developer.roomdatabasetutorial.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.khudoyshukur_developer.roomdatabasetutorial.R
import com.khudoyshukur_developer.roomdatabasetutorial.database.AppDatabase
import com.khudoyshukur_developer.roomdatabasetutorial.database.Student
import com.khudoyshukur_developer.roomdatabasetutorial.dialogs.CustomDialog
import com.khudoyshukur_developer.roomdatabasetutorial.dialogs.OnPositive

class RecyclerAdapter(private val context: Context): RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private val data = arrayListOf<Student>()
    private val database = AppDatabase.getInstance(context).dao()

    fun update(){
        data.clear()
        data.addAll(database.getAllStudents())
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private lateinit var nameField: TextView
        private lateinit var phoneField: TextView

        init {
            nameField = itemView.findViewById(R.id.nameField)
            phoneField = itemView.findViewById(R.id.phoneNumberField)
        }

        fun bindData(student: Student){
            nameField.text = student.name
            phoneField.text = student.phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_student, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(data[position])

        holder.itemView.setOnLongClickListener {
            val popUpMenu = PopupMenu(context, holder.itemView)
            val inflater = popUpMenu.menuInflater

            inflater.inflate(R.menu.pop_up, popUpMenu.menu)

            popUpMenu.setOnMenuItemClickListener{
                when(it?.itemId){
                    R.id.delete -> deleteStudent(data[position])
                    R.id.edit -> editStudent(data[position])
                }

                true
            }

            popUpMenu.show()
            true
        }
    }

    private fun editStudent(clickedStudent: Student) {
        val dialog = CustomDialog(context)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dialog.create()
        }

        dialog.setTitle("Edit student")

        dialog.setOnPositiveClickListener(object : OnPositive{
            override fun onClick(student: Student) {
                student.id = clickedStudent.id
                database.updateStudent(student)
                update()
            }
        })

        dialog.show()
    }

    private fun deleteStudent(student: Student) {
        database.deleteStudent(student)
        update()
    }

    override fun getItemCount() = data.size
}