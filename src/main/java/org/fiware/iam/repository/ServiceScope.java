package org.fiware.iam.repository;

import io.micronaut.core.annotation.Introspected;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.UUID;

@Introspected
@Accessors(chain = true)
@Data
@Entity
@ToString(exclude = "service")
@EqualsAndHashCode(exclude = "service")
public class ServiceScope {

    @Id
    private String id;

    private String scopeName;

    @OneToMany(mappedBy = "serviceScope", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Credential> credentials;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

}