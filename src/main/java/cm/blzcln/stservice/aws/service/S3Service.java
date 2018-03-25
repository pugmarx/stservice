package cm.blzcln.stservice.aws.service;

public interface S3Service {
	public void downloadFile(String keyName);
	public void uploadFile(String keyName, String uploadFilePath);
}