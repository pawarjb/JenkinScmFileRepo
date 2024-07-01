package com.example.Lambdademo;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@SpringBootApplication
public class LambdademoApplication{

	public static void main(String[] args) {
		SpringApplication.run(LambdademoApplication.class, args);
				
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                uploadToS3(selectedFile);
            }
        });
    }

	private static void uploadToS3(File file) {
		String bucketname = System.getenv("BUCKET_NAME");
		String foldername = "Temp";
		String file1 = file.getName();
		String filename =file.getAbsolutePath();
		
		String key = foldername+"/"+file1;


		S3Client client = S3Client.builder().build();
		PutObjectRequest request = PutObjectRequest.builder().bucket(bucketname).key(key).build();
		
        try {
        	client.putObject(request, RequestBody.fromFile(new File(filename)));
    		
            JOptionPane.showMessageDialog(null, "File uploaded successfully to S3!");
        } catch (AwsServiceException e) {
            JOptionPane.showMessageDialog(null, "Error uploading file: " + e.getMessage());
        }
    }

		
		
		


}
