package br.com.symon.rentapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Table(name = "tag")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Tag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
