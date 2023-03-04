package com.insane.apiwtb.services;

import com.insane.apiwtb.config.AppConfig;
import com.insane.apiwtb.dto.ImageOut;
import com.insane.apiwtb.exception.ImageNotFoundException;
import com.insane.apiwtb.interfaces.ImageRepository;
import com.insane.apiwtb.model.Image;
import com.insane.apiwtb.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class ImageService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AppConfig appConfig;

    /**
     * Método para obter do banco de dados uma imagem através de sua id
     * @param imageId id da imagem
     * @return Image
     */
    public Image getById(Integer imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException(imageId));
    }

    /**
     * Método para fazer o upload de uma imagem no servidor
     * É adicionado ao objeto ImageOut uma resposta HttpStatus 200, 403, 409 ou 500
     * @param file arquivo a ser enviado
     * @return ImageOut
     */
    public ImageOut save(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        assert originalFileName != null;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = new Date().getTime()+fileExtension;

        // Image to be persisted in Database
        Image image = Image.builder()
                .name(fileName)
                .created(new Date())
                .updated(new Date())
                .build();

        ImageOut imageOut = mapper.map(image, ImageOut.class);
        imageOut.setImageURL(appConfig.getImageURL(image.getName()));

        if (Utils.checkCreateFolder(appConfig.getImagePath())) {
            if (!imageRepository.existsImageByName(image.getName())) {
                try {
                    file.transferTo(new File(appConfig.getImagePath(fileName)));
                    imageOut.setId(imageRepository.save(image).getId());
                    imageOut.setHttpStatus(HttpStatus.OK);
                    imageOut.setMessage("Arquivo salvo com sucesso!");
                } catch (IOException e) {
                    imageOut.setMessage("Ocorreu um erro ao salvar o arquivo!");
                    imageOut.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                imageOut.setMessage("Arquivo já existe!");
                imageOut.setHttpStatus(HttpStatus.CONFLICT);
            }
        } else {
            imageOut.setMessage("Permissão negada para salvar o arquivo!");
            imageOut.setHttpStatus(HttpStatus.FORBIDDEN);
        }
        return imageOut;
    }

    /**
     * Método para deletar uma imagem no servidor e o registro no banco
     * @param imageId id da imagem no banco
     * @return ImageOut
     */
    public ImageOut delete(Integer imageId) {

        Image image = this.getById(imageId);
        ImageOut imageOut = mapper.map(image, ImageOut.class);
        imageOut.setImageURL(appConfig.getImageURL(image.getName()));

        if (Utils.deleteFile(appConfig.getImagePath(image.getName()))) {
            imageRepository.delete(image);
            imageRepository.flush();
            imageOut.setHttpStatus(HttpStatus.OK);
            imageOut.setMessage("Imagem apagada com sucesso!");
        } else {
            imageOut.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            imageOut.setMessage("Não foi possível apagar a imagem!");
        }

        return imageOut;
    }

}
