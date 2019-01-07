package com.ex.smilegate_personal1.service;


import com.ex.smilegate_personal1.dto.ShortUrl;
import com.ex.smilegate_personal1.mapper.UrlMapper;
import com.ex.smilegate_personal1.model.CountReq;
import com.ex.smilegate_personal1.model.DefaultRes;
import com.ex.smilegate_personal1.utils.ResponseMessage;
import com.ex.smilegate_personal1.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            int base10;
            //base10 = db의 id값이다. (db의 메모리를 아끼기 위해 db의 id값을 직접 십만으로 설정하지 않고, 서비스 내에서 설정)

            ShortUrl is_previous = urlMapper.get_previous_url(shortUrl.getLink_url());

            if(is_previous != null){ //기존
                base10 = is_previous.getIdshort();
                log.info(is_previous.toString());
                urlMapper.update_create_count(base10); //해당 shorten url을 생성할 때 마다 count++
            }else{ //새로
                urlMapper.save_link(shortUrl); //originUrl save
                base10 = shortUrl.getIdshort();
                log.info(shortUrl.toString());
            }
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



    public String getFullLink(final String shortLink){
        log.warn(shortLink);
        //decoding 결과 저장
        long base10 = base62ConvertService.fromBase62(shortLink);
        log.error("decoding 결과: " + base10);

        ShortUrl findingUrl = urlMapper.get_full_url(base10);

        if(findingUrl != null){
            urlMapper.update_redirect_count(base10); //해당 shorten url을 호출할 때 마다 count++
            log.error("original messgae : " + findingUrl.getLink_url());
            return findingUrl.getLink_url();
        }
        return null;
    }

    public DefaultRes count_create(){
        List<ShortUrl> shortUrlList = urlMapper.create_count();
        List<CountReq> countReqList = new ArrayList<>();
        for(ShortUrl shortUrl : shortUrlList){
            String shorten = "http://13.209.168.93:8081/" +base62ConvertService.toBase62(shortUrl.getIdshort());
            CountReq countReq = new CountReq(shortUrl, shorten);
            countReqList.add(countReq);
        }
        return (new DefaultRes(StatusCode.OK, ResponseMessage.READ_LINK, countReqList));
    }

    public DefaultRes count_redirect(){
        List<ShortUrl> shortUrlList = urlMapper.redirect_count();
        List<CountReq> countReqList = new ArrayList<>();
        for(ShortUrl shortUrl : shortUrlList){
            String shorten = "http://13.209.168.93:8081/" +base62ConvertService.toBase62(shortUrl.getIdshort());
            CountReq countReq = new CountReq(shortUrl, shorten);
            countReqList.add(countReq);
        }
        return (new DefaultRes(StatusCode.OK, ResponseMessage.READ_LINK, countReqList));
    }
}
