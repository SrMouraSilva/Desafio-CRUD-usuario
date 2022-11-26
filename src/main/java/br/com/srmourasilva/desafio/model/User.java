package br.com.srmourasilva.desafio.model;

import br.com.srmourasilva.desafio.validation.ErrorMessage;
import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    public static class Constraints {
        public static final int PASSWORD_MIN_SIZE = 6;
    }

    @Schema(hidden=true)
    private String id;

    @Schema(description="Full name (given name AND family name)", example="Rachel de Queiroz")
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=3, message=ErrorMessage.MIN_SIZE)
    private String fullName;

    /**
     * User email. Used as login/username
     */
    @Schema(description="User's email", example="rachel.queiroz@gmail.com")
    @NotNull(message=ErrorMessage.REQUIRED)
    @Email(message=ErrorMessage.EMAIL)
    private String email;

    /**
     * Password encoded as `argon2id`
     */
    @Schema(description="User's password", example="S3cretP@ssword", pattern=PasswordRegex.PASSWORD)
    @NotNull(message=ErrorMessage.REQUIRED)
    @Size(min=Constraints.PASSWORD_MIN_SIZE, message=ErrorMessage.MIN_SIZE)
    private String password;

    @Schema(description="Phone number. Preferable with Country code", example="+55 85 9 8765 4321")
    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String phone;

    @Schema(description="Address where user lives")
    @NotNull(message=ErrorMessage.REQUIRED)
    private Address address;

    @Schema(description="User's profile, permission level for resource access", /*allowableValues={Profile.Constant.USER, Profile.Constant.ADMIN},*/ defaultValue=Profile.Constant.USER, example=Profile.Constant.ADMIN)
    @NotNull(message=ErrorMessage.REQUIRED)
    private Profile profile = Profile.USER;

    public User() {}

    public User(User user) {
        this();

        this.setId(user.id);
        this.setFullName(user.fullName);
        this.setEmail(user.email);
        this.setPassword(user.password);
        this.setPhone(user.phone);
        this.setAddress(new Address(user.address));
        this.setProfile(user.profile);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(getId(), user.getId()) && Objects.equal(getFullName(), user.getFullName()) && Objects.equal(getEmail(), user.getEmail()) && Objects.equal(getPassword(), user.getPassword()) && Objects.equal(getPhone(), user.getPhone()) && Objects.equal(getAddress(), user.getAddress()) && getProfile() == user.getProfile();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getFullName(), getEmail(), getPassword(), getPhone(), getAddress(), getProfile());
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", profile=" + profile +
                '}';
    }
}
