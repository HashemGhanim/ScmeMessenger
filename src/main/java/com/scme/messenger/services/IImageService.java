package com.scme.messenger.services;

import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import com.scme.messenger.model.Image;

public interface IImageService {

    void setImage(MultipartFile file, String id) throws Exception;

    Image getImage(String id) throws BadRequestException;

}
