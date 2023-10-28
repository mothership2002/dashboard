package hyun.post.dashboard.model.dto;

import hyun.post.dashboard.security.jwt.AccessToken;
import hyun.post.dashboard.security.jwt.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JsonWebToken {

    private AccessToken accessToken;
    private RefreshToken refreshToken;
}
