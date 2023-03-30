package com.example.github.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
public class GithubApiController {
    @Value("${user.token}")
    private String token;
    @Value("${user.userId}")
    private String userId;
    private GitHub github;
    @GetMapping("/test")
    @ResponseBody
    @JsonIgnore
    public void getCommits() {
        try {
            this.github = new GitHubBuilder().withOAuthToken(this.token).build();
            this.github.checkApiUrlValidity();
            GHCommitSearchBuilder builder = this.github.searchCommits()
                    .authorName(this.userId)
                    .sort(GHCommitSearchBuilder.Sort.AUTHOR_DATE);
            PagedSearchIterable<GHCommit> ghCommits = builder.list().withPageSize(2);
            PagedIterator<GHCommit> ghCommitPagedIterator = ghCommits._iterator(2);
            while (ghCommitPagedIterator.hasNext()) {
                GHCommit data = ghCommitPagedIterator.next();
                System.out.println(new Date(data.getCommitDate().getTime()));
            }
            System.out.println(new Date());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
