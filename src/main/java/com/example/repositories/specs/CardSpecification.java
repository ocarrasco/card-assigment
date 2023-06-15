package com.example.repositories.specs;

import com.example.dto.CardSearchCriteriaDTO;
import com.example.entities.Card;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class CardSpecification implements Specification<Card> {

    private final CardSearchCriteriaDTO cardSearchCriteriaDTO;

    @Override
    public Predicate toPredicate(Root<Card> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new LinkedList<>();
        if (cardSearchCriteriaDTO.hasFilters()) {

            if (cardSearchCriteriaDTO.hasName()) {
                predicates.add(builder.equal(root.get("name"), cardSearchCriteriaDTO.getName()));
            }

            if (cardSearchCriteriaDTO.hasColor()) {
                predicates.add(builder.equal(root.get("color"), cardSearchCriteriaDTO.getColor()));
            }

            if (cardSearchCriteriaDTO.hasStatus()) {
                predicates.add(builder.equal(root.get("status"), cardSearchCriteriaDTO.getStatus()));
            }

            if (cardSearchCriteriaDTO.hasDateOfCreation()) {
                predicates.add(builder.equal(root.get("createdAt"), cardSearchCriteriaDTO.convertDateToLocalDateTime()));
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }

}
