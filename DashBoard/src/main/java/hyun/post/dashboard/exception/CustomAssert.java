package hyun.post.dashboard.exception;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Slf4j
public class CustomAssert extends Assert {

    public static void hasText(@Nullable String text, String message, final Class<? extends RuntimeException> exceptionClass) {
        if (!StringUtils.hasText(text)) {
            throwException(message, exceptionClass);
        }
    }

    public static void isTrue(@Nonnull Boolean flag, String message, final Class<? extends RuntimeException> exceptionClass) {
        if(!flag) {
            throwException(message, exceptionClass);
        }
    }

    private static void throwException(String message, final Class<? extends RuntimeException> exceptionClass) {
        try {
            throw exceptionClass.getDeclaredConstructor(String.class).newInstance(message);
        } catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("Unknown Throwable in Exception Processing ; " + e.getMessage());
        }
    }
}
