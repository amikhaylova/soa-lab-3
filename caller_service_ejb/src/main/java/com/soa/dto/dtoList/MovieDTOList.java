package com.soa.dto.dtoList;

import com.soa.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
