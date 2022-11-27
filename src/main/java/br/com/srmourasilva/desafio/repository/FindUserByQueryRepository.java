package br.com.srmourasilva.desafio.repository;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;


public interface FindUserByQueryRepository {
    Page<UserResponseDTO> findAll(Query query, Pageable pageable);
}
