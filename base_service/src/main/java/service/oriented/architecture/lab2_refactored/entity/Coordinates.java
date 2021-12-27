package service.oriented.architecture.lab2_refactored.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(of = {"x", "y"})
@Table(name = "coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "x should not be null")
    @DecimalMin(value = "-875.0", inclusive = false, message = "x must be > -875")
    private Double x; //Значение поля должно быть больше -875, Поле не может быть null

    private Float y;
}
