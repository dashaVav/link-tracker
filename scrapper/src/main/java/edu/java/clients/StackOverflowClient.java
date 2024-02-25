package edu.java.clients;

import edu.java.dto.stackoverflow.StackOverflowDTO;

public interface StackOverflowClient {
    StackOverflowDTO fetchQuestion(Long id);
}
