package com.dalcomlab.waffle.dispatch;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public interface ResponseOutputStreamListener {
    void flush(byte[] buffer, int length);
}
