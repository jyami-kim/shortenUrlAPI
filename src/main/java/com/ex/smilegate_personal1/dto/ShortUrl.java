package com.ex.smilegate_personal1.dto;

import lombok.Data;

@Data
public class ShortUrl {
    private int idshort;
    private String link_url;
    private int create_count;
    private int redirect_count;
}
