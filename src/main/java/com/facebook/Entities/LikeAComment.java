package com.facebook.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like_comment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "comment_id"})
})
@Builder
public class LikeAComment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    @ManyToOne(optional = false)
    private Comment comment;

    private Instant likedAt;
}
