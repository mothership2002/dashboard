package hyun.post.dashboard.model.common;

import hyun.post.dashboard.exception.AllowAllMethodException;

public enum HttpMethod {
    GET, POST, PUT, PATCH, DELETE, ALL;

    public org.springframework.http.HttpMethod convert() {
        if (this.name().equals(HttpMethod.ALL.name())) {
           throw new AllowAllMethodException();
        }
        return org.springframework.http.HttpMethod.valueOf(this.name());
    }
}
