//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.File;
//import java.io.IOException;
//
//public class Sandbox2 {
//    public static void main(String[] args) throws IOException {
//        File file = new File("src/main/java/Sandbox.json");
//
//        pojoToJsonString();
//    }
//
//    public static void pojoToJsonString() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//
//
//        File file = new File("src/main/java/Sandbox.json");
//
//        Employee[] employee = objectMapper.readValue(file, Employee[].class);
//
//        System.out.println(employee[1].age);
//    }
//}
//
