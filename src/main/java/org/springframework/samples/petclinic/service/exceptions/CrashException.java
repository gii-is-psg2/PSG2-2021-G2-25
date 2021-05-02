package org.springframework.samples.petclinic.service.exceptions;

public class CrashException extends RuntimeException {
	public CrashException() {
        super();
    }
    public CrashException(String s) {
        super(s);
    }
    public CrashException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public CrashException(Throwable throwable) {
        super(throwable);
    }

}