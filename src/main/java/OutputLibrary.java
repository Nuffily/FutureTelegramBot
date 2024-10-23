import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutputLibrary {
    HashMap<String, HashMap<String, String> > commands;
    HashMap<String, String> singleQuotes;
    HashMap<String, String[]> randomQuotes;
    Question[] JSQuestions;
    int countOfJSQuestions = 39;

    public void fillMaps() {
        commands = importCommands("src/main/java/data/Commands.json");
        singleQuotes = importSingleQuotes("src/main/java/data/Quotes.json");
        randomQuotes = importRandomQuotes("src/main/java/data/Quotes.json");
        JSQuestions = fillQuestions("src/main/java/questionsJS.txt");
    }

    private HashMap<String, HashMap<String, String> > importCommands(String path) {
        HashMap<String, HashMap<String, String> > map = new HashMap<>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Command[] commands;

        try {
            commands = objectMapper.readValue(file, Command[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Command currentCommand: commands) {
            if (!map.containsKey(currentCommand.location)) {
                map.put(currentCommand.location, new HashMap<>());
            }
            for (String variant: currentCommand.inputVariants) {

                map.get(currentCommand.location).put(variant, currentCommand.id);
            }
        }

        return map;
    }

    private HashMap<String, String[]> importRandomQuotes(String path) {

        HashMap<String, String[]> map = new HashMap<>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Quote[] quotes;

        try {
            quotes = objectMapper.readValue(file, Quote[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Quote currentQuote: quotes) {
            if (currentQuote.type.equals("random"))
                map.put(currentQuote.name, currentQuote.outputArray);
        }

        return map;
    }

    private HashMap<String, String> importSingleQuotes(String path) {

        HashMap<String, String> map = new HashMap<String, String>();
        File file = new File(path);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Quote[] quotes;

        try {
            quotes = objectMapper.readValue(file, Quote[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Quote currentQuote: quotes) {
            if (currentQuote.type.equals("single"))
                map.put(currentQuote.name, currentQuote.output);
        }

        return map;
    }

    private Question[] fillQuestions(String fileName) {

        Question[] array = new Question[countOfJSQuestions];

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            Question question;
            String s;
            int questNumber = 0;

            while ((s = br.readLine()) != null) {

                question = new Question();

                while ((s = br.readLine()) != null && !(s.equals("$"))) { }

                if (s == null) break;

                question.number = Integer.parseInt(br.readLine());

                while (((s = br.readLine()) != null) && !s.equals("$V")) {
                    question.body += s  + "\n";
                }

                question.answers = new String[Integer.parseInt(br.readLine())];

                for (int i = 0; ((s = br.readLine()) != null) && !s.equals("$A"); i++) {
                    question.answers[i] = s;
                }

                question.correctAns = Integer.parseInt(br.readLine());

                array[questNumber++] = question;
//                System.out.println(arr[questNumber - 1].number);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        return array;
    }

//    private HashMap<String, String> fillCommands(String fileName) {
//
//        HashMap<String, String> map = new HashMap<String, String>();
//
//        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
//        {
//            String command = "";
//            String s = "";
//            while ((s = br.readLine()) != null) {
//
//                while ((s = br.readLine()) != null && !s.equals("$")) { }
//
//                command = br.readLine();
//                br.readLine();
//
//                while (((s = br.readLine()) != null) && !s.equals("$")) {
//                    map.put(s, command);
//                }
//            }
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//
//        return map;
//    }

//    private HashMap<String, String> fillMapSingleQuotes(String fileName) {
//
//        HashMap<String, String> map = new HashMap<String, String>();
//
//        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
//        {
//            String response = "";
//            String s = "";
//            while ((s = br.readLine()) != null) {
//
//                response = "";
//
//                if (s.equals("$s")) {
//
//                    while ((s = br.readLine()) != null && !s.equals("$")) {
//                        response += s + "\n";
//                    }
//
//                    s = br.readLine();
//                    map.put(s, response);
//                }
//            }
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        return map;
//    }
//
//    private HashMap<String, String[]> fillMapRandomQuotes(String fileName) {
//
//        HashMap<String, String[]> map = new HashMap<String, String[]>();
//        String responses[];
//
//        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
//        {
//            String s = "";
//            String cur = "";
//            Stack<String> stack = new Stack<String>();
//            int ResponseCount;
//
//            while ((s = br.readLine()) != null) {
//
//                if (s.equals("$r")) {
//
//                    ResponseCount = 0;
//                    a: while (true) {
//                        while ((s = br.readLine()) != null && !s.equals("$") && !s.equals("$$")) {
//                            cur += s;
//                        }
//                        stack.push(cur);
//                        ResponseCount++;
//                        cur = "";
//                        if (s == null || s.equals("$$")) break;
//                    }
//
//                    responses = new String[ResponseCount];
//
//                    for (int i = 0; i < ResponseCount; i++)
//                        responses[i] = stack.pop();
//
//                    s = br.readLine();
//                    handMadeUtils.shuffleArray(responses);
//                    map.put(s, responses);
//                }
//            }
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//        return map;
//    }
//

//    private void createPredefinedQuote(HashMap<String, String> map, BufferedReader br1) {
//        String response = "";
//
//        try(BufferedReader br = br1)
//        {
//            String s;
//
//            response = "";
//
//            while ((s = br.readLine()) != null && !s.equals("$")) {
//                response += s + "\n";
//            }
//
//            while (((s = br.readLine()) != null) && !s.equals("$end")) {
//                map.put(s, response);
//            }
//            return;
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//        }
//    }

}