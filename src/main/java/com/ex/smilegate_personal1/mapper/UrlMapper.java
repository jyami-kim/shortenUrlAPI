package com.ex.smilegate_personal1.mapper;

import com.ex.smilegate_personal1.dto.ShortUrl;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UrlMapper {

    @Select("SELECT * FROM short ORDER BY create_count DESC LIMIT 5;")
    List<ShortUrl> create_count();

    @Select("SELECT * FROM short ORDER BY redirect_count DESC LIMIT 5;")
    List<ShortUrl> redirect_count();

    @Select("SELECT * FROM short WHERE idshort = #{idshort};")
    ShortUrl get_full_url(@Param("idshort") final long idshort);

    @Update("UPDATE short SET create_count=create_count+1 WHERE idshort = #{idshort};")
    void update_create_count(@Param("idshort") final long idshort);

    @Update("UPDATE short SET redirect_count=redirect_count+1 WHERE idshort = #{idshort};")
    void update_redirect_count(@Param("idshort") final long idshort);

    @Insert("INSERT INTO short(link_url, create_count, redirect_count) VALUE(#{shortUrl.link_url}, 1, 0);")
    @Options(useGeneratedKeys=true, keyProperty = "shortUrl.idshort")
    int save_link(@Param("shortUrl") final ShortUrl shortUrl);

    @Select("SELECT * FROM shortURL.short WHERE link_url = #{link}")
    ShortUrl get_previous_url(@Param("link")final String link);
}
