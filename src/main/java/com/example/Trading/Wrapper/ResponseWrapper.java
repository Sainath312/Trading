package com.example.Trading.Wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter charArrayWriter = new CharArrayWriter();
    private final ServletOutputStream servletOutputStream = new ServletOutputStream() {
        @Override
        public void write(int b) {
            charArrayWriter.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    };

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter()  {
        return new PrintWriter(charArrayWriter);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    public String getCapturedResponse() {
        return charArrayWriter.toString();
    }
}