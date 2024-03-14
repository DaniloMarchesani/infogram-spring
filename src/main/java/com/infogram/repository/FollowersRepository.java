package com.infogram.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infogram.models.Followers;
import com.infogram.models.Profile;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, Long>{
    
    public Optional<List<Followers>> findByFollowing(Profile profile);
}
