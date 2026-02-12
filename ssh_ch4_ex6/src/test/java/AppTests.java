import com.example.config.ProjectConfiguration;
import com.example.proxies.CommentNotificationProxy;
import com.example.proxies.CommentPushNotificationProxy;
import com.example.proxies.EmailCommentNotificationProxy;
import com.example.repositories.CommentRepository;
import com.example.repositories.DBCommentRepository;
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
    @DisplayName("Test that the CommentNotificationProxy implementations are in the Spring context")
    void testCommentNotificationProxyImplementationsAreInContext() {
        CommentNotificationProxy emailProxy = context.getBean(EmailCommentNotificationProxy.class);
        CommentNotificationProxy pushProxy = context.getBean(CommentPushNotificationProxy.class);
        assertNotNull(emailProxy);
        assertNotNull(pushProxy);
    }

    @Test
    @DisplayName("Test that CommentService is linked to CommentRepository and CommentNotificationProxy")
    void testCommentServiceIsLinkedToDependencies() {
        CommentService service = context.getBean(CommentService.class);
        CommentRepository repository = context.getBean(DBCommentRepository.class);
        CommentNotificationProxy proxy = context.getBean(CommentPushNotificationProxy.class);

        Object injectedRepository = ReflectionTestUtils.getField(service, "commentRepository");
        Object injectedProxy = ReflectionTestUtils.getField(service, "commentNotificationProxy");

        assertSame(repository, injectedRepository);
        assertSame(proxy, injectedProxy);
    }

}
