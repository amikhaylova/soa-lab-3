package service.oriented.architecture.lab2_refactored.dto.dtoList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.dto.MovieDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class MovieDTOList {
    @XmlElementWrapper(name = "movies")
    @XmlElement(name = "movie")
    private List<MovieDTO> movieList;
    private long count;
}
