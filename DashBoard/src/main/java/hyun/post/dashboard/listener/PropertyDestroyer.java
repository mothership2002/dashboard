package hyun.post.dashboard.listener;

import hyun.post.dashboard.exception.CustomAssert;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PropertyDestroyer {

    @Getter
    private final static List<Class<?>> propertyClassList = new ArrayList<>();
    private final AnnotationConfigServletWebServerApplicationContext ac;

    public PropertyDestroyer(ApplicationContext ac) {
        this.ac = (AnnotationConfigServletWebServerApplicationContext) ac;
    }

    public static String parseClassName(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1, name.indexOf("$"));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void destroy() {
        propertyClassList.forEach(property -> ac.removeBeanDefinition(getBeanName(property, ac)));
    }

    private String getBeanName(@NotNull Class<?> clazz, AnnotationConfigServletWebServerApplicationContext ac) {
        String name = parseClassName(clazz.getSimpleName());
        CustomAssert.isTrue(ac.containsBeanDefinition(name), "Not Include Bean");
        return name;
    }



}
