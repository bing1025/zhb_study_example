package com.example.zhb.study.demo.message.support;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class MessageEvent implements Serializable {

    private String eventId = getEventId();

    private MessageSource source;

    private volatile Integer maxRetryTimes = 1;

    private volatile Integer curRetryTimes = 1;

    public MessageEvent(MessageSource source){
        if (source == null){
            throw new IllegalArgumentException("null source");
        }
        this.source = source;
    }

    public synchronized void retryAgain(){
        this.curRetryTimes++;
    }

    public boolean isExceedMax(){
        return curRetryTimes >= maxRetryTimes;
    }

    public MessageEvent disableRetry(){
        this.maxRetryTimes = 1;
        return this;
    }

    private static String getEventId(){
        UUID uuid = UUID.randomUUID();
        String eventId = uuid.toString();
        return eventId.substring(0,8) + eventId.substring(9,13) + eventId.substring(19,23) + eventId.substring(24);
    }
}
