package br.com.symon.rentapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Entity
@Getter
@EqualsAndHashCode(of = "id")
public class ItemCategory {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull(message = "Name cannot be null.")
    @NotBlank(message = "Name cannot be empty.")
    @Size(min = 3, max = 30, message = "Name must have at least 3 and at most 30 characters.")
    @Column(name = "name")
    private String name;
}
