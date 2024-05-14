package test;

import dao.NoteDao;
import model.Note;
import org.junit.jupiter.api.DisplayName;
import service.NoteService;
import service.NoteServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestService {

    @Mock
    private NoteDao noteDao;
    private NoteService noteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        noteService = new NoteServiceImpl(noteDao);
    }

    @Test
    @DisplayName("Генерация уникального ID")
    public void testGenerateUniqueId() {
        Note note1 = new Note("Некоторый текст для заметки 1");
        Note note2 = new Note("Некоторый текст для заметки 2");
        assertNotEquals(note1.getId(), note2.getId());
    }

    @Test
    @DisplayName("Проверка формата сгенерированного ID")
    public void testGeneratedIdFormat() {
        Note note = new Note();
        String id = note.getId();
        assertTrue(id.startsWith("ID-"));
        assertTrue(id.length() > 10);
    }

    @Test
    @DisplayName("Создание заметки с допустимыми данными")
    public void testCreateNoteWithValidData() {
        String text = "Допустимый текст";
        List<String> labels = Arrays.asList("Метка1", "Метка2");
        noteService.createNote(text, labels);
        verify(noteDao).create(any(Note.class));
    }

    @Test
    @DisplayName("Создание заметки с недопустимыми данными")
    public void testCreateNoteWithInvalidData() {
        String text = "";
        List<String> labels = Collections.singletonList("Метка1");
        noteService.createNote(text, labels);
        verify(noteDao, never()).create(any(Note.class));
    }
    @Test
    @DisplayName("Корректность ввода ID при удалении")
    public void testRemoveNoteWithValidId() {
        Note note = new Note();
        note.setId("ID-123456789");
        noteService.createNote("Пример текста заметки", Arrays.asList("Метка1", "Метка2"));
        noteService.removeNoteById(note.getId());
        verify(noteDao, times(1)).delete(eq(note.getId()));
    }
}
