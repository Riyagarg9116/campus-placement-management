
package com.placement.service;

import com.placement.dto.CompanyRequest;
import com.placement.dto.CompanyResponse;
import com.placement.entity.Company;
import com.placement.repository.CompanyRepository;
import com.placement.repository.PlacementDriveRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PlacementDriveRepository placementDriveRepository;

    public CompanyService(
            CompanyRepository companyRepository,
            PlacementDriveRepository placementDriveRepository) {

        this.companyRepository = companyRepository;
        this.placementDriveRepository = placementDriveRepository;
    }

    // Add company
    public CompanyResponse addCompany(CompanyRequest request) {

        Company company = new Company();

        company.setCompanyName(request.getCompanyName());
        company.setIndustry(request.getIndustry());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setHrName(request.getHrName());
        company.setHrEmail(request.getHrEmail());
        company.setHrPhone(request.getHrPhone());

        Company savedCompany = companyRepository.save(company);

        return new CompanyResponse(savedCompany);
    }

    // Get all companies
    public List<CompanyResponse> getAllCompanies() {

        return companyRepository.findAll()
                .stream()
                .map(CompanyResponse::new)
                .toList();
    }

    // Get company by ID
    public CompanyResponse getCompanyById(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Company not found"));

        return new CompanyResponse(company);
    }

    // Search companies by name
    public List<CompanyResponse> searchCompanies(String name) {

        return companyRepository
                .findByCompanyNameContainingIgnoreCase(name)
                .stream()
                .map(CompanyResponse::new)
                .toList();
    }

    // Update company
    public CompanyResponse updateCompany(
            Long id, CompanyRequest request) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Company not found"));

        company.setCompanyName(request.getCompanyName());
        company.setIndustry(request.getIndustry());
        company.setWebsite(request.getWebsite());
        company.setLocation(request.getLocation());
        company.setHrName(request.getHrName());
        company.setHrEmail(request.getHrEmail());
        company.setHrPhone(request.getHrPhone());

        Company updatedCompany = companyRepository.save(company);

        return new CompanyResponse(updatedCompany);
    }

    // Delete company safely
    public void deleteCompany(Long id) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Company not found"));

        boolean hasPlacementDrives =
                !placementDriveRepository.findByCompanyId(id).isEmpty();

        if (hasPlacementDrives) {
            throw new RuntimeException(
                    "Cannot delete company because it has existing placement drives");
        }

        companyRepository.delete(company);
    }
}

