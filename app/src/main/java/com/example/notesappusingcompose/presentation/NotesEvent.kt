package com.example.notesappusingcompose.presentation

sealed interface NotesEvent {

    object SortNotes: NotesEvent
    data class DeleteNote(var note: com.example.notesappusingcompose.data.Note): NotesEvent
    data class SaveNote(
        var title: String,
        var disp: String

    ) : NotesEvent

}