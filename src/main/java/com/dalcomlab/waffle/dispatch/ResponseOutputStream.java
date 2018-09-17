package com.dalcomlab.waffle.dispatch;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResponseOutputStream extends ServletOutputStream {

    private ResponseOutputStreamListener listener;
    private int bufferCapacity = 1024 * 1024;
    private byte[] buffer = new byte[bufferCapacity];
    private long contentLength = 0;
    private int writtenLength = 0;

    public ResponseOutputStream(ResponseOutputStreamListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for <code>write</code> is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument <code>b</code>. The 24
     * high-order bits of <code>b</code> are ignored.
     * <p>
     * Subclasses of <code>OutputStream</code> must provide an
     * implementation for this method.
     *
     * @param b the <code>byte</code>.
     * @throws IOException if an I/O error occurs. In particular,
     *                     an <code>IOException</code> may be thrown if the
     *                     output stream has been closed.
     */
    @Override
    public void write(int b) throws IOException {
        if(writtenLength == bufferCapacity) {
            flush();
        }
        buffer[writtenLength] = (byte)b;
        contentLength++;
        writtenLength++;
    }

    public void flushBuffer() throws IOException {
        flush();
    }

    public void resetBuffer() {
        contentLength -= writtenLength;
        writtenLength = 0;
    }


    public void reset() {
        contentLength = 0;
        writtenLength = 0;
    }

    public void flush() {
        if (this.listener != null) {
            this.listener.flush(this.buffer, writtenLength);
        }
        writtenLength = 0;
    }
}
