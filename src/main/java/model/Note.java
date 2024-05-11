package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@AllArgsConstructor
public class Note {
    @NonNull
    private String id;
    private String text;
    private List<String> labels;

    public Note() {
        this.id = generateUniqueId();
        this.text = "";
        this.labels = new ArrayList<>();
    }

    public Note(String text) {
        this.id = generateUniqueId();
        this.text = text;
        this.labels = new ArrayList<>();
    }

    private String generateUniqueId() {
        return "ID-" + UUID.randomUUID().toString();
    }

    public void addLabel(String label) {
        labels.add(label.toUpperCase());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(id).append("\n");
        builder.append("Текст: ").append(text).append("\n");
        builder.append("Метки: ");
        for (String label : labels) {
            builder.append(label).append("; ");
        }
        builder.append("\n");
        return builder.toString();
    }

    public boolean isValid() {
        boolean isValid = isValidText(text) && isValidLabels(labels);
        if (!isValid) {
            log.warn("Некорректная заметка: id={}, текст={}, метки={}", id, text, labels);
        }
        return isValid;
    }

    public boolean isValidText(String text) {
        boolean isValid = text != null && !text.trim().isEmpty() && text.length() >= 3;
        if (!isValid) {
            log.warn("Некорректный текст: {}", text);
        }
        return isValid;
    }

    public boolean isValidLabels(List<String> labels) {
        if (labels == null || labels.isEmpty()) {
            return false;
        }

        for (String label : labels) {
            if (label == null || label.trim().isEmpty()) {
                log.warn("Некорректная метка: " + label);
                return false;
            }
        }

        return true;
    }

    private boolean isValidLabel(String label) {
        boolean isValid = label != null && label.matches("[a-zA-Zа-яА-Я]+");
        if (!isValid) {
            log.warn("Некорректная метка: {}", label);
        }
        return isValid;
    }
}
