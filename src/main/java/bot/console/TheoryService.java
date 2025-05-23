package bot.console;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import model.Theory;


public class TheoryService {
    private Theory[] theories;
    private final InputService input;
    private final OutputService printer;

    public TheoryService(OutputService printer, InputService input) {
        this.printer = printer;
        this.input = input;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.theories = objectMapper.readValue(
                    new File("src/main/resources/TheoryStorage.json"), Theory[].class);

        } catch (IOException e) {
            printer.println(e.getMessage());
        }
    }

    private void displayAvailableTheories() {
        printer.println("Доступные темы:");
        int count = 0;
        for (Theory theory : theories) {
            printer.println(++count + ". " + theory.getTitle() + " - " + theory.getCommand());
        }
    }

    public void startTheory() {
        while (true) {
            displayAvailableTheories();
            printer.print("Введите номер темы или 'exit' для выхода: ");

            String userInput = input.getInput();

            try {
                int index = Integer.parseInt(userInput) - 1;
                if (index >= 0 && index < theories.length) {
                    handleUserInputForSection(theories[index], index);

                } else {
                    if ("back".equalsIgnoreCase(userInput)) {
                        printer.println("Выход из программы.");
                        break;
                    }
                    printer.println("Такой команды нет, пожалуйста, введите корректный номер.");
                }
            } catch (NumberFormatException e) {
                if ("exit".equalsIgnoreCase(userInput) || "back".equalsIgnoreCase(userInput)) {
                    printer.println("Выход в главное меню.");
                    break;
                }
                printer.println("Некорректный ввод, пожалуйста, введите число.");
            }
        }
    }

    private void displaySections(Theory theory) {
        printer.println("Доступные разделы:");
        for (Theory.Section section : theory.getSections()) {
            printer.println(section.getNumber() + ". " + section.getTitle());
        }
    }

    private void handleUserInputForSection(Theory theory, int index) {

        while (true) {
            displaySections(theories[index]);
            printer.print("Введите номер раздела для изучения или 'back' для возврата: ");
            String userInput = input.getInput();

            try {
                int sectionIndex = Integer.parseInt(userInput) - 1;
                if (sectionIndex >= 0 && sectionIndex < theory.getSections().size()) {

                    printer.println(theory.getSections().get(sectionIndex).getContent());
                    printer.println("Введите back для возврата.");

                    String curInput = input.getInput();
                    if (Objects.equals(curInput, "menu")) {
                        displayAvailableTheories();
                        break;
                    }
                } else {
                    printer.println("Некорректный номер раздела, пожалуйста, введите число в диапазоне.");
                }
            } catch (NumberFormatException e) {
                if ("back".equalsIgnoreCase(userInput)) {
                    printer.println("Выходим из раздела секции.");
                    break;
                }
                printer.println("Некорректный ввод, пожалуйста, введите число или 'back'.");
            }
        }
    }

}
