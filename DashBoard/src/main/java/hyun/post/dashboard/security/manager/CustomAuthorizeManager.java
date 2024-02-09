package hyun.post.dashboard.security.manager;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CustomAuthorizeManager implements AuthorizationManager<Object> {

    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
    private final Map<String, String> role = new HashMap<>();

    @Override
    public AuthorizationDecision check(Supplier authentication, Object object) {
        return null;
    }


}
