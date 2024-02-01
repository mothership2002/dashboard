package hyun.post.dashboard.exception.entity;

import jakarta.persistence.EntityNotFoundException;

public class NotFoundAccessToken  extends EntityNotFoundException {


    public NotFoundAccessToken(String message) {
        super(message);
    }
}
