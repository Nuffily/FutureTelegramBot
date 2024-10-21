import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Sandbox2 {
    public static void main(String[] args) throws IOException {
        String fileName = "test.json";
        ObjectMapper mapper = new ObjectMapper();

        List<Person> myPeople = mapper.readValue(new File(fileName), new TypeReference<>(){});
        for(Person myPerson : myPeople) {
            System.out.println(myPerson.toString());
            // => Person{name='Ivan', age=20, contacts={mail=1@mail.ru, tel=25-12-86}}
            // => Person{name='Petr', age=25, contacts={mail=2@mail.ru, tel=35-32-16}}
        }

    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    static class Person {
        String name;
        int age;
        Map<String, String> contacts;

        public Person(){}

        public Person(String name, int age, Map<String, String> contacts) {
            this.name = name;
            this.age = age;
            this.contacts = contacts;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", contacts=" + contacts +
                    '}';
        }
    }
}