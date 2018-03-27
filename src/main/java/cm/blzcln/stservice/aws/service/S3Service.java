package cm.blzcln.stservice.aws.service;

public interface S3Service {
	public String downloadFile(String keyName);
	public void uploadFile(String keyName, String uploadFilePath);
	public void deleteFile(String keyName);
}