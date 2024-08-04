package com.kafka.connect;

import java.io.File;
import java.util.Optional;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

public class CloneRepo {
    public static void main(String[] args) {
        String repoUrl = "https://github.com/dnaorem/GitHubActionsDemo.git";
        String cloneDirectoryPath = "D:\\Git\\";

        try {
            System.out.println("Cloning repository from " + repoUrl + " to " + cloneDirectoryPath);
            Repository repo = Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(new File(cloneDirectoryPath))
                    .call().getRepository();

            
            Git git = new Git(repo);
            
            Optional<String> testBranch = git.branchList().setListMode(ListMode.REMOTE).call().stream()
					.map(r -> r.getName()).filter(n -> n.contains("Test")).findAny();

			/*
			 * If develop branch present then checkout.
			 * 
			 * Equivalent of --> $ git checkout -b local-develop /remotes/origin/develop
			 */
			if (testBranch.isPresent()) {
				System.out.println("\n>>> Checking out test branch\n");
				git.checkout().setCreateBranch(true).setName("Test")
						.setStartPoint(testBranch.get()).call();
				
			}
            System.out.println("Repository cloned successfully.");
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
    }
