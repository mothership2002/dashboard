package hyun.post.dashboard.listener;

import hyun.post.dashboard.exception.CustomAssert;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PropertyDestroyer {

    @Getter
    private final static List<Class<?>> propertyClassList = new ArrayList<>();
    private final ApplicationContext ac;

    public PropertyDestroyer(ApplicationContext ac) {
        this.ac = ac;
    }

    public static String parseClassName(String name) {
        return name.substring(0, 1).toLowerCase() + name.substring(1, name.indexOf("$"));
    }

//    @EventListener(ApplicationReadyEvent.class)
    public void destroy() {
        if (ac instanceof AnnotationConfigServletWebServerApplicationContext) {
            propertyClassList.forEach(property -> {
                AnnotationConfigServletWebServerApplicationContext aac = (AnnotationConfigServletWebServerApplicationContext) ac;
                aac.removeBeanDefinition(getBeanName(property));
            });
        }
    }

    private String getBeanName(@NotNull Class<?> clazz) {
        String name = parseClassName(clazz.getSimpleName());
        CustomAssert.isTrue(ac.containsBeanDefinition(name), "Not Include Bean");
        return name;
    }


}
