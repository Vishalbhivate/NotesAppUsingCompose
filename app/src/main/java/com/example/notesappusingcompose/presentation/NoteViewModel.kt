package com.example.notesappusingcompose.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesappusingcompose.data.Note
import com.example.notesappusingcompose.data.NoteDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NoteViewModel(
        private var dao: NoteDao
    
        ):ViewModel() {

    private var isSortedDateAdded = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var notes = isSortedDateAdded.flatMapLatest {
        if (it) {
            dao.getOrderByDateAdded()
        } else {
            dao.getOrderByTitle()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    var _state = MutableStateFlow(NoteState())
    var state = combine(_state, isSortedDateAdded, notes) { state, isSortedDateAdded, notes ->
        state.copy(
            notes = notes,
            title = mutableStateOf(""),
            disp = mutableStateOf("")
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }

            }

            is NotesEvent.SaveNote -> {

                val note = Note(
                    title = state.value.title.value,
                    disp = state.value.disp.value,
                    dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.upserNote(note = note)
                }
                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        disp = mutableStateOf(""),
                        notes = TODO()
                    )

                        
                }
            }

            NotesEvent.SortNotes -> {
                isSortedDateAdded.value = !isSortedDateAdded.value
            }
        }
    }
}