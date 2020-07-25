package com.example.blog.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 */
@GroupSequence({com.example.blog.common.validator.group.AddGroup.class, com.example.blog.common.validator.group.UpdateGroup.class})
public interface Group {

}
