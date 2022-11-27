package br.com.srmourasilva.desafio.dto.user;

import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.validation.regex.PasswordRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import org.openapitools.jackson.nullable.JsonNullable;

public class UserUpdateRequestDTO {

    @Schema(description="Full name (given name AND family name)", example="Rachel de Queiroz")
    private JsonNullable<String> fullName = JsonNullable.undefined();

    @Schema(description="User's email. Used as login/username", type="String", example="rachel.queiroz@gmail.com")
    private JsonNullable<String> email = JsonNullable.undefined();

    @Schema(description="User's password", example="S3cretP@ssword", type="String", pattern= PasswordRegex.PASSWORD)
    private JsonNullable<String> password = JsonNullable.undefined();

    @Schema(description="Phone number. Preferable with Country code", type="String", example="+55 85 9 8765 4321")
    private JsonNullable<String> phone = JsonNullable.undefined();

    @Schema(description="Address where user lives", type="Address")
    private JsonNullable<AddressUpdateRequestDTO> address = JsonNullable.undefined();

    @Schema(description="User's profile, permission level for resource access", type="String", example=Profile.Constant.ADMIN, allowableValues={Profile.Constant.USER, Profile.Constant.ADMIN})
    private JsonNullable<Profile> profile = JsonNullable.undefined();

    public User join(User user) {
        User newUser = new User(user);

        if (fullName.isPresent()) {
            newUser.setFullName(fullName.get());
        }
        if (email.isPresent()) {
            newUser.setEmail(email.get());
        }
        if (password.isPresent()) {
            newUser.setPassword(password.get());
        }
        if (phone.isPresent()) {
            newUser.setPhone(phone.get());
        }
        if (address.isPresent()) {
            newUser.setAddress(address.get().join(user.getAddress()));
        }
        if (profile.isPresent()) {
            newUser.setProfile(profile.get());
        }

        return newUser;
    }

    public JsonNullable<String> getFullName() {
        return fullName;
    }

    public void setFullName(JsonNullable<String> fullName) {
        this.fullName = fullName;
    }

    public JsonNullable<String> getEmail() {
        return email;
    }

    public void setEmail(JsonNullable<String> email) {
        this.email = email;
    }

    public JsonNullable<String> getPassword() {
        return password;
    }

    public void setPassword(JsonNullable<String> password) {
        this.password = password;
    }

    public JsonNullable<String> getPhone() {
        return phone;
    }

    public void setPhone(JsonNullable<String> phone) {
        this.phone = phone;
    }

    public JsonNullable<AddressUpdateRequestDTO> getAddress() {
        return address;
    }

    public void setAddress(JsonNullable<AddressUpdateRequestDTO> address) {
        this.address = address;
    }

    public JsonNullable<Profile> getProfile() {
        return profile;
    }

    public void setProfile(JsonNullable<Profile> profile) {
        this.profile = profile;
    }
}
