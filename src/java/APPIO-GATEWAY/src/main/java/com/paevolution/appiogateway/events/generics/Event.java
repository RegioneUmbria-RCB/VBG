package com.paevolution.appiogateway.events.generics;

import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Event<T> implements ResolvableTypeProvider {

    private T source;

    @Override
    public ResolvableType getResolvableType() {

	return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(source));
    }
}
