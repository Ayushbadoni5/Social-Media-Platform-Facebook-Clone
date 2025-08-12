package com.facebook.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue
    private UUID id;

    @Column( nullable = false,columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdOn;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    private long likeCount;


    private long commentCount;

    @PrePersist
    public void prePersist() {
        this.createdOn = LocalDateTime.now();
    }
}
