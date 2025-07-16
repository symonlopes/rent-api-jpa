package br.com.symon.rentapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
@Table(name = "tag")
public class Tag {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;
}
