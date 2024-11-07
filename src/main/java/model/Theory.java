package model;

import java.util.List;

public class Theory {
    private String command;  // Команда для вызова темы
    private String title;    // Заголовок темы
    private List<Section> sections;  // Список разделов темы

    // Конструкторы, геттеры и сеттеры
    public Theory() {}

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    // Статический вложенный класс Section
    public static class Section {
        private String title;
        private String content;
        private int number;

        // Конструкторы, геттеры и сеттеры
        public Section() {}

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
