import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class OutputLibrary {
    HashMap<String, String> MainCommands;
    HashMap<String, String> JSCommands;
    HashMap<String, String> singleQuotes;
    HashMap<String, String[]> randomQuotes;
    Question[] JSQuestions;
    HashMap<String, String> EngQuestions;

    OutputLibrary() {
        MainCommands = new HashMap<String, String>();
        JSCommands = new HashMap<String, String>();
        singleQuotes = new HashMap<String, String>();
        randomQuotes = new HashMap<String, String[]>();
        JSQuestions = new Question[39];
    }

    public void fillMaps() {
        fillCommands(MainCommands, "MainCommands.txt");
        fillCommands(JSCommands, "JSCommands.txt");
        fillMapSingleQuotes(singleQuotes, "quotes.txt");
        fillMapRandomQuotes(randomQuotes, "quotes.txt");
        fillQuestions(JSQuestions, "questionsJS.txt");
    }

    private void fillQuestions(Question[] arr, String fileName) {

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

                arr[questNumber++] = question;
//                System.out.println(arr[questNumber - 1].number);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }


    }

    private void fillCommands(HashMap<String, String> map, String fileName) {

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String command = "";
            String s = "";
            while ((s = br.readLine()) != null) {

                while ((s = br.readLine()) != null && !s.equals("$")) { }

                command = br.readLine();
                br.readLine();

                while (((s = br.readLine()) != null) && !s.equals("$")) {
                    map.put(s, command);
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void fillMapSingleQuotes(HashMap<String, String> map, String fileName) {

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String response = "";
            String s = "";
            while ((s = br.readLine()) != null) {

                response = "";

                if (s.equals("$s")) {

                    while ((s = br.readLine()) != null && !s.equals("$")) {
                        response += s + "\n";
                    }

                    s = br.readLine();
                    map.put(s, response);
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void fillMapRandomQuotes(HashMap<String, String[]> map, String fileName) {
        String responses[];

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String s = "";
            String cur = "";
            Stack<String> stack = new Stack<String>();
            int ResponseCount;

            while ((s = br.readLine()) != null) {

                if (s.equals("$r")) {

                    ResponseCount = 0;
                    a: while (true) {
                        while ((s = br.readLine()) != null && !s.equals("$") && !s.equals("$$")) {
                            cur += s;
                        }
                        stack.push(cur);
                        ResponseCount++;
                        cur = "";
                        if (s == null || s.equals("$$")) break;
                    }

                    responses = new String[ResponseCount];

                    for (int i = 0; i < ResponseCount; i++)
                        responses[i] = stack.pop();

                    s = br.readLine();
                    Some.shuffleArray(responses);
                    map.put(s, responses);
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


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