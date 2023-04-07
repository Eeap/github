package com.example.github.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class GitHubApiCommitSearch {
    @Value("${user.token}")
    private String token;
    private GitHub github;
    private final EmailService emailService;
    @JsonIgnore
    public void getCommits(String userId) {
        try {
            this.github = new GitHubBuilder().withOAuthToken(this.token).build();
            this.github.checkApiUrlValidity();
            GHCommitSearchBuilder builder = this.github.searchCommits()
                    .committerName(userId)
                    .sort(GHCommitSearchBuilder.Sort.COMMITTER_DATE);
            PagedSearchIterable<GHCommit> ghCommits = builder.list().withPageSize(5);
            PagedIterator<GHCommit> ghCommitPagedIterator = ghCommits._iterator(5);
            if (ghCommitPagedIterator.hasNext()) {
                GHCommit data = ghCommitPagedIterator.next();
                long compareTime = 1000*60*60*24;
                long now = new Date().getTime();
                long commitTime = data.getCommitDate().getTime();
                if ((now-commitTime) > compareTime) {
                    emailService.sendMsg();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
