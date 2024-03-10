package edu.java.client;

import edu.java.dto.github.GitHubDTO;

public interface GitHubClient {
    GitHubDTO fetchRepo(String owner, String repo);
}
