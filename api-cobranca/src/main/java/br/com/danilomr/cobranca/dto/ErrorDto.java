package br.com.danilomr.cobranca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    private int status;
    private String path;
    private LocalDateTime timestamp;
    private String error;
    private String method;
}
