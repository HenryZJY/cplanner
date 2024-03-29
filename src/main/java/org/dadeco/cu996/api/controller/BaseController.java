package org.dadeco.cu996.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dadeco.cu996.api.error.BusinessException;
import org.dadeco.cu996.api.error.EmBusinessError;
import org.dadeco.cu996.api.model.RuntimeUserInfo;
import org.dadeco.cu996.api.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Object handlerException(HttpServletRequest request, Exception ex) {

		Map<String, Object> responseData = new HashMap<>();
		log.error(ex.getMessage(), ex);

		if (ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			responseData.put("errCode", businessException.getErrCode());
			responseData.put("errMsg", businessException.getErrMsg());
		} else {
			responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
			responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrMsg());
		}
		return CommonReturnType.create(responseData, "fail");
	}

	protected RuntimeUserInfo getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (RuntimeUserInfo) auth.getPrincipal();
	}
}
