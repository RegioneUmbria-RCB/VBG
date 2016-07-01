// recupero dei service
var id2cService = getService('istanzedyn2datiServiceImpl');
var d2cService = getService('dyn2CampiServiceImpl');

// CERCO LA LISTA DEI CAMPI DINAMICI
var id2datis = id2cService.findByIstanzaAndDyn2Campi(currIstanza.getId().getCodice(),992,0);

var id2datisArray = id2datis.toArray();

var richiedente = currIstanza.getRichiedente();
var valore = 'Il richiedente ' + richiedente.getDescrizioneRichiedente()+ ' non ha specificato un valore per ANAGRAFE.TIPOANAGRAFE';

if(richiedente.getTipoanagrafe()!=null){
    if(richiedente.getTipoanagrafe().equals("F")){
        valore = 'Il richiedente ' + richiedente.getDescrizioneRichiedente()+ ' DICHIARA di essere nato a ' + richiedente.getComuneNascita().getComune();

    }else{
        valore = 'Il richiedente ' + richiedente.getDescrizioneRichiedente()+ ' è un\'impresa';    
    }
}
if(id2datisArray.length>0){
        for (x in id2datisArray){
          // AGGIORNO IL CAMPO
                var id2d = id2datisArray[x];
            if((id2d.getValore()==null||id2d.getValore().equals(''))){
                id2d.setValore(valore);
                id2d.setValoredecodificato(valore);
                id2cService.update(id2d);                        
                print('aggiornato campo');
            }
        }
}else{
 // INSERISCO IL CAMPO
        var id2d = new Istanzedyn2dati();
        id2d.getId().setCodiceistanza(currIstanza.getId().getCodice());
        id2d.getId().setFkD2cId(992);
        id2d.getId().setIndice(0);
        id2d.getId().setIndiceMolteplicita(0);
        id2d.setIstanza(currIstanza);
        var d2c = d2cService.findById(new PkId(992));
        id2d.setDyn2Campi(d2c);
        id2d.setValore(valore);
        id2d.setValoredecodificato(valore);        
        id2cService.insert(id2d);
        print('inserito campo');
}