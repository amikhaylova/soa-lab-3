package service.oriented.architecture.lab2_refactored.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.enums.MovieGenre;
import service.oriented.architecture.lab2_refactored.enums.MpaaRating;
import service.oriented.architecture.lab2_refactored.xml.LocalDateAdapter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Entity
@Table(name = "MOVIE")
@NamedQuery(name = "Entity.Movie.getAll", query = "SELECT m FROM Movie m")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "name should not be null")
    private String name;//Поле не может быть null

    @Column(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    @NotNull(message = "creation date should not be null")
    private LocalDate creationDate;//Значение этого поля должно генерироваться автоматически

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinate_id")
    @XmlElement(name = "coordinates")
    private Coordinates coordinates;//Поле не может быть null

    @Column(name = "oscars")
    @DecimalMin(value = "0", inclusive = true, message = "oscars count must be >= 0")
    private Long oscarsCount; //Значение поля должно быть больше 0, Поле может быть null

    @DecimalMin(value = "0", inclusive = false, message = "duration must be > 0")
    private Long duration;//Значение поля должно быть больше 0

    @Enumerated(EnumType.STRING)
    private MovieGenre genre; //Поле может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    @NotNull(message = "rating should not be null")
    private MpaaRating mpaaRating;//Поле не может быть null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    @XmlElement(name = "screenWriter")
    private Person screenWriter;//Поле не может быть null
}
