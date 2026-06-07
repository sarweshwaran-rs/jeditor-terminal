package com.tecs.application;

public enum ExitCode {
    SUCCESS(0),
    INVALID_ARGUMENTS(1),
    FILE_ERROR(2),
    INTERNAL_ERROR(3);

    private final int code;

    ExitCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
