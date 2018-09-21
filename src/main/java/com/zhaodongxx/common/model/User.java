package com.zhaodongxx.common.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/21 18:28
 */
@Data
@Slf4j
@Accessors(chain = true)
public class User {
    private String username;
    private String avatar;
}
