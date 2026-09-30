package it.gruppoinit.pal.gp.core.features.segnaposto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegnapostoServiceImpl implements ISegnapostoService {

    @Autowired
    ISegnapostoDAO segnapostoDAO;

    @Override
    public String getTemplateRTF(String segnaposto) {

	return this.segnapostoDAO.getTemplateRTF(segnaposto);
    }

    @Override
    public String getTemplateHTML(String segnaposto) {

	return this.segnapostoDAO.getTemplateHTML(segnaposto);
    }
}
