
package com.placement.controller;

import com.placement.dto.PlacementDriveRequest;
import com.placement.dto.PlacementDriveResponse;
import com.placement.service.PlacementDriveService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
@CrossOrigin(origins = "*")
public class PlacementDriveController {

    private final PlacementDriveService driveService;

    public PlacementDriveController(
            PlacementDriveService driveService) {
        this.driveService = driveService;
    }

    // ADMIN: Create a placement drive
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlacementDriveResponse> createDrive(
            @Valid @RequestBody PlacementDriveRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(driveService.createDrive(request));
    }

    // ADMIN and STUDENT: Get all placement drives
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<PlacementDriveResponse>> getAllDrives() {
        return ResponseEntity.ok(driveService.getAllDrives());
    }

    // ADMIN and STUDENT: Get drive by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<PlacementDriveResponse> getDriveById(
            @PathVariable Long id) {

        return ResponseEntity.ok(driveService.getDriveById(id));
    }

    // ADMIN and STUDENT: Get drives by company
    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<PlacementDriveResponse>> getDrivesByCompany(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                driveService.getDrivesByCompany(companyId));
    }

    // ADMIN and STUDENT: Search drives by job role
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    public ResponseEntity<List<PlacementDriveResponse>> searchByJobRole(
            @RequestParam String jobRole) {

        return ResponseEntity.ok(
                driveService.searchByJobRole(jobRole));
    }

    // ADMIN: Update drive status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlacementDriveResponse> updateDriveStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(
                driveService.updateDriveStatus(id, status));
    }

    // ADMIN: Delete a placement drive
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDrive(
            @PathVariable Long id) {

        driveService.deleteDrive(id);

        return ResponseEntity.ok(
                "Placement drive deleted successfully");
    }
}


