/*
package com.example.lionking.global.s3;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.s3.bucket}") private String bucket;
    @Value("${cloud.s3.expTime}") private Long expTime;
    private final S3Config s3Config;


    */
/**
     * PUT용 presigned url 발급 -> 이미지 업로드
     *//*


    public GetPresignedUrlResponse getPresignedUrlForPut(GetPresignedUrlRequest getPresignedUrlRequest) {
        String filePath = createPath(getPresignedUrlRequest.prefix(), getPresignedUrlRequest.fileName());
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, filePath, HttpMethod.PUT);
        URL url = s3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest);
        return GetPresignedUrlResponse.of(url.toString(), filePath);
    }

    */
/**
     * GET용 presigned url 발급 -> 이미지 조회
     *//*

    public String getPresignedUrlForGet(String filePath) {
        if (filePath != null) {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, filePath, HttpMethod.GET);
            URL url = s3Config.amazonS3().generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        }
        return filePath;
    }

    */
/**
     * Amazon S3 SDK에서 Presigned URL을 생성하기 위한 요청 객체를 생성
     *//*

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName, HttpMethod method) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(method)
                .withExpiration(getPresignedUrlExpiration());

        return generatePresignedUrlRequest;
    }

    //
    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += expTime;
        expiration.setTime(expTimeMillis);

        return expiration;
    }

    // 파일 경로 생성 -> {prefix}/{yyyyMMddHHmmss}-{UUID}-{파일명}.png
    private String createPath(String prefix, String fileName) {
        String fileUniqueId = UUID.randomUUID().toString();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return String.format("%s/%s-%s-%s", prefix, timestamp, fileUniqueId, fileName);
    }
}
*/
