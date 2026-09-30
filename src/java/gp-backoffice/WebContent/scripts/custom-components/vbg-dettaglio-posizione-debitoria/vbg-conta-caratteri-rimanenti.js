export class VbgContaCaratteriRimanenti {


 constructor(textArea,containerCaratteriRimanenti,limiteCaratteri){
	 this.textArea = textArea;
	 this.limiteCaratteri = limiteCaratteri || 500;
	 this.containerCaratteriRimanenti = containerCaratteriRimanenti;
	 this.textArea.addEventListener('keyup', ()=>{
      	this.verificaLunghezza();
      
      });
 	this.textArea.addEventListener('paste', ()=>{
      	this.verificaLunghezza();
      
      });
 }
   verificaLunghezza(){
   this.containerCaratteriRimanenti.style.color = "black";
 	let el = this.textArea,
 		len = el.value.length,
        delta = this.limiteCaratteri - len,
        limiteSuperato = delta < 0;
        this.containerCaratteriRimanenti.innerHTML =  len + "/" + this.limiteCaratteri;     
 	 if (limiteSuperato) {
		this.containerCaratteriRimanenti.innerHTML = "il numero dei caratteri ha raggiunto il limite di  " + this.limiteCaratteri;
		this.containerCaratteriRimanenti.style.color = "red";
		el.value = el.value.slice(0, this.limiteCaratteri);
		/*this.verificaLunghezza();*/
      }
      
      return this.containerCaratteriRimanenti.text;
      
 }


}