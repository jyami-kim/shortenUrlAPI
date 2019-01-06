package com.ex.smilegate_personal1.api;

import com.ex.smilegate_personal1.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class RedirectController
{

    private final UrlService urlService;

    public RedirectController(final UrlService urlService){
        this.urlService = urlService;
    }
//
//    @RequestMapping("/redirect")
//    public String redirect(@RequestParam("url") String url) {
//        return "redirect:" + url;
//    }

    @RequestMapping("/{shortLink}")
    public String getPullLink(@PathVariable(value = "shortLink") final String shortLink){
        log.warn("shortLink : "+ shortLink);
        String result = urlService.getPullLink(shortLink);
        log.warn(result);
        return "redirect:" + result;
        //이 link로 바로 redirection
    }

}
