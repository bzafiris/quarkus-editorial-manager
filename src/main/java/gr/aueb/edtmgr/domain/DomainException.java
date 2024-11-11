package gr.aueb.edtmgr.domain;

public class DomainException extends RuntimeException {

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }
}
