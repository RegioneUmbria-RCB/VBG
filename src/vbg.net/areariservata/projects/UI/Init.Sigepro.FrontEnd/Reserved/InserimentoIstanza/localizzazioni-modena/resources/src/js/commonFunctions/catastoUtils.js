/**
 * questo file contiene una serie di funzioni per interrogare i layer del catasto
 */

 var catastoUtils = 
 {

    /**
     * Metodo che esegue una richiesta WFS per recuperare la lista di tutti i fogli del layer dei fogli catastali.
     * Recupera solo l'informazione alfanumerica, non la geometria. 
     * Usato per popolare la tendina di ricerca per foglio
     * 
     * I due parametri di input sono opzionali
     * 
     * @param {function} successFunction - metodo eseguito i caso di esito positivo della richiesta WFS
     * @param {function} [errorFunction=catastoUtils.onError] - metodo eseguito i caso di fallimento della richiesta WFS
     */
    loadAllFogli:
            function(successFunction, errorFunction)
            {

                //prendo solo allegato e sviluppo con valore zero per non avere dei doppioni in lista fogli.
                //i numeri di particella sono univoci a livello di foglio quindi non esisteranno due particelle con stesso numero

                var filter = 
                            config_ol.layer_foglio.field_name_comune+"='"+config_ol.layer_foglio.value_comune+"' AND "+
                            config_ol.layer_foglio.field_name_sezione+"='"+config_ol.layer_foglio.value_sezione+"' AND "+
                            config_ol.layer_foglio.field_name_allegato+"='"+config_ol.layer_foglio.def_value_allegato+"' AND "+
                            config_ol.layer_foglio.field_name_sviluppo+"='"+config_ol.layer_foglio.def_value_sviluppo+"'";

            
                
            
                //nello shape FOGLIO e' un campo String, quindi ordinamento e' alfabetico (es 1,10,2,20,...)
                //nel layer SDE FOGLIO e' intero quindi i dati arrivano gia correttamente ordinati numericamente
                var sortBy = config_ol.layer_foglio.field_name_foglio;

                var optionsObj = gisUtils.prepareWFSRequestParams(
                    config_ol.layer_foglio.layer_name,
                    config_ol.gs_default_workspace_name,
                    config_ol.gs_default_workspace_URI,
                    [config_ol.layer_foglio.field_name_foglio],
                    config_ol.coord_system_code,
                    false,
                    filter,
                    gisUtils.filter_type_cql,
                    undefined,
                    sortBy
                );
        
                if(errorFunction == undefined)
                {
                    errorFunction = catastoUtils.onError;
                }
        
                gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, successFunction, errorFunction, false);
            },
    
    /**
     * Metodo che esegue una richiesta WFS per recuperare tutte le particelle catastali associate ad un foglio
     * 
     * @param {string} foglio - numero foglio catastale
     * @param {function} successFunction - metodo da eseguire in caso di successo richiesta WFS
     * @param {function} [errorFunction] - metodo da eseguire in caso di fallimento richiesta WFS
     */
    loadAllParticelleByFoglio:
            function(foglio, successFunction, errorFunction)
            {
                if(commonUtils.string.isEmptyOrNull(foglio))
                {
                    console.log("non riesco a recuperare il valore per foglio catastale selezionato");
                }
                else
                {
                    //ignoro il filtro per allegato e sviluppo, devo estrarre tutte le particella
                    var filter = 
                            config_ol.layer_particella.field_name_comune+"='"+config_ol.layer_foglio.value_comune+"' AND "+
                            config_ol.layer_particella.field_name_sezione+"='"+config_ol.layer_foglio.value_sezione+"' AND "+
                            config_ol.layer_particella.field_name_foglio+"='"+foglio+"'";

                    var optionsObj = gisUtils.prepareWFSRequestParams(
                        config_ol.layer_particella.layer_name,
                        config_ol.gs_default_workspace_name,
                        config_ol.gs_default_workspace_URI,
                        [config_ol.layer_particella.field_name_foglio, config_ol.layer_particella.field_name_mappale, config_ol.layer_particella.field_name_partkey],
                        config_ol.coord_system_code,
                        false,
                        filter,
                        gisUtils.filter_type_cql,
                        undefined,
                        config_ol.layer_particella.field_name_mappale
                    );

                    if(errorFunction == undefined)
                    {
                        errorFunction = catastoUtils.onError;
                    }
            
            
                    gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, successFunction, errorFunction, false);

                }
            },

    /**
     * 
     * @param {array} lista_partkey 
     * @param {function} successFunction 
     * @param {function} errorFunction 
     */
    loadSelectedParticelle:
            function(lista_partkey, successFunction, errorFunction)
            {
                //verifico quanti elementi contiene array.
                //se multipli fa un filtro IN
                //se uno solo fa filtro equals

                if(lista_partkey != undefined && lista_partkey.length > 0)
                {
                    var filter = undefined;
                    if(lista_partkey.length == 1)
                    {
                        filter = gisUtils.prepareEqualToFilter(false, config_ol.layer_particella.field_name_partkey, lista_partkey[0]);
                    }
                    else
                    {
                        //filtro con clausola IN perche piu di un PARTKEY richiesto
                        filter = gisUtils.prepareInFilter(false, config_ol.layer_particella.field_name_partkey, lista_partkey);
                    }

                    var optionsObj = gisUtils.prepareWFSRequestParams(
                        config_ol.layer_particella.layer_name,
                        config_ol.gs_default_workspace_name,
                        config_ol.gs_default_workspace_URI,
                        [config_ol.layer_particella.field_name_geom, config_ol.layer_particella.field_name_foglio, config_ol.layer_particella.field_name_mappale, config_ol.layer_particella.field_name_partkey],
                        config_ol.coord_system_code,
                        false,
                        filter,
                        gisUtils.filter_type_cql,
                        undefined,
                        config_ol.layer_particella.field_name_mappale
                    );

                    if(errorFunction == undefined)
                    {
                        errorFunction = catastoUtils.onError;
                    }

                    gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, successFunction, errorFunction, false);
                }

            },
   
    /**
     * Metodo che esegue una richiesta WFS per recuperare una feature foglio (compresa di geometria)
     * dato il numero foglio
     * @param {number} numeroFoglio - numero di foglio cataastale
     * @param {function} successFunction - funzione da eseguire in caso di esito positivo della richiesta WFS
     * @param {function} [errorFunction=catastoUtils.onError] - funzione da eseguire in caso di fallimento della richiesta WFS
     */
    loadFoglioByNumero:   
            function(numeroFoglio, successFunction, errorFunction) 
            {

                var filter = gisUtils.prepareLikeFilter(false, config_ol.layer_foglio.field_name_foglio, numeroFoglio);
            
                var optionsObj = gisUtils.prepareWFSRequestParams(
                    config_ol.layer_foglio.layer_name,
                    config_ol.gs_default_workspace_name,
                    config_ol.gs_default_workspace_URI,
                    [config_ol.layer_foglio.field_name_geom, config_ol.layer_foglio.field_name_foglio],
                    config_ol.coord_system_code,
                    false,
                    filter,
                    gisUtils.filter_type_cql
                );

                if(errorFunction == undefined)
                {
                    errorFunction = catastoUtils.onError;
                }

                gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, successFunction, errorFunction, false);
            },
    
    /**
     * Metodo che esegue una richiesta WFS per recuperare il numero di foglio (no geometria)
     * passando come filtro un punto di intersezione col layer dei fogli catastali
     * 
     * @param {number} pointX - coordinata X
     * @param {number} pointY - coordinata Y
     * @param {function} successFunction - funzione da eseguire in caso di esito positivo della richiesta WFS
     */
    loadFoglioByIntersectionPoint:
            function(pointX, pointY, successFunction)
            {
                var filter = gisUtils.prepareIntersectWithPointFilter(
                    false, config_ol.layer_foglio.field_name_geom, pointX, pointY, config_ol.coord_system_code
                );
        
                var optionsObj = gisUtils.prepareWFSRequestParams(
                    config_ol.layer_foglio.layer_name,
                    config_ol.gs_default_workspace_name,
                    config_ol.gs_default_workspace_URI,
                    [config_ol.layer_foglio.field_name_foglio],
                    config_ol.coord_system_code,
                    false,
                    filter,
                    gisUtils.filter_type_cql
                );

                //callback da lanciare in caso di esito positivo della richiesta WFS
                //in pratica ripeto la richiesta WFS del foglio filtrando per numero foglio
                //in questo modo includo anche eventuali sezioni separate in allegato o sviluppo
                var wfsSuccessCallBack = 
                        function(jsonResponse) 
                        {
                            var features = new ol.format.GeoJSON().readFeatures(jsonResponse);
                            if (features != undefined && features[0] != undefined) {
                                catastoUtils.loadFoglioByNumero(
                                        features[0].getProperties()[config_ol.layer_foglio.field_name_foglio], 
                                        successFunction);
                            }
                        }; 
        
                gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, wfsSuccessCallBack, catastoUtils.onError, false);

            },

    /**
     * meotodo richiamato su evento click sulla mappa, genera una richiesta WFS a Geoserver per ottenere 
     * la feature particella intersecante col punto cliccato
     * 
     * @param {*} pointX - coordinata X
     * @param {*} pointY - coordinata Y
     * @param {*} successFunction - funzione da eseguire in caso di esito positivo della richiesta WFS
     * @param {*} [errorFunction=catastoUtils.onError] - funzione da eseguire in caso di fallimento della richiesta WFS
     */
    loadParticellaByIntersectionPoint:
            function(pointX, pointY, successFunction, errorFunction)
            {
                var filter = gisUtils.prepareIntersectWithPointFilter
                    (false, config_ol.layer_particella.field_name_geom, pointX, pointY, config_ol.coord_system_code);
        
                var optionsObj = gisUtils.prepareWFSRequestParams(
                    config_ol.layer_particella.layer_name,
                    config_ol.gs_default_workspace_name,
                    config_ol.gs_default_workspace_URI,
                    [config_ol.layer_particella.field_name_geom, config_ol.layer_particella.field_name_foglio, config_ol.layer_particella.field_name_mappale, config_ol.layer_particella.field_name_partkey],
                    config_ol.coord_system_code,
                    false,
                    filter,
                    gisUtils.filter_type_cql
                );

                if(errorFunction == undefined)
                {
                    errorFunction = catastoUtils.onError;
                }
        
                gisUtils.getWFSFeatures(config_ol.url_geoserver, optionsObj, successFunction, errorFunction, false);
            },
    
    /**
     * Recupero numero del foglio da partkey.
     * i valori di allegato e sviluppo non modificano il numero foglio nel partkey
     * i foglio e i suoi eventuali frazionamenti deevono essere considerati tutti un solo foglio
     * 
     * @param {string} partkey 
     */
    getNumeroFoglioFromPartkey:
            function(partkey)
            {
                if(partkey != undefined && partkey.length > 0)
                {
                    //
                    //esempio di PARTKEY: F257  257  100
                    //                    _____||||=====  
                    return partkey.substring(5, 9).trim();
                }
                return undefined;
            },

    /**
     * Recupero valore del mappale dal partkey
     * @param {string} partkey 
     */
    getValoreMappaleFromPartkey:
            function(partkey)
            {
                if(partkey != undefined && partkey.length > 0)
                {
                    //
                    //esempio di PARTKEY: F257  257  100
                    //                    _____||||=====  
                    return partkey.substring(10, 15).trim();
                }
                return undefined;
            },
    
    /**
     * Metodo generico di gestione errore
     */
    onError:
            function(error) 
            {
                console.log('request failed', error);
            }

};