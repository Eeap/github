package com.example.github.controller;

import com.example.github.service.GitHubApiCommitSearch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class GithubApiController {
    private final GitHubApiCommitSearch apiCommitSearch;
    @GetMapping("/test")
    @ResponseBody
    @JsonIgnore
    public void getCommits() {
        apiCommitSearch.getCommits();
    }
}
