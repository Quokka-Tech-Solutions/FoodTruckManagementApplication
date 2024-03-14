package com.quokkatech.foodtruckmanagement.api.request;

import com.quokkatech.foodtruckmanagement.domain.entities.Address;
import com.quokkatech.foodtruckmanagement.domain.entities.User;

import java.util.Objects;

public class ProfileRequest {
    private Long profileId;

    private Address homeAddress;
    private Address companyAddress;

    private User user;

    public ProfileRequest(){}
    public ProfileRequest(Long profileId, Address homeAddress, Address companyAddress, User user) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileRequest that = (ProfileRequest) o;
        return Objects.equals(profileId, that.profileId) && Objects.equals(homeAddress, that.homeAddress) && Objects.equals(companyAddress, that.companyAddress) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, homeAddress, companyAddress, user);
    }
}
