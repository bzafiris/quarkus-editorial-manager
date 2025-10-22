package gr.aueb.edtmgr.util;

import gr.aueb.edtmgr.util.SystemDate;

import java.time.LocalDate;

public class SystemDateStub {

    public static void setStub(LocalDate stub) {
        SystemDate.setStubObject(stub);
    }
    
    public static void reset() {
        SystemDate.removeStub();
    }
    
}