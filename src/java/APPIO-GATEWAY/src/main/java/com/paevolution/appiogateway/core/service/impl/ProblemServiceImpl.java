package com.paevolution.appiogateway.core.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.appiogateway.core.domain.Messaggi;
import com.paevolution.appiogateway.core.domain.Problem;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.dto.ProblemDTO;
import com.paevolution.appiogateway.core.repository.MessaggiRepository;
import com.paevolution.appiogateway.core.repository.ProblemRepository;
import com.paevolution.appiogateway.core.service.ProblemService;
import com.paevolution.appiogateway.exceptions.MessageNotFoundException;

@Service
public class ProblemServiceImpl implements ProblemService {

    private ProblemRepository problemRepository;
    private MessaggiRepository messaggiRepository;

    public ProblemServiceImpl() {

    }

    @Autowired
    public void setProblemRepository(ProblemRepository problemRepository) {

	this.problemRepository = problemRepository;
    }

    @Autowired
    public void setMessaggiRepository(MessaggiRepository messaggiRepository) {

	this.messaggiRepository = messaggiRepository;
    }
    // private EntitiesAndRestBeanAdapter<Risposte, RisposteRestBean> adapter;
    // @Autowired
    // public RisposteServiceImpl(RisposteRepository risposteRepository) {
    // this.risposteRepository = risposteRepository;
    // this.adapter = new EntitiesAndRestBeanAdapterImpl<Risposte,
    // RisposteRestBean>(Risposte.class, RisposteRestBean.class);
    // }
    //
    // @Override
    // public List<RisposteRestBean> findAll() throws Exception {
    // Iterable<Risposte> serviceResult = this.risposteRepository.findAll();
    //
    // return this.adapter.entitiesToRestBeans(serviceResult);
    // }

    @Override
    public Optional<ProblemDTO> findById(Long id) {

	ProblemDTO problemDTO = null;
	Optional<Problem> problemOPT = problemRepository.findById(id);
	ModelMapper mapper = new ModelMapper();
	if (problemOPT.isPresent()) {
	    problemDTO = mapper.map(problemOPT.get(), ProblemDTO.class);
	}
	return Optional.ofNullable(problemDTO);
    }

    @Override
    public Problem save(MessaggiDTO msgDTO) throws Exception {

	ModelMapper mapper = new ModelMapper();
	Problem problem = mapper.map(msgDTO, Problem.class);
	dataIntegration(problem);
	return problemRepository.save(problem);
    }

    private void dataIntegration(Problem entity) throws Exception {

	Optional<Messaggi> messageOpt = messaggiRepository.findById(entity.getId());
	if (!messageOpt.isPresent()) {
	    throw new MessageNotFoundException(
		    "Impossibile impostare la property message per l'entity problem: Messaggio con id=[ " + entity.getId() + "] non trovato!");
	}
	entity.setMessaggi(messageOpt.get());
    }
}
