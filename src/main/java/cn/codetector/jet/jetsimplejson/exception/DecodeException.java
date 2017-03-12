package cn.codetector.jet.jetsimplejson.exception;

/**
 * Created by Codetector on 2017/3/12.
 * Project Jet
 */
public class DecodeException extends RuntimeException{
    public DecodeException() {
    }

    public DecodeException(String message) {
        super(message);
    }

    public DecodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
