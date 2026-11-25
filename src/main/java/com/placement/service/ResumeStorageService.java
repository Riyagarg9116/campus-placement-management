
package com.placement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ResumeStorageService {

    private final Path storageLocation;

    public ResumeStorageService(
            @Value("${file.upload-dir}") String uploadDir) {

        this.storageLocation = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create resume storage directory", e);
        }
    }

    public String storeResume(MultipartFile file, Long studentId) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please select a resume");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "Resume must be 5 MB or less");
        }

        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String extension = originalName.substring(
                originalName.lastIndexOf(".") + 1)
                .toLowerCase();

        if (!extension.matches("pdf|doc|docx")) {
            throw new IllegalArgumentException(
                    "Only PDF, DOC and DOCX files are allowed");
        }

        String storedName = studentId + "_" +
                UUID.randomUUID() + "." + extension;

        Path destination = storageLocation.resolve(storedName)
                .normalize();

        if (!destination.startsWith(storageLocation)) {
            throw new IllegalArgumentException("Invalid file path");
        }

        try {
            Files.copy(file.getInputStream(), destination,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store resume", e);
        }

        return destination.toString();
    }

    public Path getResumePath(String resumeUrl) {

        Path path = Paths.get(resumeUrl)
                .toAbsolutePath()
                .normalize();

        if (!path.startsWith(storageLocation) ||
                !Files.isRegularFile(path)) {
            throw new IllegalArgumentException("Resume not found");
        }

        return path;
    }

    public void deleteResume(String resumeUrl) {

        if (resumeUrl == null || resumeUrl.isBlank()) {
            return;
        }

        try {
            Files.deleteIfExists(getResumePath(resumeUrl));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete resume", e);
        }
    }
}
