package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.validation.ErrorMessage;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private String id;

    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=3, message=ErrorMessage.MIN_SIZE)
    private String fullName;

    /**
     * User email. Used as login/username
     */
    @NotNull(message=ErrorMessage.REQUIRED)
    @Email(message=ErrorMessage.EMAIL)
    private String email;

    /**
     * Password encoded as `argon2id`
     */
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=6, message=ErrorMessage.MIN_SIZE)
    private String password;

    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String phone;

    @NotNull(message=ErrorMessage.REQUIRED)
    private Address address;

    @NotNull(message=ErrorMessage.REQUIRED)
    private Profile profile = Profile.USER;

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
