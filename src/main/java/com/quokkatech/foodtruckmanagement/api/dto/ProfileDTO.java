package com.quokkatech.foodtruckmanagement.api.dto;

import com.quokkatech.foodtruckmanagement.domain.entities.Address;
import com.quokkatech.foodtruckmanagement.domain.entities.Profile;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

public class ProfileDTO {
    private Long profileId;

    private Address homeAddress;
    private Address companyAddress;

    private User user;

    public ProfileDTO(){}
    public ProfileDTO(Long profileId, Address homeAddress, Address companyAddress, User user) {
        this.profileId = profileId;
        this.homeAddress = homeAddress;
        this.companyAddress = companyAddress;
        this.user=user;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Address companyAddress) {
        this.companyAddress = companyAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
