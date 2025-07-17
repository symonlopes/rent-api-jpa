package br.com.symon.rentapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@Table(name = "tag")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
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
