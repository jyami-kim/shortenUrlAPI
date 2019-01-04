package com.ex.smilegate_personal1.mapper;

import com.ex.smilegate_personal1.dto.ShortUrl;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UrlMapper {

    @Select("SELECT link_url FROM shortURL.short WHERE idshort = #{idshort};")
    String get_pull_url(@Param("idshort") final long idshort);


//    @Insert("INSERT INTO short(link_url) VALUE(#{originUrl});")
//    @Options(useGeneratedKeys=true, keyColumn="idshort")
//    int save_link(@Param("originUrl") final String originUrl);


    @Insert("INSERT INTO short(link_url) VALUE(#{shortUrl.link_url});")
    @Options(useGeneratedKeys=true, keyColumn = "shortUrl.idshort")
    int save_link(@Param("shortUrl") final ShortUrl shortUrl);

}
