package cz.cvut.kbss.ear.eshop;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class HelloWorldTest {

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private HttpServletResponse responseMock;

    private HelloWorld sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.sut = new HelloWorld();
    }

    @Test
    public void doGetReturnsHtmlPageWithHelloWorld() throws Exception {
        final TestOutputStream out = new TestOutputStream();
        when(responseMock.getOutputStream()).thenReturn(out);
        sut.doGet(requestMock, responseMock);
        assertThat(out.content.toString(), containsString("Hello, world!"));
    }

    @Test
    public void doPostReturnsHtmlPageWithHelloWorld() throws Exception {
        final TestOutputStream out = new TestOutputStream();
        when(responseMock.getOutputStream()).thenReturn(out);
        sut.doPost(requestMock, responseMock);
        assertThat(out.content.toString(), containsString("Hello, world!"));
    }

    private static class TestOutputStream extends ServletOutputStream {

        private StringBuilder content = new StringBuilder();

        @Override
        public void print(String s) {
            content.append(s);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // Do nothing
        }

        @Override
        public void write(int b) {
            // Do nothing
        }
    }
}