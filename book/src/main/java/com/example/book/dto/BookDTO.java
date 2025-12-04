package com.example.book.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    @NotBlank(message = "Do not accept blank")
    @Pattern(regexp = "^B(\\d{5})", message = "Please keep the format")
    private String isbn;
    @NotBlank(message = "Do not accept blank")
    private String title;
    @NotNull(message = "Do not accept blank")
    @Range(min = 0, max = 1000000)
    private long price;
    @NotBlank(message = "Do not accept blank")
    private String author;

    private String description;

}
