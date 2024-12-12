package model;

import java.util.List;

public class Theory {
    private String command;  // Команда для вызова темы
    private String title;    // Заголовок темы
    private List<Section> sections;  // Список разделов темы

    public String getCommand() {
        return command;
    }

    public String getTitle() {
        return title;
    }

    public List<Section> getSections() {
        return sections;
    }

    public static class Section {
        private String title;
        private String content;
        private int number;

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public int getNumber() {
            return number;
        }
    }
}
