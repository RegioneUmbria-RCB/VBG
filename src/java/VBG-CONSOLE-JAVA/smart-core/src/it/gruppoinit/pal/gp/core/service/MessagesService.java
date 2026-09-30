package it.gruppoinit.pal.gp.core.service;

public interface MessagesService {

    public String getMessage(String key);

    public String getMessage(String key, Object[] placeHolders);
}
