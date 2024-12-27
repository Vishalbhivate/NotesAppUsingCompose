package com.example.notesappusingcompose.presentation

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class NoteState
    (
    val notes: List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val disp: MutableState<String> = mutableStateOf("")

) {
    fun copy(
        notes: List<Note>,
        title: MutableState<String>,
        disp: MutableState<String>
    ) {


    }
}




