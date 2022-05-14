package com.dhao.servicebase.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: dhao
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HaoException extends RuntimeException {

    private Integer code;

    private String msg;
}
