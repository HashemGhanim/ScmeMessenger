package com.scme.messenger.model;

import com.scme.messenger.encryption.util.PublicKey;
import com.scme.messenger.model.converters.BigIntegerStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KeyPair extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(400)")
    @Convert(converter = BigIntegerStringConverter.class)
    private BigInteger publicKey;

    @Column(columnDefinition = "VARCHAR(400)")
    @Convert(converter = BigIntegerStringConverter.class)
    private BigInteger privateKey;

    @Column(columnDefinition = "VARCHAR(400)")
    @Convert(converter = BigIntegerStringConverter.class)
    private BigInteger mod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
