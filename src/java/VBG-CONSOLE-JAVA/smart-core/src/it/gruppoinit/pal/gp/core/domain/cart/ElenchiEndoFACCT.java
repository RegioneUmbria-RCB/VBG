package it.gruppoinit.pal.gp.core.domain.cart;

public class ElenchiEndoFACCT {

    private AlberoEndo endoCart = new AlberoEndo();
    private AlberoEndo endoNecessari = new AlberoEndo();
    private AlberoEndo endoRicorrenti = new AlberoEndo();
    private AlberoEndo altriEndo = new AlberoEndo();

    public AlberoEndo getEndoCart() {

	return endoCart;
    }

    public void setEndoCart(AlberoEndo endoCart) {

	this.endoCart = endoCart;
    }

    public AlberoEndo getEndoNecessari() {

	return endoNecessari;
    }

    public void setEndoNecessari(AlberoEndo endoNecessari) {

	this.endoNecessari = endoNecessari;
    }

    public AlberoEndo getEndoRicorrenti() {

	return endoRicorrenti;
    }

    public void setEndoRicorrenti(AlberoEndo endoRicorrenti) {

	this.endoRicorrenti = endoRicorrenti;
    }

    public AlberoEndo getAltriEndo() {

	return altriEndo;
    }

    public void setAltriEndo(AlberoEndo altriEndo) {

	this.altriEndo = altriEndo;
    }
}
