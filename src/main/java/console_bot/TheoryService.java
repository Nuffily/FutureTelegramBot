package console_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import model.Theory;
import utils.MyUtils;

public class TheoryService {
    private Theory[] theories;
    private final InputService input;
    private final OutputService printer;
    private int countTheory = 0;

    public TheoryService(OutputService printer, InputService input) {
        this.printer = printer;
        this.input = input;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.theories = objectMapper.readValue(
                    new File("src/main/resources/TheoryStorage.json"), Theory[].class);

        } catch (IOException e) {
            printer.println("Ошибка при загрузке теорий: " + e.getMessage());
            this.theories = new Theory[0];
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

    private void handleRandomTheory() {
        while (true) {
            int theoryIndex = MyUtils.getRandom(0, theories.length - 1);
            Theory randomTheory = theories[theoryIndex];
            int sectionIndex = MyUtils.getRandom(0, randomTheory.getSections().size() - 1);
            if (theories.length == 0) {
                printer.println("Нет доступных теорий для отображения.");
                return;
            }
            if (randomTheory.getSections().isEmpty()) {
                printer.println("Нет разделов в выбранной теории.");
                return;
            }
            countTheory++;
            printer.println("---- Случайная теория ----");
            printer.println(randomTheory.getTitle());
            printer.println(randomTheory.getSections().get(sectionIndex).getContent());
            printer.println("--------------------------");
            printer.println("-Для выхода введите back--");

            String userInput = input.getInput();
            if ("back".equalsIgnoreCase(userInput)) {
                printer.println("Выход из случайной теории.");
                break;
            }
        }
    }

    private Integer parseInputAsInteger(String input) {
        try {
            printer.println(Integer.parseInt(input) );
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            if (input.equalsIgnoreCase("back")){
                return null;
            }
            else{
                printer.println("Некорректный ввод: ");
                return null;
            }
        }
    }

    private String getTrimmedInput(String promptMessage) {
        printer.print(promptMessage);
        return input.getInput().trim();
    }

    private void printTheoryMenu() {
        printer.println("Вы в меню теории! \nВведите rand для получения рандомной теории \n stat для статистики \n get_theory для списка теорий.");
    }


    public void handleUserInputForTheory() {
        while (true) {
            String userInput = input.getInput();
            if (userInput.equalsIgnoreCase("rand")){
                handleRandomTheory();
                printTheoryMenu();
                continue;
            }
            if (userInput.equalsIgnoreCase("stat")){
                printer.println("Вы изучили " + countTheory);
                printTheoryMenu();
                continue;
            }
            if (userInput.equalsIgnoreCase("back")){
                printer.println("Вы в главном меню бота!");
                break;
            }

            displayAvailableTheories();
            userInput = getTrimmedInput("Введите номер темы или 'back' для выхода: ");
            Integer index = parseInputAsInteger(userInput);

            if (index != null && index >= 1 && index <= theories.length) {
                handleUserInputForSection(theories[index - 1]);
            } else if ("back".equalsIgnoreCase(userInput)) {
                printer.println("Выход из меню теорий.");
                break;
            }
        }
    }

    private void displaySections(Theory theory) {
        printer.println("Доступные разделы:");
        for (Theory.Section section : theory.getSections()) {
            printer.println(section.getNumber() + ". " + section.getTitle());
        }
    }

    private void handleUserInputForSection(Theory theory) {
        while (true) {
            displaySections(theory);
            String userInput = getTrimmedInput("Введите номер раздела для изучения или 'back' для возврата: ");
            if ("back".equalsIgnoreCase(userInput)) {
                printer.println("Выходим из раздела.");
                printer.println("Вы в меню теории! Введите rand для получения рандомной теории \n get_theory для получения списка теории");

                break;
            }

            Integer sectionIndex = parseInputAsInteger(userInput);
            if (sectionIndex != null && sectionIndex >= 0 && sectionIndex < theory.getSections().size()) {
                Theory.Section section = theory.getSections().get(sectionIndex);
                printer.println("Содержание раздела:");
                printer.println(section.getContent());
                printer.println("-------------------");
                countTheory++;

                printer.println("Введите 'back', чтобы вернуться к разделам.");
                while (true) {
                    String backInput = input.getInput().trim();
                    if ("back".equalsIgnoreCase(backInput)) {
                        break;
                    }
                    printer.println("Некорректный ввод. Введите 'back' для возврата.");
                }
            } else {
                printer.println("Некорректный номер раздела. Введите число от 1 до " + theory.getSections().size() + " или 'back' для возврата.");
            }
        }
    }

    public Theory[] getTheories(){
        return this.theories;
    }

}
