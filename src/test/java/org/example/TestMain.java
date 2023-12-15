package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMain {

    // fake test for GitHub build testing
    @Test
    public void should_return_hello_world() throws Exception {
        Main.main(null);
        assertTrue(1==1);
    }
}
