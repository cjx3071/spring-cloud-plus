package org.gourd.hu.core.enums;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件类型
 *
 * @author gourd
 * @create 2018-07-04 15:41
 **/
@JSONType(serializeEnumAsJavaBean = true)
public enum FileTypeEnum{
    PPT("PPT","ppt"),
    FILE("FILE","文本"),
    WORD("WORD","word"),
    EXCEL("EXCEL","excel"),
    PDF("PDF","pdf"),
    VIDEO("VIDEO","视频"),
    TXT("TXT","文本"),
    PNG("PNG","图片"),
    BPM("BPM","bpm"),
    JPG("JPG","图片"),
    GIF("GIF","gif");


    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private String desc;

    FileTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
