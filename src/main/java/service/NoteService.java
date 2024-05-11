package service;

import model.Note;

import java.util.List;

public interface NoteService {
    void createNote(String text, List<String> labels);
    List<Note> getAllNotes();
    void removeNoteById(String id);
    String exportNotes();
    List<Note> getNotesByLabel(String label);
    void printHelp();
}