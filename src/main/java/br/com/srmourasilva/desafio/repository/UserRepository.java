package br.com.srmourasilva.desafio.repository;

import br.com.srmourasilva.desafio.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, FindUserByQueryRepository {
    boolean existsByEmail(String email);
}
