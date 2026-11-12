

package com.placement.service;

import com.placement.dto.PlacementDriveRequest;
import com.placement.dto.PlacementDriveResponse;

import com.placement.entity.Company;
import com.placement.entity.PlacementDrive;

import com.placement.repository.CompanyRepository;
import com.placement.repository.PlacementDriveRepository;
import com.placement.repository.ApplicationRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlacementDriveService {

    private final PlacementDriveRepository driveRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;

    public PlacementDriveService(
            PlacementDriveRepository driveRepository,
            CompanyRepository companyRepository,
            ApplicationRepository applicationRepository) {

        this.driveRepository = driveRepository;
        this.companyRepository = companyRepository;
        this.applicationRepository = applicationRepository;
    }

    // Create a placement drive
    public PlacementDriveResponse createDrive(
            PlacementDriveRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        PlacementDrive drive = new PlacementDrive();

        drive.setCompany(company);
        drive.setJobRole(request.getJobRole());
        drive.setJobDescription(request.getJobDescription());
        drive.setPackageAmount(request.getPackageAmount());
        drive.setJobLocation(request.getJobLocation());
        drive.setApplicationDeadline(request.getApplicationDeadline());
        drive.setDriveDate(request.getDriveDate());

        if (request.getStatus() == null ||
                request.getStatus().isBlank()) {
            drive.setStatus("OPEN");
        } else {
            drive.setStatus(request.getStatus().toUpperCase());
        }

        PlacementDrive savedDrive = driveRepository.save(drive);

        return new PlacementDriveResponse(savedDrive);
    }

    // Get all placement drives
    public List<PlacementDriveResponse> getAllDrives() {

        return driveRepository.findAll()
                .stream()
                .map(PlacementDriveResponse::new)
                .toList();
    }

    // Get placement drive by ID
    public PlacementDriveResponse getDriveById(Long id) {

        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        return new PlacementDriveResponse(drive);
    }

    // Get drives by company
    public List<PlacementDriveResponse> getDrivesByCompany(
            Long companyId) {

        return driveRepository.findByCompanyId(companyId)
                .stream()
                .map(PlacementDriveResponse::new)
                .toList();
    }

    // Search drives by job role
    public List<PlacementDriveResponse> searchByJobRole(
            String jobRole) {

        return driveRepository.findByJobRoleContainingIgnoreCase(jobRole)
                .stream()
                .map(PlacementDriveResponse::new)
                .toList();
    }

    // Update placement drive status
    public PlacementDriveResponse updateDriveStatus(
            Long id, String status) {

        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        drive.setStatus(status.toUpperCase());

        PlacementDrive updatedDrive = driveRepository.save(drive);

        return new PlacementDriveResponse(updatedDrive);
    }

    // Delete placement drive safely
    public void deleteDrive(Long id) {

        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Placement drive not found"));

        boolean hasApplications =
                applicationRepository.existsByPlacementDriveId(id);

        if (hasApplications) {
            throw new RuntimeException(
                    "Cannot delete placement drive because students have applied");
        }

        driveRepository.delete(drive);
    }
}
