import model.Location;
import model.Theory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TheoryService {
    private Theory[] theories;
    Input input = new Input();

    public TheoryService() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.theories = objectMapper.readValue(new File("src/main/resources/TheoryStorage.json"), Theory[].class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTheory() {
        handleUserInputForTheory();
    }

    private void displayAvailableTheories() {
        System.out.println("Доступные темы:");
        int count = 0;
        for (Theory theory : theories) {
            System.out.println(++count + ". " + theory.getTitle() + " - " + theory.getCommand());
        }
    }

    private void handleUserInputForTheory() {
        while (true) {
            displayAvailableTheories();
            System.out.print("Введите номер темы или 'exit' для выхода: ");

            String userInput = input.readLine();

            try {
                int index = Integer.parseInt(userInput) - 1;
                if (index >= 0 && index < theories.length) {
                    handleUserInputForSection(theories[index], index);

                } else {
                    if ("back".equalsIgnoreCase(userInput)) {
                        System.out.println("Выход из программы.");
                        break;
                    }
                    System.out.println("Такой команды нет, пожалуйста, введите корректный номер.");
                }
            } catch (NumberFormatException e) {
                if ("exit".equalsIgnoreCase(userInput) || "back".equalsIgnoreCase(userInput)) {
                    System.out.println("Выход в главное меню.");
                    break;
                }
                System.out.println("Некорректный ввод, пожалуйста, введите число.");
            }
        }
    }

    private void displaySections(Theory theory) {
        System.out.println("Доступные разделы:");
        for (Theory.Section section : theory.getSections()) {
            System.out.println(section.getNumber() + ". " + section.getTitle());
        }
    }

    private void handleUserInputForSection(Theory theory, int index) {

        while (true) {
            displaySections(theories[index]);
            System.out.print("Введите номер раздела для изучения или 'back' для возврата: ");
            String userInput = input.readLine();
            try {
                int sectionIndex = Integer.parseInt(userInput) - 1;
                if (sectionIndex >= 0 && sectionIndex < theory.getSections().size()) {
                   // locationInTheory = "sections";
                    System.out.println(theory.getSections().get(sectionIndex).getContent());
                    System.out.println("Введите back для возврата.");

                    String curInput = input.readLine();
                    if (Objects.equals(curInput, "menu")){
                        displayAvailableTheories();
                        break;
                    }
                } else {
                    System.out.println("Некорректный номер раздела, пожалуйста, введите число в диапазоне.");
                }
            } catch (NumberFormatException e) {
                if ("back".equalsIgnoreCase(userInput)) {
                    System.out.println("Выходим из раздела секции.");
                    break;
                }
                System.out.println("Некорректный ввод, пожалуйста, введите число или 'back'.");
            }
        }
    }

}
