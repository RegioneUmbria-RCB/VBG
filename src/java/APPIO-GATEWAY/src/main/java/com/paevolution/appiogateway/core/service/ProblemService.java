package com.paevolution.appiogateway.core.service;

import java.util.Optional;

import com.paevolution.appiogateway.core.domain.Problem;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.dto.ProblemDTO;

public interface ProblemService {

    // List<RisposteRestBean> findAll() throws Exception;
    public Problem save(MessaggiDTO msgDTO) throws Exception;

    public Optional<ProblemDTO> findById(Long id);
}
