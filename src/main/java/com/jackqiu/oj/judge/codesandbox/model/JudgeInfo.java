package com.jackqiu.oj.judge.codesandbox.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jackqiu
 */
@Data
public class JudgeInfo implements Serializable {
    private static final long serialVersionUID = 900488662021911770L;
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗时间  单位为ms
     */
    private Long time;

    /**
     * 消耗内存 单位为kb
     */
    private Long memory;
}
