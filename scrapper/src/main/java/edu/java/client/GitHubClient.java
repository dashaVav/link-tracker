package edu.java.client;

import edu.java.dto.github.GitHubDTO;
import java.util.List;

public interface GitHubClient {
    List<GitHubDTO> fetchRepo(String owner, String repo);
}
