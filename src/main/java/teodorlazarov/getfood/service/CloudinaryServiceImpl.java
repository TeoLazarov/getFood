package teodorlazarov.getfood.service;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {

        File file = File
                .createTempFile("temp-file", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        return this.cloudinary.uploader()
                .upload(file, new HashMap())
                .get("url").toString();
    }

    @Override
    public void deleteImage(String imgUrl) throws IOException {
        //TODO fix
        String parsedUrl = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
//        HashMap options = new HashMap();
//        options.put("invalidate", true);
        this.cloudinary.uploader().destroy(parsedUrl, Cloudinary.asMap("invalidate", true));
    }
}
