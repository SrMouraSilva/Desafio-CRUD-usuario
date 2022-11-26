package br.com.srmourasilva.desafio.dto.user;

import br.com.srmourasilva.desafio.model.Address;
import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.validation.ErrorMessage;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResponseDTO {

    @Schema(description="Unique identifier")
    private String id;

    @Schema(description="Full name (given name AND family name)", example="Rachel de Queiroz")
    @NotNull(message= ErrorMessage.REQUIRED)
    @Size(min=3, message=ErrorMessage.MIN_SIZE)
    private String fullName;

    @Schema(description="User's email. Used as login/username", example="rachel.queiroz@gmail.com")
    @NotNull(message=ErrorMessage.REQUIRED)
    @Email(message=ErrorMessage.EMAIL)
    private String email;

    @Schema(description="Phone number. Preferable with Country code", example="+55 85 9 8765 4321")
    @Size(min=4, message=ErrorMessage.MIN_SIZE)
    private String phone;

    @Schema(description="Address where user lives")
    @NotNull(message=ErrorMessage.REQUIRED)
    private Address address;

    @Schema(description="User's profile, permission level for resource access", /*allowableValues={Profile.Constant.USER, Profile.Constant.ADMIN},*/ defaultValue= Profile.Constant.USER, example=Profile.Constant.ADMIN)
    @NotNull(message=ErrorMessage.REQUIRED)
    private Profile profile;

    public UserResponseDTO() {}

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = new Address(user.getAddress());
        this.profile = user.getProfile();
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
        if (!(o instanceof UserResponseDTO)) return false;
        UserResponseDTO that = (UserResponseDTO) o;
        return Objects.equal(getId(), that.getId()) && Objects.equal(getFullName(), that.getFullName()) && Objects.equal(getEmail(), that.getEmail()) && Objects.equal(getPhone(), that.getPhone()) && Objects.equal(getAddress(), that.getAddress()) && getProfile() == that.getProfile();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getFullName(), getEmail(), getPhone(), getAddress(), getProfile());
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", profile=" + profile +
                '}';
    }
}
