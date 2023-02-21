package com.douyin.common.vo;

import com.douyin.common.constants.StateConstant;
import lombok.Data;

/**
 * CommentResponseVO
 *
 * @author chengyu
 */

@Data
public class CommentResponseVO extends BaseResponseVO{
    private CommentVO comment;


    public CommentResponseVO(Integer status_code, String status_msg) {
        super(status_code, status_msg);
    }

    public CommentResponseVO(Integer status_code, String status_msg, CommentVO comment) {
        super(status_code, status_msg);
        this.comment = comment;
    }

    public static CommentResponseVO success() {
        return new CommentResponseVO(StateConstant.SUCCESS_CODE, "评论删除成功");
    }

    public static CommentResponseVO success(CommentVO commentVO) {
        return new CommentResponseVO(StateConstant.SUCCESS_CODE, "评论成功", commentVO);
    }
}
