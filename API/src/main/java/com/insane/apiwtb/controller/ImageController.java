package com.insane.apiwtb.controller;

import com.insane.apiwtb.config.AppConfig;
import com.insane.apiwtb.dto.ImageOut;
import com.insane.apiwtb.interfaces.ImageRepository;
import com.insane.apiwtb.model.Image;
import com.insane.apiwtb.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wtb-v1/image")
public class ImageController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageService imageService;

    @GetMapping
    @ResponseBody
    public List<Image> listAll() {
        return mapList(imageRepository.findAll(), Image.class);
    }

    /**
     * Método para fazer o streaming de uma imagem salva no servidor
     * @param filename nome do arquivo
     * @return imagem binaria
     */
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> streamImage(@PathVariable("filename") String filename) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(appConfig.getImagePath(filename)));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método para fazer o streaming de uma imagem salva no servidor utilizando a id da imagem
     * @param imageId id da imagem salva no banco
     * @return imagem binaria
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> streamImage(@PathVariable("id") Integer imageId) {
        try {
            Image img = imageService.getById(imageId);
            byte[] image = Files.readAllBytes(Paths.get(appConfig.getImagePath(img.getName())));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Upload image MAX file size is 2MB controller will reject with 405 error
     * @param file Multipart file
     * @param response HttpStatus 200, 403, 405, 409 or 500
     * @return ImageOut
     */
    @PostMapping
    public ImageOut upload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        ImageOut imageOut = imageService.save(file);
        response.setStatus(imageOut.getHttpStatus().value());
        return imageOut;
    }

    @PostMapping("/list")
    public List<ImageOut> uploadImages(@RequestParam("files") List<MultipartFile> files, HttpServletResponse response) {
        ArrayList<ImageOut> imagesOut = new ArrayList();
        for (MultipartFile file: files) {
            imagesOut.add(imageService.save(file));
        }
        response.setStatus(imagesOut.get(0).getHttpStatus().value());
        return imagesOut;
    }

    @DeleteMapping
    public ImageOut delete(@RequestParam("id") Integer imageId, HttpServletResponse response) {
        ImageOut imageOut = imageService.delete(imageId);
        response.setStatus(imageOut.getHttpStatus().value());
        return imageOut;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
