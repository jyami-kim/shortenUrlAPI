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

@Slf4j
@RestController
@RequestMapping("")
public class UrlController {

    private final UrlService urlService;

    UrlController(final UrlService urlService){
        this.urlService = urlService;
    }

    @PostMapping("")
        public ResponseEntity saveLink (@RequestBody final ShortUrl originLink){
        try{
            log.info("postmapping : " + originLink.toString());
            return new ResponseEntity(urlService.saveLink(originLink), HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.OK);
        }
    }
}

