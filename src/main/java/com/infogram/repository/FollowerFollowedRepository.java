package com.infogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infogram.models.FollowerFollowed;
import com.infogram.models.RelationshipID;
import com.infogram.models.User;

import java.util.List;


public interface FollowerFollowedRepository extends JpaRepository<FollowerFollowed, RelationshipID>{
    List<User> findByFollowed(User followed);

    //TODO: Add more methods to the repository
    //TODO: aggiungere metodo che utente segue un altro utente
    
}
