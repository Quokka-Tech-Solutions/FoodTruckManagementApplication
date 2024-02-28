package com.quokkatech.foodtruckmanagement.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Profile {
    @Id
    private long profileId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "home_address_id")
    private Address homeAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_address_id")
    private Address companyAddress;


    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Profile(){}

    public Profile(long profileId, Address homeAddress, Address companyAddress, User user) {
        this.profileId = profileId;
        this.homeAddress = homeAddress;
        this.companyAddress = companyAddress;
        this.user = user;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
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
        Profile profile = (Profile) o;
        return profileId == profile.profileId && Objects.equals(homeAddress, profile.homeAddress) && Objects.equals(companyAddress, profile.companyAddress) && Objects.equals(user, profile.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, homeAddress, companyAddress, user);
    }
}
