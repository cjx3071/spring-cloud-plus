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

package org.gourd.hu.base.request.filter;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.request.bean.RequestDetail;
import org.gourd.hu.base.request.holder.RequestDetailThreadLocal;
import org.gourd.hu.base.utils.IpAddressUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求详情信息Filter
 *
 * @author gourd.hu
 * @date 2020/3/25
 **/
@Slf4j
public class RequestDetailFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestDetailFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 设置请求详情信息
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 请求IP
        String ip = IpAddressUtil.getIpAddr(httpServletRequest);
        // 请求路径
        String path = httpServletRequest.getRequestURI();
        RequestDetail requestDetail = new RequestDetail()
                .setIp(ip)
                .setPath(path);
        // 设置请求详情信息
        RequestDetailThreadLocal.setRequestDetail(requestDetail);
        chain.doFilter(request, response);
        // 释放
        RequestDetailThreadLocal.remove();
    }

    @Override
    public void destroy() {
        log.info("RequestDetailFilter destroy");
    }
}
