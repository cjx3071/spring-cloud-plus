package org.gourd.hu.base.handler;


import org.gourd.hu.base.data.BaseResponse;
import org.gourd.hu.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 统一异常处理器
 * @author gourd
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

	/**
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		log.debug("请求有参数才进来:{} ",binder.getObjectName());
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "gourd");
	}

	/**
	 * 处理自定义业务异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = BusinessException.class)
	public BaseResponse handleException(BusinessException e) {
		// 打印堆栈信息
		log.error("异常信息：",e);
		return BaseResponse.failure(e.getMessage());
	}

	/**
	 * 捕捉校验异常(BindException)
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BindException.class})
	public BaseResponse validException(BindException e) {
		log.error("异常信息：",e);
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		Map<String, Object> result = this.getValidError(fieldErrors);
		return new BaseResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
	}

	/**
	 * 捕捉校验异常(MethodArgumentNotValidException)
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public BaseResponse validException(MethodArgumentNotValidException e) {
		log.error("异常信息：",e);
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		Map<String, Object> result = this.getValidError(fieldErrors);
		return new BaseResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(), result.get("errorList"));
	}

	/**
	 * 捕捉404异常
	 * @return
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public BaseResponse handle(NoHandlerFoundException e) {
		log.error("异常信息：",e);
		return new BaseResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
	}

	/**
	 * 处理所有不可知的异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public BaseResponse handleException(Exception e){
		// 控制台打印log
		log.error("异常信息：",e);
		return BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage() == null ? "未知异常" : e.getMessage() );
	}
	/**
	 * 获取状态码
	 * @param request
	 * @return
	 */
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.valueOf(statusCode);
	}

	/**
	 * 获取校验错误信息
	 * @param fieldErrors
	 * @return
	 */
	private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
		Map<String, Object> result = new HashMap<String, Object>(16);
		List<String> errorList = new ArrayList<String>();
		StringBuffer errorMsg = new StringBuffer("校验异常(ValidException):");
		for (FieldError error : fieldErrors) {
			errorList.add(error.getField() + "-" + error.getDefaultMessage());
			errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
		}
		result.put("errorList", errorList);
		result.put("errorMsg", errorMsg);
		return result;
	}
}