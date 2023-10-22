package hyun.post.dashboard.security.encrypt;

import org.springframework.stereotype.Component;

@Component
public class EncryptionProvider {

    public String encrypting(String password) {
        return password;
    }

    public String decrypting(String password) {
        return password;
    }
}
