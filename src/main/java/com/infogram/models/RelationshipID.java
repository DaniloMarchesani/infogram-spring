package com.infogram.models;

import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@IdClass(RelationshipID.class)
public class RelationshipID {
    
    private User follower;
    private User followed;

    public RelationshipID(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }



}
