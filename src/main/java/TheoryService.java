import model.Location;
import model.Theory;
import model.Question;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TheoryService {
    public ResourceStorage storage;
    public Location location;
    private List<Theory> theories;
    Input input = new Input();

    public TheoryService(ResourceStorage storage, Location location) {
        this.storage = storage;
        this.location = location;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TheoryData theoryData = objectMapper.readValue(new File("src/main/resources/TheoryStorage.json"), TheoryData.class);
            this.theories = theoryData.getTheory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class TheoryData {
        private List<Theory> theory;

        public List<Theory> getTheory() {
            return theory;
        }
    }


    boolean flag = false;


    public void startTheory() {
        displayAvailableTheories();
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
            flag = false;
            System.out.print("Введите номер темы или 'exit' для выхода: ");
            String userInput = input.readLine();
            if ("exit".equalsIgnoreCase(userInput) || "back".equalsIgnoreCase(userInput)) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                int index = Integer.parseInt(userInput) - 1;
                if (index >= 0 && index < theories.size()) {
                    displaySections(theories.get(index));
                    handleUserInputForSection(theories.get(index));
                    if (flag){
                        displaySections(theories.get(index));
                        continue;
                    }
                    break;
                } else {
                    System.out.println("Такой команды нет, пожалуйста, введите корректный номер.");
                }
            } catch (NumberFormatException e) {
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

    private void handleUserInputForSection(Theory theory) {
        while (true) {
            System.out.print("Введите номер раздела для изучения или 'back' для возврата: ");
            String userInput = input.readLine();
            try {
                int sectionIndex = Integer.parseInt(userInput) - 1;
                if (sectionIndex >= 0 && sectionIndex < theory.getSections().size()) {
                    System.out.println(theory.getSections().get(sectionIndex).getContent());
                    break;
                } else {
                    System.out.println("Некорректный номер раздела, пожалуйста, введите число в диапазоне.");
                }
            } catch (NumberFormatException e) {
                if ("back".equalsIgnoreCase(userInput)) {
                    flag = true;
                    break;
                }
                System.out.println("Некорректный ввод, пожалуйста, введите число или 'back'.");
            }
        }
    }

}
