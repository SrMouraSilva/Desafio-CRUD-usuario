package br.com.srmourasilva.desafio.repository;

import br.com.srmourasilva.desafio.dto.user.UserResponseDTO;
import br.com.srmourasilva.desafio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class FindUserByQueryRepositoryImpl implements FindUserByQueryRepository {

    @Autowired
    private MongoTemplate template;

    @Override
    public Page<UserResponseDTO> findAll(Query query, Pageable pageable) {
        Assert.notNull(query, "Query must not be null!");

        long total = template.count(query, User.class);
        List<UserResponseDTO> content = template.find(query.with(pageable), User.class)
            .stream().map(it -> new UserResponseDTO(it))
            .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, total);
    }
}