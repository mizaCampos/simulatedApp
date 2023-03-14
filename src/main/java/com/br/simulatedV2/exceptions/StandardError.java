package com.br.simulatedV2.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StandardError {

    private Long timestamp;
    private Integer status;
    private String error;

}
