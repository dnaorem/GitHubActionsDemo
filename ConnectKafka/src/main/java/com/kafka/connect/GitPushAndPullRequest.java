package com.kafka.connect;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kafka.connect.model.KafkaConfig;

@Component
public class GitPushAndPullRequest {

    private static final Logger logger = LoggerFactory.getLogger(GitPushAndPullRequest.class);
    
   
    @Autowired
    KafkaConfig kafkaConfig;

    //public static void main(String[] args) {
    public void commitGit(String token) {
        try {


            // Open the local repository
            File repoDir = new File(kafkaConfig.getLocalRepoPath());
            Git git = Git.open(repoDir);
            Status status = git.status().call();
			System.out.println("Modified file = " + status.getModified());
            // Add files to the index
            git.add().addFilepattern(".").call();

            // Commit changes
            git.commit().setMessage("Prop file changed").call();

            // Push changes to the remote repository
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(kafkaConfig.getGithubUser(), token))
                    .call();

            System.out.println("Changes pushed to remote repository successfully!");
            
            createPullRequest(token);
            System.out.println("Pull Request created successfully for reviewer!");

        } catch (IOException e) {
            logger.error("IOException occurred: ", e);
        } catch (GitAPIException e) {
            logger.error("GitAPIException occurred: ", e);
        }
    }
    
    public void createPullRequest(String token) {
    	HttpURLConnection connection = null;
    	try {
    		URL obj = new URI(kafkaConfig.getPullRequestUrl()).toURL();
    		connection = (HttpURLConnection)obj.openConnection();
    		
    		connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setRequestProperty("Accept", "application/vnd.github+json");
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
    		
            try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
                os.writeBytes("{\"title\":\"New Topic Added by Developer\",\"body\":\"Please pull these new changes in!\",\"head\":\""+kafkaConfig.getGithubUser()+":"+kafkaConfig.getGithubBranchName()+"\",\"base\":\"main\"}");
                os.flush();
            }
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                StringBuilder response = new StringBuilder();

                try (
                    BufferedReader reader = new BufferedReader( new InputStreamReader( connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line); 
                    }
                }
                System.out.println("Response: " + response.toString());
            }
            else {
                System.out.println("Error: HTTP Response code - " + responseCode);
                throw new RuntimeException();
            }
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (connection != null) {
    			connection.disconnect();
    		}
    	}
    }
    
}
