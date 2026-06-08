package com.tecs.application.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.tecs.application.exceptions.CommandLineException;

public class CommandLineParserTest {

    @Test
    void shouldParseHelp() {
        CommandLineParser parser = new CommandLineParser();

        EditorOptions opt1 = parser.parse(new String[]{"--help"});
        assertTrue(opt1.isShowHelp());
        
        EditorOptions opt2 = parser.parse(new String[]{"-h"});
        assertTrue(opt2.isShowHelp());
    }

    @Test
    void shouldParseVersion() {
        CommandLineParser parser = new CommandLineParser();
        EditorOptions opt1 = parser.parse(new String[] {"--version"});
        assertTrue(opt1.isShowVersion());
    
        EditorOptions opt2 = parser.parse(new String[]{"-v"});
        assertTrue(opt2.isShowVersion());
    }

    @Test
    void shouldParseFile() {
        CommandLineParser parser = new CommandLineParser();
        EditorOptions opt1 = parser.parse(new String[]{"notes.txt"});
        assertEquals("notes.txt", opt1.getFileName());
    }

    @Test
    void shouldRejectUnknownFlag() {
        CommandLineParser parser = new CommandLineParser();

        assertThrows(CommandLineException.class, () -> parser.parse(new String[]{"--abd"}));
    }

    @Test
    void shouldRejectMultipleFiles() {
        CommandLineParser parser = new CommandLineParser();

        assertThrows(CommandLineException.class,() -> 
            parser.parse(new String[]{"a.txt", "b.txt"}));
    }
}
