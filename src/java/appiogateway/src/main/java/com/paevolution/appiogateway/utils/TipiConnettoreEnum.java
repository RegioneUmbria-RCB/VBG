package com.paevolution.appiogateway.utils;

public enum TipiConnettoreEnum {

    UD("UD"), IOITALIA("IOITALIA");

    private String name;

    TipiConnettoreEnum(String name) {

	this.name = name;
    }

    public String getName() {

	return name;
    }
}
