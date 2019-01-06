package com.ex.smilegate_personal1.api;

import com.ex.smilegate_personal1.dto.ShortUrl;
import com.ex.smilegate_personal1.model.DefaultRes;
import com.ex.smilegate_personal1.service.UrlService;
import com.ex.smilegate_personal1.utils.ResponseMessage;
import com.ex.smilegate_personal1.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ex.smilegate_personal1.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("")
public class UrlController {

    private final UrlService urlService;

    UrlController(final UrlService urlService){
        this.urlService = urlService;
    }



//    @GetMapping("checking")
//    public boolean checkResponse(@RequestBody final ShortUrl shortUrl){
//        try{
//
//            log.info(shortUrl.getLink_url().getClass().getName());
//            URL url = new URL(shortUrl.getLink_url());
//
//
////            URL url = new URL("http://example.com");
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            int code = connection.getResponseCode();
//            log.info("Response code of the object is "+code);
//            if (code==200)
//            {
//                log.info("OK");
//                return true;
//            }
//            return false;
//
//        }catch(Exception e){
//            log.error(e.getMessage());
//            return false;
//        }
//    }

    @PostMapping("")
        public ResponseEntity saveLink (@RequestBody final ShortUrl originLink){
        try{
            log.info("postmapping : " + originLink.toString());
            return new ResponseEntity(urlService.saveLink(originLink), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

