ModificaRiduzioniVisualizzazioneExtender = function(){
	this._elementiRegistrati = new Array();
}

ModificaRiduzioniVisualizzazioneExtender.prototype = {
	getElementiRegistrati : function(){ return this._elementiRegistrati;},
	AddNew : function( idElemento , checkBoxId , doubleTextBoxId , imageButtonId , riduzione )
	{
		var nuovoElemento = new Object();
		nuovoElemento["checkBox"] = document.getElementById( checkBoxId );
		nuovoElemento["doubleTextBox"] = document.getElementById( doubleTextBoxId );
		nuovoElemento["imageButton"] = document.getElementById( imageButtonId );
		nuovoElemento["riduzione"] = riduzione;
		
		nuovoElemento.checkBox.datiVisualizzazione = nuovoElemento;
		nuovoElemento.checkBox.onclick = function(){
											this.datiVisualizzazione.doubleTextBox.style.visibility = this.checked ? "visible" : "hidden";
											this.datiVisualizzazione.imageButton.style.visibility = this.checked ? "visible" : "hidden";
											this.datiVisualizzazione.doubleTextBox.value = this.checked ? this.datiVisualizzazione.riduzione : "0,0";
										 };
		
		nuovoElemento.doubleTextBox.style.visibility = nuovoElemento.checkBox.checked ? "visible" : "hidden";
		nuovoElemento.imageButton.style.visibility = nuovoElemento.checkBox.checked ? "visible" : "hidden";
		
		this.getElementiRegistrati().push( nuovoElemento );
	}
}

var g_visualizzazioneExtender = new ModificaRiduzioniVisualizzazioneExtender();
