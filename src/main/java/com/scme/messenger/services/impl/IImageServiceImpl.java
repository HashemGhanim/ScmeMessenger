package com.scme.messenger.services.impl;

import com.scme.messenger.constants.ResponseConstants;
import com.scme.messenger.model.Image;
import com.scme.messenger.repository.ImageRepo;
import com.scme.messenger.services.IImageService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class IImageServiceImpl implements IImageService {

    private final ImageRepo imageRepo;

    @Value("${image.max.size.file}")
    private long maxFileSize;

    @Override
    public void setImage(MultipartFile file, String id) throws Exception {
        String contentType = file.getContentType();
        long size = file.getSize();

        if(contentType != null && contentType.startsWith("image/") && size <= maxFileSize){

            Image image = Image.builder().id(id).mime_type(file.getContentType()).filename(file.getOriginalFilename())
                    .data(file.getBytes()).build();

            imageRepo.save(image);

        }else{
            throw new BadRequestException(ResponseConstants.IMAGE_SIZE_LIMIT);
        }

        return;
    }

    @Override
    public Image getImage(String id) throws BadRequestException {
        Image image = Optional.of(imageRepo.getReferenceById(id))
                .orElseThrow(() -> new BadRequestException(ResponseConstants.Image_NOT_FOUND));

        return image;
    }

}
