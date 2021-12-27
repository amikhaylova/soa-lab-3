package service.oriented.architecture.lab2_refactored.dto.dtoList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.oriented.architecture.lab2_refactored.dto.LocationDTO;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class LocationDTOList {
    @XmlElementWrapper(name = "locations")
    @XmlElement(name = "location")
    private List<LocationDTO> locationsList;
}
