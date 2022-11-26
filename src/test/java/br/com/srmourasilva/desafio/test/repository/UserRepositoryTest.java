package br.com.srmourasilva.desafio.test.repository;


import br.com.srmourasilva.desafio.config.MongoContainerSetup;
import br.com.srmourasilva.desafio.model.User;
import br.com.srmourasilva.desafio.repository.UserRepository;
import br.com.srmourasilva.desafio.sample.SampleModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(initializers={MongoContainerSetup.class})
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void existsEmail() {
        final String email = "sample_mail@example.com";

        User user = SampleModel.sampleUser();
        user.setFullName("Test 1");
        user.setEmail(email);

        User anotheruser = SampleModel.sampleUser();
        anotheruser.setFullName("Test 2");
        anotheruser.setEmail(email);

        // given
        repository.save(user);

        // when
        User example = new User();
        example.setEmail(email);

        List<User> users = repository.findAll(Example.of(example));

        // then
        assertThat(users)
            .hasSize(1)
            .extracting(User::getEmail)
                .containsOnly(email);
    }
}