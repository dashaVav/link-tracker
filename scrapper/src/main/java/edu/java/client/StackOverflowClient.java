package edu.java.client;

import edu.java.dto.stackoverflow.StackOverflowDTO;

public interface StackOverflowClient {
    StackOverflowDTO fetchAnswersByQuestionId(Long id);

    String getMessage(StackOverflowDTO.Item event);
}
