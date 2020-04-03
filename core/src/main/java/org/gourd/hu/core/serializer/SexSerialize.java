package org.gourd.hu.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.gourd.hu.core.annotation.SexShow;
import org.gourd.hu.core.enums.FieldShowEnum;
import org.gourd.hu.core.enums.SexEnum;

import java.io.IOException;
import java.util.Objects;


/**
 * 性别序列化类
 *
 * @author gourd
 */
public class SexSerialize extends JsonSerializer<SexEnum> implements ContextualSerializer {

  private FieldShowEnum type;

  public SexSerialize() {
  }

  public SexSerialize(final FieldShowEnum type) {
    this.type = type;
  }


  @Override
  public void serialize(final SexEnum sexEnum, final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

    if(FieldShowEnum.LABEL.equals(type)){
      jsonGenerator.writeObject(sexEnum.getLabel());
    }else if(FieldShowEnum.VALUE.equals(type)){
      jsonGenerator.writeObject(sexEnum.getValue());
    }else {
      jsonGenerator.writeObject(sexEnum);
    }

  }
 
  @Override
  public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
      final BeanProperty beanProperty) throws JsonMappingException {
    // 为空直接跳过
    if (beanProperty != null) {
      // 非 SexEnum 类直接跳过
      if (Objects.equals(beanProperty.getType().getRawClass(), SexEnum.class)) {
        SexShow sexShow = beanProperty.getAnnotation(SexShow.class);
        if (sexShow == null) {
          sexShow = beanProperty.getContextAnnotation(SexShow.class);
        }
        if (sexShow != null) {
          return new SexSerialize(sexShow.type());
        }
      }
      return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
    return serializerProvider.findNullValueSerializer(beanProperty);
  }
}