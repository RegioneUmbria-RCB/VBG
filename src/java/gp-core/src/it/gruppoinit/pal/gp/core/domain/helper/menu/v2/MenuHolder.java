package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class MenuHolder {

    public MenuHolder() {

	super();
    }

    public MenuHolder(List<MenuHelper> menu) {

	super();
	this.menu = menu;
    }

    private List<MenuHelper> menu = new ArrayList<MenuHelper>();

    public List<MenuHelper> getMenu() {

	return menu;
    }

    public void setMenu(List<MenuHelper> menu) {

	this.menu = menu;
    }
}
