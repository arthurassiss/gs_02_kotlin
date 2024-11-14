package br.com.fiap.alunoresponsvel_rm94242

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EcoDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "EcoDicasDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Dicas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_TITULO TEXT, 
                $COLUMN_DESCRICAO TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertDica(dica: EcoDica): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITULO, dica.titulo)
            put(COLUMN_DESCRICAO, dica.descricao)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllDicas(): List<EcoDica> {
        val dicasList = mutableListOf<EcoDica>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        with(cursor) {
            while (moveToNext()) {
                val titulo = getString(getColumnIndexOrThrow(COLUMN_TITULO))
                val descricao = getString(getColumnIndexOrThrow(COLUMN_DESCRICAO))
                dicasList.add(EcoDica(titulo, descricao))
            }
            close()
        }
        return dicasList
    }
}
