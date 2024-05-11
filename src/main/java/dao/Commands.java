package dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Commands {
    HELP("Выводит список доступных команд"),
    NOTE_NEW("Создать новую заметку"),
    NOTE_LIST("Отобразить все заметки"),
    NOTE_REMOVE("Удалить заметку"),
    NOTE_EXPORT("Экспортировать заметки в текстовый файл"),
    EXIT("Выход из приложения");

    private final String description;
}
