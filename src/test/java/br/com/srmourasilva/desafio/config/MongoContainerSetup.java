package br.com.srmourasilva.desafio.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
public class MongoContainerSetup implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:6.0.3"))
        .withReuse(true);

    static {
        container.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues values = TestPropertyValues.of(
            "spring.data.mongodb.uri=" + container.getReplicaSetUrl()
        );
        values.applyTo(applicationContext);

        container.start();
    }
}
