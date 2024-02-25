package edu.java.clients;

import edu.java.dto.github.GitHubDTO;

public interface GitHubClient {
    GitHubDTO fetchRepo(String owner, String repo);
}
