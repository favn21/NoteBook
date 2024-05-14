package service;

import dao.NoteDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Note;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteDao noteDao;

    @Override
    public void createNote(String text, List<String> labels) {
        if (isValidText(text) && isValidLabels(labels)) {
            Note note = new Note();
            note.setText(text);
            note.setLabels(labels);
            note.setId(generateId());
            noteDao.create(note);
            log.info("Заметка добавлена: Id={}, Текст={}, Метки={}", note.getId(), note.getText(), note.getLabels());
        } else {
            log.warn("Не удалось создать заметку: некорректный ввод");
        }
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDao.getAll();
    }

    @Override
    public void removeNoteById(String id) {
        if (isValidId(id)) {
            noteDao.delete(id);
            log.info("Заметка удалена: Id={}", id);
        } else {
            log.warn("Не удалось удалить заметку: некорректный Id");
        }
    }

    @Override
    public String exportNotes() {
        String filename = generateFilename();
        log.info("Заметки экспортированы в файл: {}", filename);
        return filename;
    }

    @Override
    public List<Note> getNotesByLabel(String label) {
        if (isValidLabel(label)) {
            return noteDao.getByLabel(label);
        } else {
            log.warn("Некорректная метка");
            return null;
        }
    }

    @Override
    public void printHelp() {
        log.info("Выведено сообщение справки");
    }

    private String generateId() {
        return "ID-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    private String generateFilename() {
        return "notes_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss")) + ".txt";
    }

    private boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    private boolean isValidLabels(List<String> labels) {
        return labels != null && !labels.isEmpty() && labels.stream().allMatch(label -> label != null && !label.trim().isEmpty());
    }

    private boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    private boolean isValidLabel(String label) {
        return label != null && !label.trim().isEmpty();
    }

    private void handleNoteCreation(Scanner scanner) {
        System.out.println("Введите текст заметки:");
        String text = scanner.nextLine();
        System.out.println("Введите метки через запятую:");
        String labelsInput = scanner.nextLine();
        List<String> labels = Arrays.asList(labelsInput.split(","));
        createNote(text, labels);
    }

    private void handleNoteListing() {
        List<Note> allNotes = getAllNotes();
        for (Note note : allNotes) {
            System.out.println(note);
        }
    }

    private void handleNoteRemoval(Scanner scanner) {
        System.out.println("Введите id заметки для удаления:");
        String id = scanner.nextLine();
        removeNoteById(id);
    }

    private void handleNoteExport() {
        String exportResult = exportNotes();
        System.out.println(exportResult);
    }

    private void handleHelpPrinting() {
        printHelp();
    }

    private void handleInvalidCommand() {
        System.out.println("Неверная команда. Введите help для получения списка команд.");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать! Введите команду (help для получения списка команд):");
        String input = scanner.nextLine();

        while (!input.equals("exit")) {
            switch (input) {
                case "note-new":
                    handleNoteCreation(scanner);
                    break;
                case "note-list":
                    handleNoteListing();
                    break;
                case "note-remove":
                    handleNoteRemoval(scanner);
                    break;
                case "note-export":
                    handleNoteExport();
                    break;
                case "help":
                    handleHelpPrinting();
                    break;
                default:
                    handleInvalidCommand();
            }

            System.out.println("Введите следующую команду:");
            input = scanner.nextLine();
        }

        System.out.println("Спасибо за использование нашего приложения!");
        scanner.close();
    }
}
