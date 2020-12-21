package com.khudoyshukur_developer.roomdatabasetutorial.database

import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    fun insertStudent(student: Student)

    @Delete
    fun deleteStudent(student: Student)

    @Update
    fun updateStudent(student: Student)

    @Query("select * from students")
    fun getAllStudents(): List<Student>
}