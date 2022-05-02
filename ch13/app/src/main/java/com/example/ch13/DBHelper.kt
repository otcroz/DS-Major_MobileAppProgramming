package com.example.ch13

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) { // DB 테이블 만드는 작업
        p0?.execSQL("create table todo_tb (_id integer primary key autoincrement, todo not null )") // sql db language
        //p0?.execSQL("create table todo_tb ("+"_id integer primary key autoincrement,"+" todo not null )") // sql db language: 교재에 있는
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}