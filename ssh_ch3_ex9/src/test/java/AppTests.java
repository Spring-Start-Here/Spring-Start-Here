import com.example.beans.Parrot;
import com.example.beans.Person;
import com.example.config.ProjectConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTests {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void init() {
        context = new AnnotationConfigApplicationContext(ProjectConfig.class);
    }


    @Test
    @DisplayName("Test that the person 'Ella' is in the Spring context")
    void testEllaIsInContext() {
        Person person = context.getBean(Person.class);
        assertEquals("Ella", person.getName());
    }

    @Test
    @DisplayName("Test that the parrot 'Koko' is in the Spring context")
    void testKokoIsInContext() {
        Parrot p = context.getBean("parrot1", Parrot.class);
        assertEquals("Koko", p.getName());
    }

    @Test
    @DisplayName("Test that the parrot 'Miki' is in the Spring context")
    void testMikiIsInContext() {
        Parrot p = context.getBean("parrot2", Parrot.class);
        assertEquals("Miki", p.getName());
    }

    @Test
    @DisplayName("Test that the person 'Ella' is linked to parrot 'Miki'")
    void testEllaIsLinkedToMiki() {
        Person person = context.getBean(Person.class);
        assertEquals("Miki", person.getParrot().getName());
    }

}
