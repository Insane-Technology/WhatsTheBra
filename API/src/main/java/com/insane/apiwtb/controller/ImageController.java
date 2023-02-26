package com.insane.apiwtb.controller;

import com.insane.apiwtb.config.AppConfig;
import com.insane.apiwtb.dto.ImageOut;
import com.insane.apiwtb.interfaces.ImageRepository;
import com.insane.apiwtb.model.Image;
import com.insane.apiwtb.services.ImageService;
import com.insane.apiwtb.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
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
    public List<Image> imageList() {
        return mapList(imageRepository.findAll(), Image.class);
    }

    /**
     * Método para fazer o streaming de uma imagem salva no servidor
     * @param filename nome do arquivo
     * @return imagem binaria
     */
    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(appConfig.getImagePath()+"/"+filename));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(image);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Método para fazer o upload de uma imagem no servidor
     * @param file arquivo de imagem
     * @param response resposta http do servidor 200, 403, 409 ou 500
     * @return objeto ImageOut
     */
    @PostMapping
    public ImageOut uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {

        String imagePath = appConfig.getImagePath();
        String fileName = file.getOriginalFilename();

        Image image = Image.builder()
                .name(fileName)
                .created(new Date())
                .updated(new Date())
                .build();

        ImageOut imageOut = ImageOut.builder()
                .id(image.getId())
                .name(image.getName())
                .imageURL(appConfig.getImageURL(image.getName()))
                .build();

        if (Utils.checkCreateFolder(imagePath)) {
            if (!imageRepository.existsImageByName(image.getName())) {
                try {
                    file.transferTo(new File(imagePath + "/" + fileName));
                    imageOut.setId(imageRepository.save(image).getId());
                    response.setStatus(HttpStatus.OK.value());
                    imageOut.setMessage("Arquivo salvo com sucesso!");

                } catch (IOException e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    imageOut.setMessage("Ocorreu um erro ao salvar o arquivo!");
                }
            }
            else {
                response.setStatus(HttpStatus.CONFLICT.value());
                imageOut.setMessage("Arquivo já existe!");
            }
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            imageOut.setMessage("Permissão negada para salvar o arquivo!");
        }
        return imageOut;
    }

    /**
     * Método para deletar uma imagem do servidor e o registro no banco
     * @param imageId id da imagem no banco
     * @param response resposta http do servidor 200, 405
     * @return objeto ImageOut
     */
    @DeleteMapping
    public ImageOut deleteFile(@RequestParam("id") Integer imageId, HttpServletResponse response) {

        Image image = imageService.getById(imageId);
        ImageOut imageOut = ImageOut.builder()
                .id(image.getId())
                .name(image.getName())
                .imageURL(appConfig.getImageURL(image.getName()))
                .build();

        if (Utils.deleteFile(appConfig.getImagePath()+"/"+image.getName())) {
            imageRepository.delete(image);
            imageRepository.flush();
            response.setStatus(HttpStatus.OK.value());
            imageOut.setMessage("Imagem apagada com sucesso!");
        }

        return imageOut;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
