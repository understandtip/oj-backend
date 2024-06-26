package com.jackqiu.oj.common;

import lombok.Data;
import java.io.Serializable;

/**
 * id请求体
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
