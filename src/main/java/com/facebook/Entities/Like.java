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
@Table(name = "likes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Like {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime reactedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
