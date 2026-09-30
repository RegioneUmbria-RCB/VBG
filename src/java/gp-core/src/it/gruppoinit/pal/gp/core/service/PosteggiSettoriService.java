package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;

import java.util.List;

public interface PosteggiSettoriService extends BaseService<PosteggiSettori, PkId> {

    List<PosteggiSettori> findByDescrizione(String textToSearch);
}
