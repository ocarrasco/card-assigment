package com.example.repositories;

import com.example.entities.Card;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Integer>, JpaSpecificationExecutor<Card> {

    @Query("from Card c where c.id = ?1 and c.owner.id = ?2")
    Optional<Card> findByIdAndOwnerId(Integer cardId, Integer ownerId);

}
