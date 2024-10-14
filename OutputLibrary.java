import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class OutputLibrary {
    HashMap<String, String> MainQuotes;
    HashMap<String, String> JSQuotes;
    HashMap<String, String[]> RandomQuotes;
    HashMap<String, String> JSQuestions;
    HashMap<String, String> EngQuestions;

    OutputLibrary() {
        MainQuotes = new HashMap<String, String>();
        JSQuotes = new HashMap<String, String>();
        RandomQuotes = new HashMap<String, String[]>();
    }

    public void fillMaps() {
        fillMapSingleQuotes(MainQuotes, "quotesMain.txt");
        fillMapSingleQuotes(JSQuotes, "quotesJS.txt");
    }


    private void fillMapSingleQuotes(HashMap<String, String> map, String fileName) {
        String response = "";

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String s = "";
            while ((s = br.readLine()) != null) {

                response = "";

                if (s.equals("$single")) {

                    while ((s = br.readLine()) != null && !s.equals("$")) {
                        response += s + "\n";
                    }

                    while (((s = br.readLine()) != null) && !s.equals("$end")) {
                        map.put(s, response);
                    }
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
            String cur;
            Stack<String> stack = new Stack<String>();
            int ResponseCount;

            while ((s = br.readLine()) != null) {

                if (s.equals("$random")) {

                    cur = "";
                    ResponseCount = 0;
                    while ((s = br.readLine()) != null && !s.equals("$$")) {
                        while ((s = br.readLine()) != null && !s.equals("$")) {
                            cur += s;
                        }
                        stack.push(cur);
                        ResponseCount++;
                    }

                    responses = new String[ResponseCount];

                    for (int i = 0; i < ResponseCount; i++)
                        responses[i] = stack.pop();

                    while (((s = br.readLine()) != null) && !s.equals("$end")) {
                        map.put(s, responses);
                    }
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
