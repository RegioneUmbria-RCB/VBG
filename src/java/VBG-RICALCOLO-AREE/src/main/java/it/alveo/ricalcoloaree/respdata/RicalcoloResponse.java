package it.alveo.ricalcoloaree.respdata;

import java.util.List;

public class RicalcoloResponse {

    private List<String> ricalcoloAreeIdList;

    public RicalcoloResponse(List<String> ricalcoloAreeIdList) {

	this.ricalcoloAreeIdList = ricalcoloAreeIdList;
    }

    // Getter and Setter
    public List<String> getRicalcoloAreeIdList() {

	return ricalcoloAreeIdList;
    }

    public void setRicalcoloAreeIdList(List<String> ricalcoloAreeIdList) {

	this.ricalcoloAreeIdList = ricalcoloAreeIdList;
    }
}
