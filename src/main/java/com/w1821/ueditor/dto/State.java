package com.w1821.ueditor.dto;

import lombok.Data;

/**
 * 判断成功失败基本对象
 * @author Administrator
 */
@Data
public class State {

    /**
     * 成功返回SUCCESS，是否返回其他字符串
     */
    private String state;

    public State(String state) {
        this.state = state;
    }
}
