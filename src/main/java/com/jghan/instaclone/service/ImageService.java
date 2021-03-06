package com.jghan.instaclone.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jghan.instaclone.config.auth.PrincipalDetails;
import com.jghan.instaclone.domain.image.Image;
import com.jghan.instaclone.domain.image.ImageRepository;
import com.jghan.instaclone.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image>popularImg(){

        return imageRepository.mPopular();
    }


    @Transactional(readOnly = true)
    public List<Image>imageStory(int principalId){
        List<Image> images = imageRepository.mStory(principalId);

        //images에 좋아요 상태 탐기기

        //2로 로그인 -> 2가 팔로우한 계정의 이미지롤 for문으로 모두 가져오기
        //-> 각 이미지의 좋아요 정보를 모두 가져와서
        //-> 그 좋아요가 내가 좋아요 한지 판별하기(image의 like정보의 user와 principalId가 같은지 보기)
        images.forEach((image)->{

            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if(like.getUser().getId() == principalId){ //해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
                    image.setLikeState(true);
                }

            });
        });

       return images;
    }




    @Value("${file.path}") //application.properties에서 가져옴
    private String uploadFolder;

//    @Transactional
//    public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
//
//        UUID uuid = UUID.randomUUID();
//        String imageFilename = uuid + "_"+ imageUploadDto.getFile().getOriginalFilename(); //db에 저장되는 파일명
//        System.out.println("이미지 파일이름: " + imageFilename);
//
//        Path imageFilePath = Paths.get(uploadFolder+imageFilename);
//
//        // 통신, I/O -> 예외가 발생할 수 있다.
//        try {
//            Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //(파일 패스, 실제 이미지 파일)
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        //Image 테이블에 저장
//        Image image = imageUploadDto.toEntity(imageFilename, principalDetails.getUser());
//        imageRepository.save(image);
//
//
//    }

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    @Transactional
    public void upload_S3(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){

        UUID uuid = UUID.randomUUID();
        String imageFilename = uuid + "_"+ imageUploadDto.getFile().getOriginalFilename(); //db에 저장되는 파일명
        System.out.println("이미지 파일이름: " + imageFilename);

        Path imageFilePath = Paths.get(uploadFolder+imageFilename);

        // 이미지 s3서버에 저장
        try {
//            Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //(파일 패스, 실제 이미지 파일)
            s3Client.putObject(new PutObjectRequest(bucket, imageFilename, imageUploadDto.getFile().getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (Exception e){
            e.printStackTrace();
        }

        //Image db 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFilename, principalDetails.getUser());
        imageRepository.save(image);


    }

//    public String upload(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//
//        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return s3Client.getUrl(bucket, fileName).toString();
//    }


    @Transactional
    public void deleteImage(int imageId){
        imageRepository.deleteImage(imageId);
    }
}
