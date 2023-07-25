package org.atrokhau.vasili.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Human {

    private Long id;

    private String name;

    private Integer age;

    private LocalDate birthday;

}
