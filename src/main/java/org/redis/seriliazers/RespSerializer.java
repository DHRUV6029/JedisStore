package org.redis.seriliazers;

import org.redis.utilities.Constants;

import javax.crypto.Cipher;

public  class RespSerializer {
    public String serialize(String str, boolean isBulk) {
        if (isBulk) {
            if (str == null) {
                return STR."\{Constants.BULK_STR}-1\{Constants.CRLF}";
            } else if (str.isEmpty()) {
                return STR."\{Constants.BULK_STR}0\{Constants.CRLF}";
            } else {
                return STR."\{Constants.BULK_STR}\{str.length()}\{Constants.CRLF}\{str}\{Constants.CRLF}";
            }
        } else {
            return (str.indexOf(Constants.CR) > 0 || str.indexOf(Constants.LF) > 0) ?
                    serialize(new RuntimeException("this is a bulk str, can't serialize as simple str")) :
                    STR."\{Constants.SIMPLE_STR}\{str}\{Constants.CRLF}";
        }
    }
    public String serialize(RuntimeException error) {
        return STR."\{Constants.SIMPLE_ERR}\{error.getMessage()}\{Constants.CRLF}";
    }
    public String serialize(int integer) {
        return STR."\{Constants.INTEGER}\{integer}\{Constants.CRLF}";
    }
    public String serialize(Object[] arr) {
        String des = STR."\{Constants.ARRAY}";
        if (arr == null) {
            des = STR."\{des}-1\{Constants.CRLF}";
        } else if (arr.length == 0) {
            des = STR."\{des}0\{Constants.CRLF}";
        } else {
            des = STR."\{des}\{arr.length}\{Constants.CRLF}";
            for (Object obj : arr) {
                if (obj instanceof String) {
                    des = STR."\{des}\{serialize((String) obj, true)}";
                } else if (obj instanceof Integer) {
                    des = STR."\{des}\{serialize((Integer) obj)}";
                }else if (obj == null){
                    des = STR."\{des}\{serialize(null , true)}";
                }
            }
        }
        return des;
    }


}
