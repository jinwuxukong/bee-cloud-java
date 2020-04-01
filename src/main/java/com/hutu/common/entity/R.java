package com.hutu.common.entity;

import com.hutu.common.enums.ResultCode;

import java.util.HashMap;


/**
 * 统一前端返回对象
 *
 * @author hutu
 * @date 2020-01-15 11:44
 */
public class R extends HashMap<String, Object> {

	private static final String CODE = "code";
	private static final String MSG = "msg";
	public R() {
		put(CODE, ResultCode.OK.code);
		put(MSG, ResultCode.OK.msg);
	}

	public static R error() {
		return error(ResultCode.INTERNAL_SERVER_ERROR);
	}

	public static R error(ResultCode eme) {
		R r = new R();
		r.put(CODE, eme.code);
		r.put(MSG, eme.msg);
		return r;
	}
	public static R error(String msg) {
		R r = new R();
		r.put(CODE, ResultCode.INTERNAL_SERVER_ERROR.code);
		r.put(MSG, msg);
		return r;
	}
	public static R error(int code, String msg) {
		R r = new R();
		r.put(CODE, code);
		r.put(MSG, msg);
		return r;
	}
	public static R ok() {
		return new R();
	}
	public static R ok(ResultCode isg) {
		R r = new R();
		r.put(MSG, isg.msg);
		return r;
	}
	public static R ok(String msg) {
		R r = new R();
		r.put(MSG, msg);
		return r;
	}
	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
