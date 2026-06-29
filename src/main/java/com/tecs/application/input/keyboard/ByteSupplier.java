package com.tecs.application.input.keyboard;

@FunctionalInterface
public interface ByteSupplier {
    int read() throws Exception;
}
