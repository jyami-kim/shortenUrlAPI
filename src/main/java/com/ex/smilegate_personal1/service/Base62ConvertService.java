package com.ex.smilegate_personal1.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Base62ConvertService {
    final static String[] elements = {
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
            "p","q","r","s","t","u","v","w","x","y","z","1","2","3","4",
            "5","6","7","8","9","0","A","B","C","D","E","F","G","H","I",
            "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
            "Y","Z"
    };

    private static final int default_plus = 3000000;

    private static final int base = 62;

    //base10은 db의 id값, base62는 db의 주어진 id값을 62진수로 변환한 것

    String toBase62(long base10){ //encoding

        StringBuilder base62 = new StringBuilder();
        base10 += default_plus;
        int mod;
        do{
            mod = (int) base10 % base;
            base62.append(elements[mod]);
        }while((base10 = base10/ base) != 0);

        return base62.reverse().toString();
    }

    long fromBase62(String base62){ //decoding
        long mul = 1;
        long base10 =0;
        int pos;

        for(int i=base62.length()-1; i>=0; i--){
            pos = Arrays.asList(elements).indexOf(base62.substring(i, i+1));
            base10 += (long) pos*mul;
            mul *= base;
        }
        base10 -= default_plus;
        return base10;
    }
}
