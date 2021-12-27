package service.oriented.architecture.lab2_refactored.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double x;

    @NotNull(message = "y should not be null")
    private Integer y; //Поле не может быть null

    @NotNull(message = "name should not be null")
    private String name; //Поле может быть null
}
