package com.paevolution.appiogateway;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.dto.ServiziDTO;
import com.paevolution.appiogateway.core.repository.TipoConnettoreRepository;
import com.paevolution.appiogateway.core.service.ServiziService;
import com.paevolution.appiogateway.utils.TipiConnettoreEnum;

@SpringBootTest
public class ServiziTest {

    @Autowired
    private ServiziService serviziService;
    @Autowired
    private TipoConnettoreRepository tipoConnettoreRepository;

    @Test
    public void findAllServizi_test() {

	List<ServiziDTO> servizifetched = serviziService.findAllServizi();
	assertThat(servizifetched).isNotEmpty();
    }

    @Test
    public void findServiziByTipoConnettore_test() {

	Optional<TipoConnettore> opt = tipoConnettoreRepository.findByNome(TipiConnettoreEnum.UD.getName());
	List<ServiziDTO> serviziUmbriaDigitale = serviziService.findByTipoConnettore(opt.get());
	assertThat(serviziUmbriaDigitale).isNotEmpty();
    }
}
