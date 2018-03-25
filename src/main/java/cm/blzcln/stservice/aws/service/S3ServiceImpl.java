package cm.blzcln.stservice.aws.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service("s3Service")
public class S3ServiceImpl implements S3Service {

	private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${student.s3.bucket}")
	private String bucketName;
	
	@Value("${student.img.tmp.dir}")
	private String tmpDir;

	@Override
	public String downloadFile(String keyName) {
		File localFile = new File(tmpDir, keyName);
		try {
			System.out.println("Downloading an object");
			
			//ObjectMetadata object = s3client.getObject(new GetObjectRequest(bucketName, keyName), localFile);
			s3client.getObject(new GetObjectRequest(bucketName, keyName), localFile);
			
			logger.info("===================== Import File - Done! =====================");
		} catch (AmazonServiceException ase) {
			handleAWSServiceException("GET", ase);
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		} 
		return localFile.getAbsolutePath();
	}

	@Override
	public void uploadFile(String keyName, String uploadFilePath) {
		try {
			File file = new File(uploadFilePath);
			s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
			logger.info("===================== Upload File - Done! =====================");
		} catch (AmazonServiceException ase) {
			handleAWSServiceException("PUT", ase);
		} catch (AmazonClientException ace) {
			logger.info("Caught an AmazonClientException: ");
			logger.info("Error Message: " + ace.getMessage());
		}
	}
	
	private void handleAWSServiceException(String action, AmazonServiceException e){
		logger.info("Caught an AmazonServiceException from "+ action + " requests, rejected reasons:");
		logger.info("Error Message:    " + e.getMessage());
		logger.info("HTTP Status Code: " + e.getStatusCode());
		logger.info("AWS Error Code:   " + e.getErrorCode());
		logger.info("Error Type:       " + e.getErrorType());
		logger.info("Request ID:       " + e.getRequestId());
	}
}
