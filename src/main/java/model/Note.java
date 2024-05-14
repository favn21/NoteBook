package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}
