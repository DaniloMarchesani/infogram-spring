package com.infogram.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Long id;
    
    @NotBlank
    private String userName;
    
    private String firstName;
    
    private String lastName;
    private LocalDateTime birthday;
    private String bio;
    private String avatarUrl;
    
    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToMany
    private List<Post> posts;

    @OneToMany(mappedBy = "following")
    private Set<Followers> following;

    @OneToMany(mappedBy = "follower") 
    private Set<Followers> followers;
}
