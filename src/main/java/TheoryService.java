import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import model.Theory;


public class TheoryService {
    private Theory[] theories;
    private final Scanner scan = new Scanner(System.in);
    private final PrintService printer;

    public TheoryService(ResourceStorage storage) {
        printer = new PrintService(storage);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.theories = objectMapper.
                    readValue(new File("src/main/resources/TheoryStorage.json"), Theory[].class);

        } catch (IOException e) {
            printer.println(e);
        }
    }

    public void startTheory() {
        handleUserInputForTheory();
    }

    private void displayAvailableTheories() {
        printer.println("Доступные темы:");
        int count = 0;
        for (Theory theory : theories) {
            printer.println(++count + ". " + theory.getTitle() + " - " + theory.getCommand());
        }
    }

    private void handleUserInputForTheory() {
        while (true) {
            displayAvailableTheories();
            printer.print("Введите номер темы или 'exit' для выхода: ");

            String userInput = scan.nextLine();

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
            String userInput = scan.nextLine();

            try {
                int sectionIndex = Integer.parseInt(userInput) - 1;
                if (sectionIndex >= 0 && sectionIndex < theory.getSections().size()) {

                    printer.println(theory.getSections().get(sectionIndex).getContent());
                    printer.println("Введите back для возврата.");

                    String curInput = scan.nextLine();
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
