package com.example.notesappusingcompose.data

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
@Dao
interface NoteDao {


    @Upsert
    suspend fun upserNote(note: com.example.notesappusingcompose.data.Note)

    @Delete
    suspend fun deleteNote(note:Note)

    @Query("SELECT * FROM note ORDER By title ASC")
    fun getOrderByTitle(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER By DateAdded")
    fun getOrderByDateAdded(): Flow<List<Note>>
}