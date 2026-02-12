import com.example.config.ProjectConfiguration;
import com.example.proxies.CommentNotificationProxy;
import com.example.repositories.CommentRepository;
import com.example.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AppTests {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void init() {
        context = new AnnotationConfigApplicationContext(ProjectConfiguration.class);
    }


    @Test
    @DisplayName("Test that the CommentService is in the Spring context")
    void testCommentServiceIsInContext() {
        CommentService service = context.getBean(CommentService.class);
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test that the CommentRepository is in the Spring context")
    void testCommentRepositoryIsInContext() {
        CommentRepository repository = context.getBean(CommentRepository.class);
        assertNotNull(repository);
    }

    @Test
    @DisplayName("Test that the CommentNotificationProxy is in the Spring context")
    void testCommentNotificationProxyIsInContext() {
        CommentNotificationProxy proxy = context.getBean(CommentNotificationProxy.class);
        assertNotNull(proxy);
    }

    @Test
    @DisplayName("Test that CommentService is linked to CommentRepository and CommentNotificationProxy")
    void testCommentServiceIsLinkedToDependencies() {
        CommentService service = context.getBean(CommentService.class);
        CommentRepository repository = context.getBean(CommentRepository.class);
        CommentNotificationProxy proxy = context.getBean(CommentNotificationProxy.class);

        Object injectedRepository = ReflectionTestUtils.getField(service, "commentRepository");
        Object injectedProxy = ReflectionTestUtils.getField(service, "commentNotificationProxy");

        assertSame(repository, injectedRepository);
        assertSame(proxy, injectedProxy);
    }

}
