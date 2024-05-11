package dao;

import model.Note;
import java.util.List;

public interface NoteDao {
    void create(Note note);
    List<Note> getAll();
    Note getById(String id);
    void update(Note note);
    void delete(String id);
    List<Note> getByLabel(String label);
}
