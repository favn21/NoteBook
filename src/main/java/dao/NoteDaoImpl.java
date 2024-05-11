package dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Note;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class NoteDaoImpl implements NoteDao {
    private List<Note> noteList;

    private boolean isValidNote(Note note) {
        return note != null && note.isValid();
    }

    @Override
    public void create(Note note) {
        if (isValidNote(note)) {
            noteList.add(note);
            log.info("Заметка создана: {}", note);
        } else {
            log.warn("Не удалось создать заметку: Некорректная заметка");
        }
    }

    @Override
    public List<Note> getAll() {
        return noteList;
    }

    @Override
    public Note getById(String id) {
        for (Note note : noteList) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }

    @Override
    public void update(Note note) {
        Note existingNote = getById(note.getId());
        if (existingNote != null) {
            existingNote.setText(note.getText());
            existingNote.setLabels(note.getLabels());
            log.info("Заметка обновлена: {}", existingNote);
        } else {
            log.warn("Не удалось обновить заметку: Заметка не найдена");
        }
    }

    @Override
    public void delete(String id) {
        noteList.removeIf(note -> note.getId().equals(id));
        log.info("Заметка удалена: Id={}", id);
    }

    @Override
    public List<Note> getByLabel(String label) {
        List<Note> filteredNotes = new ArrayList<>();
        for (Note note : noteList) {
            if (note.getLabels().contains(label)) {
                filteredNotes.add(note);
            }
        }
        return filteredNotes;
    }
}