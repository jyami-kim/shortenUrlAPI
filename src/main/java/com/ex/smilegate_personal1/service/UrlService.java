package com.ex.smilegate_personal1.service;


import com.ex.smilegate_personal1.dto.ShortUrl;
import com.ex.smilegate_personal1.mapper.UrlMapper;
import com.ex.smilegate_personal1.model.DefaultRes;
import com.ex.smilegate_personal1.utils.ResponseMessage;
import com.ex.smilegate_personal1.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UrlService {

    private final UrlMapper urlMapper;

    private final Base62ConvertService base62ConvertService;

    UrlService( final UrlMapper urlMapper, final Base62ConvertService base62ConvertService){
        this.urlMapper = urlMapper;
        this.base62ConvertService = base62ConvertService;
    }

//    public DefaultRes saveLink(final String originUrl){
//
//        //여기에 response 확인하기!
//        log.warn(originUrl);
//        //base10 = db의 id값이다. (db의 메모리를 아끼기 위해 db의 id값을 직접 십만으로 설정하지 않고, 서비스 내에서 설정)
//        int base10 = urlMapper.save_link(originUrl);
//
//        log.warn("base10 : "+base10);
//        return(new DefaultRes(StatusCode.OK, ResponseMessage.CREAT_LINK, base62ConvertService.toBase62(base10)));
//    }

    public DefaultRes saveLink(final ShortUrl shortUrl){

        //여기에 response 확인하기!
        log.warn(shortUrl.toString());
        //base10 = db의 id값이다. (db의 메모리를 아끼기 위해 db의 id값을 직접 십만으로 설정하지 않고, 서비스 내에서 설정)
        int base10 = urlMapper.save_link(shortUrl);
        log.info(shortUrl.getIdshort()+"");

        log.warn("base10 : "+base10);
        return(new DefaultRes(StatusCode.OK, ResponseMessage.CREAT_LINK, base62ConvertService.toBase62(base10)));
    }

    public DefaultRes getPullLink(final String shortLink){

        //decoding 결과 저장
        long base10 = base62ConvertService.fromBase62(shortLink);

        return (new DefaultRes(StatusCode.OK, ResponseMessage.READ_LINK, urlMapper.get_pull_url(base10)));
    }

}
