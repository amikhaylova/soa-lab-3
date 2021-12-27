package com.soa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class MovieDTO {
    private String id;
    private String name;
    private String creationDate;
    private CoordinatesDTO coordinates;
    private Long oscarsCount;
    private Long duration;
    private String genre;
    private String mpaaRating;
    private PersonDTO screenWriter;
}
