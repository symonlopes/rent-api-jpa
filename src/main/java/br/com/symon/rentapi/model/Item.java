package br.com.symon.rentapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder(toBuilder = true)
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be ampty.")
    @Size(min = 5, max = 100, message = "Name must have at least 5 and at most 100 characters.")
    private String name;

    @Column(name = "details")
    private String details;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    @NotEmpty(message = "You must have at least one image for the item.")
    @Builder.Default
    private Set<ItemImage> images =  new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category cannot be null.")
    private ItemCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tag_item",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags =  new HashSet<>();
}
