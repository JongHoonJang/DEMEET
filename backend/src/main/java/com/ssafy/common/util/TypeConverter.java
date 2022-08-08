package com.ssafy.common.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Repository
public class TypeConverter {

    public LocalDateTime LongToLocalDateTime(Long milliseconds) {
        log.info("LongToLocalDateTime 시작");
        log.debug("milliseconds: " + milliseconds);
//        LocalDateTime date =LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
        LocalDateTime date = Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
        log.debug("date: " + date.toString());
        log.info("LongToLocalDateTime 종료");
        return date;
    }
}
