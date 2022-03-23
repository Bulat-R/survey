package com.example.survey.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(updatable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToMany(mappedBy = "survey", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Question> questions;

}
