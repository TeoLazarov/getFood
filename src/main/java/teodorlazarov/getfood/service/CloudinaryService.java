package teodorlazarov.getfood.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    String uploadImage(MultipartFile multipartFile) throws IOException;

    void deleteImage(String imgUrl) throws IOException;

}
