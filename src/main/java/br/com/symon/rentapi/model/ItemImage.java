package br.com.symon.rentapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@Table(name = "image")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ItemImage {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "url", nullable = false)
    private String url;

//    @Column(name = "item_id", nullable = false)
//    private UUID itemId;
}
