package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;

@XmlRootElement(name = "response")
public class FindAlberoProcTempiResponse {

    @XmlElement(name = "tempi")
    private List<AlberoProcTempiTestataModel> tempi;

    public static FindAlberoProcTempiResponse FromAlberoprocTempi(List<AlberoprocTempi> tempi) {

	if (tempi == null || tempi.size() == 0) {
	    return null;
	}
	FindAlberoProcTempiResponse response = new FindAlberoProcTempiResponse();
	response.tempi = new ArrayList<AlberoProcTempiTestataModel>();
	for (AlberoprocTempi tempo : tempi) {
	    response.tempi.add(AlberoProcTempiTestataModel.FromAlberoprocTempi(tempo));
	}
	return response;
    }

    public List<AlberoProcTempiTestataModel> getTempi() {

	return tempi;
    }

    public void setTempi(List<AlberoProcTempiTestataModel> tempi) {

	this.tempi = tempi;
    }
}
