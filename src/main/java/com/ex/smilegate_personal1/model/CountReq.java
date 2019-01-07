package com.ex.smilegate_personal1.model;

import com.ex.smilegate_personal1.dto.ShortUrl;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountReq {
    private ShortUrl shortUrl;
    private String shorten;
}
