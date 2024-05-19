package com.ire.backend.database;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public final class OrganizationInformation {

    public String id;
    public String companyName;
    public String companyDescription;
    public String telephoneNumber;
    public String websiteUrl;
    public String facebookUrl;
    public String instagramUrl;
    public String youtubeUrl;
    public Long memberSince;
    public Long lastUpdated;


    public OrganizationInformation(
            final String id,
            final String companyName,
            final String companyDescription,
            final String telephoneNumber,
            final String websiteUrl,
            final String facebookUrl,
            final String instagramUrl,
            final String youtubeUrl,
            final Long memberSince,
            final Long lastUpdated
    ) {
        this.id = id;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.telephoneNumber = telephoneNumber;
        this.websiteUrl = websiteUrl;
        this.facebookUrl = facebookUrl;
        this.instagramUrl = instagramUrl;
        this.youtubeUrl = youtubeUrl;
        this.memberSince = memberSince;
        this.lastUpdated = lastUpdated;
    }

    public OrganizationInformation() {

    }

    public String id() {
        return id;
    }

    public String companyName() {
        return companyName;
    }

    public String companyDescription() {
        return companyDescription;
    }

    public String telephoneNumber() {
        return telephoneNumber;
    }

    public String websiteUrl() {
        return websiteUrl;
    }

    public String facebookUrl() {
        return facebookUrl;
    }

    public String instagramUrl() {
        return instagramUrl;
    }

    public String youtubeUrl() {
        return youtubeUrl;
    }

    public Long memberSince() {
        return memberSince;
    }

    public Long lastUpdated() {
        return lastUpdated;
    }

    @Override
    public String toString() {
        return "OrganizationInformation{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", facebookUrl='" + facebookUrl + '\'' +
                ", instagramUrl='" + instagramUrl + '\'' +
                ", youtubeUrl='" + youtubeUrl + '\'' +
                ", memberSince=" + memberSince +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
