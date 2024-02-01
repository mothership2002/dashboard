package hyun.post.dashboard.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CustomServletInputStream extends ServletInputStream {

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