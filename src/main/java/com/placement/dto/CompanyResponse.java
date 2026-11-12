
package com.placement.dto;

import com.placement.entity.Company;

public class CompanyResponse {

    private Long id;
    private String companyName;
    private String industry;
    private String website;
    private String location;
    private String hrName;
    private String hrEmail;
    private String hrPhone;

    public CompanyResponse(Company company) {
        this.id = company.getId();
        this.companyName = company.getCompanyName();
        this.industry = company.getIndustry();
        this.website = company.getWebsite();
        this.location = company.getLocation();
        this.hrName = company.getHrName();
        this.hrEmail = company.getHrEmail();
        this.hrPhone = company.getHrPhone();
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation() {
        return location;
    }

    public String getHrName() {
        return hrName;
    }

    public String getHrEmail() {
        return hrEmail;
    }

    public String getHrPhone() {
        return hrPhone;
    }
}

