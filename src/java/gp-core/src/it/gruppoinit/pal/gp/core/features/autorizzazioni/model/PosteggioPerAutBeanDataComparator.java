package it.gruppoinit.pal.gp.core.features.autorizzazioni.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class PosteggioPerAutBeanDataComparator implements Comparator<PosteggioPerAutBean> {

    @Override
    public int compare(PosteggioPerAutBean o1, PosteggioPerAutBean o2) {

	return o1.getUltimapresenza().compareTo(o2.getUltimapresenza());
    }

    public static void main(String[] args) {

	PosteggioPerAutBean o1 = new PosteggioPerAutBean();
	o1.setUltimapresenza(Utilities.parseDateString("2019-10-14", "yyyy-MM-dd"));
	o1.setMercato("ottobre");
	PosteggioPerAutBean o2 = new PosteggioPerAutBean();
	o2.setUltimapresenza(Utilities.parseDateString("2019-12-02", "yyyy-MM-dd"));
	o2.setMercato("dicembre");
	List<PosteggioPerAutBean> library = new ArrayList<PosteggioPerAutBean>();
	library.add(0, o2);
	library.add(1, o1);
	for (PosteggioPerAutBean p : library) {
	    System.out.println(p.getMercato());
	}
	Collections.sort(library, new PosteggioPerAutBeanDataComparator());
	for (PosteggioPerAutBean p : library) {
	    System.out.println(p.getMercato());
	}
    }
}
