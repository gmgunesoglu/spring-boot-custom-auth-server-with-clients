package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "method")
@Immutable
public class Method {

    @Id
    @SequenceGenerator(
            name = "method_seq",
            sequenceName = "method_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "method_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            length = 7
    )
    @Enumerated(EnumType.STRING)
    private MethodType name;

}
