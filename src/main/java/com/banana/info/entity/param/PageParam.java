package com.banana.info.entity.param;

import lombok.Data;

@Data
public class PageParam {

    /**
     * 当前页
     */
    private Long pageNo;

    /**
     * 页大小
     */
    private Long pageSize;
}
