package com.kafka.connect;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitPushExample {

    private static final Logger logger = LoggerFactory.getLogger(GitPushExample.class);
    private static final String REMOTE_URL = "https://github.com/dnaorem/GitHubActionsDemo.git";
    private static final String LOCAL_REPO_PATH = "D:\\Git\\";
    private static final String USERNAME = "dnaorem";
    private static final String PASSWORD = "DJFebruary@2022";

    public static void main(String[] args) {
        try {


            // Open the local repository
            File repoDir = new File(LOCAL_REPO_PATH);
            Git git = Git.open(repoDir);
            Status status = git.status().call();
			System.out.println("Modified file = " + status.getModified());
            // Add files to the index
            git.add().addFilepattern(".").call();

            // Commit changes
            git.commit().setMessage("Prop file changed").call();

            // Push changes to the remote repository
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("dnaorem", "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"))
                    .call();

            System.out.println("Changes pushed to remote repository successfully!");

        } catch (IOException e) {
            logger.error("IOException occurred: ", e);
        } catch (GitAPIException e) {
            logger.error("GitAPIException occurred: ", e);
        }
    }
}