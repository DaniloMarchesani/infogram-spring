package com.infogram.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(RelationshipID.class)
public class FollowerFollowed {
    
    private LocalDateTime createdAt;

    @Id
    @ManyToOne
    private User follower;

    @Id
    @ManyToOne
    private User followed;



    /*@ManyToOne
    @Id
    private User follower;

    @ManyToOne
    @Id
    private User followed;*/

  /*   //@OneToMany
    @Id
    private List<User> follower = new ArrayList<>();

    //@OneToMany
    @Id
    private List<User> followed = new ArrayList<>(); */
}
