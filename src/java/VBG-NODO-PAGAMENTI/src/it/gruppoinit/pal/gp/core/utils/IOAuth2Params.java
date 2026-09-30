package it.gruppoinit.pal.gp.core.utils;

public interface IOAuth2Params {

    boolean validateParams();

    String buildQueryString();

    String toString();
}
