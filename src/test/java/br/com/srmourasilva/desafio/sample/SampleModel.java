package br.com.srmourasilva.desafio.sample;

import br.com.srmourasilva.desafio.model.Address;
import br.com.srmourasilva.desafio.model.Profile;
import br.com.srmourasilva.desafio.model.Street;
import br.com.srmourasilva.desafio.model.User;

public class SampleModel {

    public static User sampleAuthUser(Profile profile) {
        User user = SampleModel.sampleUser();

        user.setFullName(profile.name());
        user.setEmail(profile+"@test.com");
        user.setProfile(profile);

        return user;
    }

    public static User sampleUser() {
        final User user = new User();
        user.setFullName("Rachel de Queiroz");
        user.setEmail("rachel.queiroz@sample.com");
        user.setPassword("the_strongest_password_1aA!");
        user.setPhone("+55 85 9 8765-4321");
        user.setProfile(Profile.USER);
        user.setAddress(sampleAddress());

        return user;
    }

    public static Address sampleAddress() {
        final Address address = new Address();
        address.setCountry("Brazil");
        address.setState("Ceará");
        address.setStreet(sampleStreet());

        return address;
    }


    public static Street sampleStreet() {
        final Street street = new Street();

        street.setZipCode("60521-025");
        street.setName("Rua Antônio Ivo");
        street.setNumber(290);
        street.setComplement(null);

        return street;
    }
}
