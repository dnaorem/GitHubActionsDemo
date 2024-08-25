package com.kafka.connect;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.api.Git;


public class CloneRepo {
   // public static void main(String[] args) {
    public void fetchGitFile() {
    	
        String repoUrl = "https://github.com/dnaorem/GitHubActionsDemo.git";
        String cloneDirectoryPath = "D:\\Git\\";

        try {
        	getComponent();
     /*       System.out.println("Cloning repository from " + repoUrl + " to " + cloneDirectoryPath);
            Repository repo = Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(new File(cloneDirectoryPath))
                    .call().getRepository();

            
            Git git = new Git(repo);
            
            Optional<String> testBranch = git.branchList().setListMode(ListMode.REMOTE).call().stream()
					.map(r -> r.getName()).filter(n -> n.contains("Test")).findAny();
*/
			/*
			 * If develop branch present then checkout.
			 * 
			 * Equivalent of --> $ git checkout -b local-develop /remotes/origin/develop
			 */
/*			if (testBranch.isPresent()) {
				System.out.println("\n>>> Checking out test branch\n");
				git.checkout().setCreateBranch(true).setName("Test")
						.setStartPoint(testBranch.get()).call();
				
			}*/
		} /*
			 * catch (GitAPIException e) { e.printStackTrace(); }
			 */
        	catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void getComponent() throws IOException
    {
    	try {
	    	File gitDir = new File("./git/");
	    	if((gitDir.list() == null) || (gitDir.list().length == 0)) {
	    		System.out.println("Fetching application.properties file starts...");
		    	Git repo = Git.cloneRepository()
		    	          .setURI("https://github.com/dnaorem/GitHubActionsDemo.git")
		    	          .setDirectory(gitDir)
		    	          .setBranchesToClone(Arrays.asList("refs/heads/Test"))
		    	          .setCloneAllBranches(false)
		    	          .setCloneSubmodules(true)
		    	          .setNoCheckout(true)
		    	          .call();
		
		    	         repo.checkout().setStartPoint("origin/Test").addPath("ConnectKafka/src/main/resources/application.properties").call();
		    	         System.out.println("############################## application.properties checked out successfully.");

	    	}else {
	    		System.out.println("################################### File application.properties already checked out.");
	    	}
	    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }  

  }
