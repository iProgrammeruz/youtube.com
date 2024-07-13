package com.braindevs.service;

import com.braindevs.dto.attach.AttachDto;
import com.braindevs.dto.attach.AttachShortInfoDto;
import com.braindevs.entity.AttachEntity;
import com.braindevs.exp.AppBadException;
import com.braindevs.repository.AttachRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachService {

    private final AttachRepository attachRepository;
    @Value("${attach.open.url}")
    private String serverUrl;

    //Save to system
    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    //Save to file server
    public AttachDto saveAttach(MultipartFile file) {
        try {
            String pathFolder = getYmDString(); // 2024/06/08
            File folder = new File("uploads/" + pathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String id = UUID.randomUUID().toString();
            String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename())); // dasda.asdas.dasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get("uploads/" + pathFolder + "/" + id + "." + extension);
            Files.write(path, bytes);
            // Save to DB
            AttachEntity entity = new AttachEntity();
            entity.setId(id + "." + extension); // dasdasd-dasdasda-asdasda-asdasd.jpg
            entity.setPath(pathFolder); // 2024/06/08
            entity.setOriginalName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setExtension(extension);
            attachRepository.save(entity);
            return toDTO(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day;
    }

    public String getExtension(String fileName) { // mp3/jpg/jfif/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    public byte[] loadImage(String fileName) {
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("attaches/" + fileName));
        } catch (Exception e) {
            return new byte[0];
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            return new byte[0];
        }
    }


    public byte[] load(String attachId) {
        BufferedImage originalImage;
        try {
            // read from db
            AttachEntity entity = get(attachId);
            originalImage = ImageIO.read(new File("uploads/" + entity.getPath() + "/" + attachId));
            // read from system
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


    public byte[] openGeneral(String attachId) {
        byte[] data;
        try {
            AttachEntity entity = get(attachId);
            String path = entity.getPath() + "/" + attachId + entity.getExtension();
            Path file = Paths.get("uploads/" + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    public ResponseEntity<Resource> download(String attachId) {
        try {
            AttachEntity entity = get(attachId);
            String path = entity.getPath() + "/" + attachId;
            Path file = Paths.get("uploads/" + path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public AttachDto toDTO(AttachEntity entity) {
        AttachDto dto = new AttachDto();
        dto.setId(entity.getId());
        dto.setCreatedData(entity.getCreatedData());
        dto.setPath(entity.getPath());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setOriginalName(entity.getOriginalName());
        dto.setUrl(serverUrl + "/attach/open2/" + entity.getId());
        return dto;
    }


    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Attach not found");
        });
    }

    public AttachDto getDTOWithURL(String attachId) {
        AttachEntity attach = attachRepository.findById(attachId)
                .orElseThrow(() -> new AppBadException("Attach not found"));
        AttachDto dto = new AttachDto();
        dto.setId(attachId);
        dto.setUrl(serverUrl + "/" + "uploads/" + attach.getPath() + "/" + attachId);
        return dto;
    }


    public void delete(String fileName) {
        AttachEntity entity = get(fileName);
        File file = new File("uploads/" + entity.getPath() + "/" + fileName);
        if (!file.delete()) {
            throw new AppBadException("file not deleted");
        }
        attachRepository.delete(entity);
    }

    public String asUrlString(String attachId) {
        if (attachId == null) {
            return null;
        }
        // return http://localhost:8080/attach/open_general/fsdseafrwavae.jpg
        return serverUrl + attachId;
    }

    public AttachShortInfoDto toDto(String attachId) {
        AttachShortInfoDto attachShortInfoDto = new AttachShortInfoDto();
        attachShortInfoDto.setId(attachId);
        attachShortInfoDto.setUrl(serverUrl + attachId);
        return attachShortInfoDto;
    }
}
