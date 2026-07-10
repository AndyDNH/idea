package com.programacion.distribuida.authors.dtos;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

        private Integer id;
        private String name;
}
