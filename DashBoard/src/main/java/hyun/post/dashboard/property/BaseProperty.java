package hyun.post.dashboard.property;

import hyun.post.dashboard.listener.PropertyDestroyer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseProperty {

    @PreDestroy
    public void destroy() {
        log.info("{} is destroyed.",
                PropertyDestroyer.parseClassName(this.getClass().getSimpleName()));
    }

    @PostConstruct
    public void init() {
        PropertyDestroyer.getPropertyClassList().add(this.getClass());
        log.info("{} is constructed.",
                PropertyDestroyer.parseClassName(this.getClass().getSimpleName()));
    }
}
