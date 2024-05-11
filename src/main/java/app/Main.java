package app;

import dao.NoteDao;
import dao.NoteDaoImpl;
import model.Note;
import service.NoteService;
import service.NoteServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Note> noteList = new ArrayList<>();
        NoteDao noteDao = new NoteDaoImpl(noteList);
        NoteService noteService = new NoteServiceImpl(noteDao);
        ((NoteServiceImpl) noteService).start();
    }
}