package com.douyin.common.dto;

import lombok.Data;


/**
 * VideoDTO
 *
 * @Author: jiahz
 * @Date: 2023/2/16 17:10
 * @Description:
 */
@Data
public class VideoDTO {
    private Long id;//

    private AuthorDTO author;

    private String playUrl;//

    private String coverUrl;//

    private Integer favoriteCount;//

    private Integer commentCount;//

    private Boolean isFavorite;

    private String title;//
}
