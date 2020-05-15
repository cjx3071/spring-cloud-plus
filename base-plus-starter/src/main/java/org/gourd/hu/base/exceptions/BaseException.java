/*
 * Copyright 2019-2029 gourd.hu(https://github.com/gourd.hu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gourd.hu.base.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * 自定义基础异常
 *
 * @author gourd.hu
 * @date 2018-11-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException{

    private Integer code;
    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

}
