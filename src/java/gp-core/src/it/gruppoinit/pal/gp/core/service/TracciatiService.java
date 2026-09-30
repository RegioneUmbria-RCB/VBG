package it.gruppoinit.pal.gp.core.service;

import java.io.File;
import java.util.List;

public interface TracciatiService {

    public List<File> creaTracciatiRegistrazioni(List<Integer> codiciRegistrazioni, File tempDir);
}
