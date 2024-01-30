package hyun.post.dashboard.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RequestLog {

    private boolean logFlag = true;
    public void inboundLog(String loggingFormat, Object... objects) {
        if (logFlag) {
            log.info(loggingFormat, objects);
        }
    }

//    public void convertedLog(String loggingFormat, Object... objects) {
//        if (logFlag) {
//            log.info(loggingFormat, objects);
//        }
//    }

    public boolean changeFlag(boolean flag) {
        this.logFlag = flag;
        return logFlag;
    }
}
