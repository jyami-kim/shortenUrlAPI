package com.ex.smilegate_personal1.service;


import com.ex.smilegate_personal1.dto.ShortUrl;
import com.ex.smilegate_personal1.mapper.UrlMapper;
import com.ex.smilegate_personal1.model.DefaultRes;
import com.ex.smilegate_personal1.utils.ResponseMessage;
import com.ex.smilegate_personal1.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class UrlService {

    private final UrlMapper urlMapper;

    private final Base62ConvertService base62ConvertService;

    UrlService( final UrlMapper urlMapper, final Base62ConvertService base62ConvertService){
        this.urlMapper = urlMapper;
        this.base62ConvertService = base62ConvertService;
    }


    public DefaultRes saveLink(final ShortUrl shortUrl){

        //여기에 response 확인하기!
        if(checkResponse(shortUrl.getLink_url())){
            //base10 = db의 id값이다. (db의 메모리를 아끼기 위해 db의 id값을 직접 십만으로 설정하지 않고, 서비스 내에서 설정)
            urlMapper.save_link(shortUrl);
            int base10 = shortUrl.getIdshort();
            log.info(shortUrl.toString());
            return(new DefaultRes(StatusCode.OK, ResponseMessage.CREAT_LINK, base62ConvertService.toBase62(base10)));
        }
        return(new DefaultRes(StatusCode.BAD_REQUEST, ResponseMessage.HAVE_NOT_RESPONSE, "not have response"));

    }


    public boolean checkResponse(final String inputurl){
        try{
            URL url = new URL(inputurl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            log.info("Response code of the object is "+code);
            if (code==200)
            {
                log.info("OK");
                return true;
            }
            return false;

        }catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public String getPullLink(final String shortLink){
        log.warn(shortLink);
        //decoding 결과 저장
        long base10 = base62ConvertService.fromBase62(shortLink);
        log.error("decoding 결과: " + base10);
        String result = urlMapper.get_pull_url(base10);
        log.error("original messgae"+result);
        return result;
    }

}
