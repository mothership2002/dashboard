package hyun.post.dashboard.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
public class RequestBodyCheckWrapper extends HttpServletRequestWrapper {

    private final String requestBody;

    public RequestBodyCheckWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.requestBody = new String(IOUtils.toByteArray(request.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CustomServletInputStream(new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8)));
    }

    private class CustomServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public CustomServletInputStream(ByteArrayInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}
