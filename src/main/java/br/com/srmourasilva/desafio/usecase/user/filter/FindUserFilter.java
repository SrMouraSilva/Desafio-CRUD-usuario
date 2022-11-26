package br.com.srmourasilva.desafio.usecase.user.filter;

import br.com.srmourasilva.desafio.model.Profile;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FindUserFilter {

    @Schema(description="Exact user id", example="507f1f77bcf86cd799439011")
    private Collection<String> id = new LinkedList<>();

    @Schema(description="Ilike user name", example="rachel")
    private Collection<String> fullName = new LinkedList<>();

    @Schema(description="Exact email", example="rachel.queiroz@google.com")
    private Collection<String> email = new LinkedList<>();

    @Schema(description="Exact phone", example="+55 85 9 8765 4321")
    private Collection<String> phone = new LinkedList<>();

    @Schema(description="Exact profile", example="ADMIN")
    private Collection<Profile> profile = new LinkedList<>();

    public Collection<String> getId() {
        return id;
    }

    public void setId(Collection<String> id) {
        this.id = id;
    }

    public Collection<String> getFullName() {
        return fullName;
    }

    public void setFullName(Collection<String> fullName) {
        this.fullName = fullName;
    }

    public Collection<String> getEmail() {
        return email;
    }

    public void setEmail(Collection<String> email) {
        this.email = email;
    }

    public Collection<String> getPhone() {
        return phone;
    }

    public void setPhone(Collection<String> phone) {
        this.phone = phone;
    }

    public Collection<Profile> getProfile() {
        return profile;
    }

    public void setProfile(Collection<Profile> profile) {
        this.profile = profile;
    }

    public Query toQuery() {
        final Query query = new Query();

        final List<Criteria> criteria = new ArrayList<>();

        if (!id.isEmpty()) {
            criteria.add(Criteria.where("id").in(id));
        }
        if (!fullName.isEmpty()) {
            List<Criteria> regexFullNames = fullName.stream()
                .map(it -> Criteria.where("fullName").regex(it, "i"))
                .collect(Collectors.toList());

            criteria.add(new Criteria().orOperator(regexFullNames));
        }
        if (!email.isEmpty()) {
            criteria.add(Criteria.where("email").in(email));
        }
        if (!phone.isEmpty()) {
            criteria.add(Criteria.where("phone").in(phone));
        }
        if (!profile.isEmpty()) {
            criteria.add(Criteria.where("profile").in(profile));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria));
        }

        return query;
    }
}