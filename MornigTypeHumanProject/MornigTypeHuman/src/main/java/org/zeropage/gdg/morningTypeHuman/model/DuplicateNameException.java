package org.zeropage.gdg.morningTypeHuman.model;

/**
 * Created by Skywave on 13. 10. 4.
 */
public class DuplicateNameException extends Exception {
    public DuplicateNameException() {
        super("중복되는 강의명이 있습니다.");
    }
}
