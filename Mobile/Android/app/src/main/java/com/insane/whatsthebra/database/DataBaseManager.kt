package com.insane.whatsthebra.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseManager (context: Context, name: String?) : SQLiteOpenHelper(context,name,null,1)  {

        override fun onCreate(p0: SQLiteDatabase?) {

            p0?.execSQL("CREATE TABLE TB_BRA_TYPE(\n" +
                    "\tid_bra_type INT NOT NULL,\n" +
                    "\tnm_bra_type VARCHAR(40),\n" +
                    "\tPRIMARY KEY (id_bra_type)\n" +
                    "\t);")
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL("DROP TABLE IF EXISTS TB_BRA_TYPE")

            p0?.execSQL("CREATE TABLE TB_BRA_TYPE(\n" +
                    "\tid_bra_type INT NOT NULL,\n" +
                    "\tnm_bra_type VARCHAR(40),\n" +
                    "\tPRIMARY KEY (id_bra_type)\n" +
                    "\t);")
        }

        fun addBraType(id: Int, name: String) {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put("id_bra_type",id)
            cv.put("nm_bra_type",name)
            db.insert("TB_BRA_TYPE","id_bra_type",cv)
        }

        fun listBraTypes(): Cursor {
            val db = this.readableDatabase
            return db.rawQuery("SELECT nm_bra_type FROM tb_bra_type", null)
        }

        fun deleteBraType(id: Int){
            val db = this.writableDatabase
            db.delete("TB_BRA_TYPE","id_bra_type=${id}",null)
        }
    }