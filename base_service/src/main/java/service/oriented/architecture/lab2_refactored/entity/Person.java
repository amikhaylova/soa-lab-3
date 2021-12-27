package service.oriented.architecture.lab2_refactored.entity;

import lombok.*;
import service.oriented.architecture.lab2_refactored.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@XmlRootElement
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@Table(name = "PERSON")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "name should not be null")
    private String name; //Поле не может быть null

    @NotNull(message = "weight should not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "weight must be > 0")
    private Float weight; //Поле не может быть null, Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    @NotNull(message = "eye color should not be null")
    @Column(name = "eyecolor")
    private Color eyeColor; //Поле не может быть null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @XmlTransient
    @NotNull(message = "location should not be null")
    private Location location; //Поле может быть null

}
