package com.paevolution.appiogateway.web.model.response;

import lombok.Data;

@Data
public class ResponseWrapper<T, S> {

    private T obj1;
    private S obj2;

    public ResponseWrapper(T obj1) {

	this.obj1 = obj1;
    }

    public ResponseWrapper(T obj1, S obj2) {

	this.obj1 = obj1;
	this.obj2 = obj2;
    }
}