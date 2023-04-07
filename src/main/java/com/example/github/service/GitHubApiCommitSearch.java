package com.example.github.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GitHubApiCommitSearch {
    @Value("${user.token}")
    private String token;
    @Value("${user.userId}")
    private String userId;
    private GitHub github;
    @JsonIgnore
    public void getCommits() {
        try {
            this.github = new GitHubBuilder().withOAuthToken(this.token).build();
            this.github.checkApiUrlValidity();
            GHCommitSearchBuilder builder = this.github.searchCommits()
                    .committerName(this.userId)
                    .sort(GHCommitSearchBuilder.Sort.COMMITTER_DATE);
            PagedSearchIterable<GHCommit> ghCommits = builder.list().withPageSize(1);
            PagedIterator<GHCommit> ghCommitPagedIterator = ghCommits._iterator(1);
            while (ghCommitPagedIterator.hasNext()) {
                GHCommit data = ghCommitPagedIterator.next();
                System.out.println("now date : "+new Date());
                System.out.println(new Date(data.getCommitDate().getTime()));
            }
            System.out.println(new Date());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
