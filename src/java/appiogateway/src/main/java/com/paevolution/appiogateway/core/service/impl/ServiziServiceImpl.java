package com.paevolution.appiogateway.core.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.Servizi;
import com.paevolution.appiogateway.core.domain.TipoConnettore;
import com.paevolution.appiogateway.core.dto.ServiziDTO;
import com.paevolution.appiogateway.core.repository.ServiziRepository;
import com.paevolution.appiogateway.core.service.ServiziService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiziServiceImpl implements ServiziService {

    private ServiziRepository serviziRepository;

    public ServiziServiceImpl(ServiziRepository serviziRepository) {

	this.serviziRepository = serviziRepository;
    }

    @Override
    public Servizi findByIdcomuneCodicecomuneSoftware(String idcomune, String codicecomune, String software) {

	Optional<Servizi> optServizi = serviziRepository.findByIdcomuneAndCodicecomuneAndSoftware(idcomune, codicecomune, software);
	if (optServizi.isPresent()) {
	    log.info("il servizio è presente");
	    return optServizi.get();
	}
	return null;
    }

    @Override
    public List<ServiziDTO> findAllServizi() {

	List<Servizi> servizis = IterableUtils.toList(this.serviziRepository.findAll());
	List<ServiziDTO> serviziDTOs = mapList(servizis, ServiziDTO.class);
	return serviziDTOs;
    }

    @Override
    public List<ServiziDTO> findByTipoConnettore(TipoConnettore tipoConnettore) {

	List<Servizi> servizis = IterableUtils.toList(this.serviziRepository.findByTipoConnettore(tipoConnettore));
	return mapList(servizis, ServiziDTO.class);
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {

	ModelMapper modelMapper = new ModelMapper();
	modelMapper.addMappings(new PropertyMap<Servizi, ServiziDTO>() {

	    @Override
	    protected void configure() {

		map().setTipoConnettore(source.getTipoConnettore().getNome());
	    }
	});
	return source.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
    }

    @Override
    public Optional<Servizi> findByIdServizio(String idServizio) {

	return serviziRepository.findByIdServizio(idServizio);
    }

    @Override
    public boolean isNotBlankOAuth2ClientInfo(String consumerKey, String consumerSecret, String clientRegistrationId) {

	return StringUtils.isNotBlank(consumerKey) && StringUtils.isNotBlank(consumerSecret) && StringUtils.isNotBlank(clientRegistrationId);
    }
}
