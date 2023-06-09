package cn.com.seabase.framework.jackson.core.datatype.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime序列化规则
 * 
 * 会将LocalDateTime序列化为毫秒级时间戳
 */
public class LocalDateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

    public static final LocalDateTimeJsonSerializer INSTANCE = new LocalDateTimeJsonSerializer();

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            return;
        }
        long timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        gen.writeNumber(timestamp);
    }
    
}
