/**
 * questo file contiene tutti i parametri di configurazione del programma
 */


//=================================================
//  PARAMETRI RELATIVI AL SISTEMA DI PROIEZIONE
//=================================================

//sistema di coordinate utilizzato (sigla EPSG)
var coordSystemCode = 'EPSG:3003';
//The extent is used to determine zoom level 0. Recommended values for a
//projection's validity extent can be found at http://epsg.io/.
var extent_italia_monte_mario 	= [1290650.93, 4192956.42, 2226749.10, 5261004.57];

proj4.defs("EPSG:3003","+proj=tmerc +lat_0=0 +lon_0=9 +k=0.9996 +x_0=1500000 +y_0=0 +ellps=intl +towgs84=-104.1,-49.1,-9.9,0.971,-2.917,0.714,-11.68 +units=m +no_defs");
ol.proj.proj4.register(proj4);

//============================================================================
//  PARAMETRI DI RISOLUZIONE ED ESTENSIONE DEL TMS NEI VARI ANNI DISPONIBILI
//============================================================================

//anni di cui abbiamo le foto aeree georeferenziate
var years_array_georef	= [2017, 2014, 2012, 2010, 2008];
//anni di cui abbiamo foto aeree (sia georeferenziate che non)
var years_array_all		= [2017, 2014, 2012, 2010, 2008, 2007, 2005, 2003, 2001, 1998];
//anno piu recente disponibile
var last_year_geo = years_array_georef[0];

//tutte le risoluzioni disponibili dei tagli TMS per ogni anno georeferenziato
var tms_resolutions 	= {};
tms_resolutions[2008] = [ 102.39999999999981, 51.19999999999990, 25.59999999999995, 12.79999999999998, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000 ];
tms_resolutions[2010] = [ 102.39999999999981, 51.19999999999990, 25.59999999999995, 12.79999999999998, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000 ];
tms_resolutions[2012] = [ 102.40000000000001, 51.20000000000000, 25.60000000000000, 12.80000000000000, 6.40000000000000, 3.20000000000000, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];
tms_resolutions[2014] = [ 102.39999999999979, 51.19999999999990, 25.59999999999995, 12.79999999999997, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];
tms_resolutions[2017] = [ 102.39999999999986, 51.19999999999993, 25.59999999999997, 12.79999999999998, 6.39999999999999, 3.20000000000000, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];

//risoluzioni dei tagli TMS interessanti per le mappe per ogni anno georeferenziato
var used_resolutions	= {};
used_resolutions[2008] = [ 25.59999999999995, 12.79999999999998, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000 ];
used_resolutions[2010] = [ 25.59999999999995, 12.79999999999998, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000 ];
used_resolutions[2012] = [ 25.60000000000000, 12.80000000000000, 6.40000000000000, 3.20000000000000, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];
used_resolutions[2014] = [ 25.59999999999995, 12.79999999999997, 6.39999999999999, 3.19999999999999, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];
used_resolutions[2017] = [ 25.59999999999997, 12.79999999999998, 6.39999999999999, 3.20000000000000, 1.60000000000000, 0.80000000000000, 0.40000000000000, 0.20000000000000, 0.10000000000000 ];

//extent del TMS per ogni anno georeferenziato
var extent 	=  {}
extent[2008] = [1640998.50000000000000, 4935534.50000000000000, 1660730.89999999990687, 4955003.29999999981374];
extent[2010] = [1641081.00000000000000, 4935616.79999999888241, 1660648.19999999995343, 4956494.19999999925494];
extent[2012] = [1640481.76351793270000, 4936018.51473952080000, 1660029.96351793270000, 4957141.81473952070000];
extent[2014] = [1640349.34499999997206, 4935769.97499999962747, 1659997.04499999992549, 4956992.87500000000000];
extent[2017] = [1640349.00012207007967, 4935770.00012207031250, 1659997.00012207007967, 4956993.00012207031250];


//origine TMS per ogni anno georeferenziato (indica la coordinata Xmin - Ymin di extent TMS)
var tms_origin 	= {};
tms_origin[2008] = [1640998.50000000000000, 4935534.50000000000000];
tms_origin[2010] = [1641081.00000000000000, 4935616.79999999888241];
tms_origin[2012] = [1640481.76351793270000, 4936018.51473952080000];
tms_origin[2014] = [1640349.34499999997206, 4935769.97499999962747];
tms_origin[2017] = [1640349.00012207007967, 4935770.00012207031250];


//====================================================
//PARAMETRI DI POSIZIONAMENTO E ZOOM INIZIALE MAPPA
//====================================================

var config_ol =
 {

    //==============================================
    //  PARAMETRI TMS DI ANNO PIU RECENTE
    //==============================================

    //URL di pubblicazione delle ortofoto
    tms_base_url          : "http://mappe.comune.modena.it/ortofoto/",
    //formato grafico dei tasselli TMS
    tms_image_extension   : "jpg",
    //anno piu recente disponibile di foto aeree
    tms_last_year_geo     : last_year_geo,
    //tutte le risoluzioni disponibili dei tagli TMS per anno georeferenziato piu recente
    tms_resolutions       : tms_resolutions[last_year_geo],
    //risoluzioni dei tagli TMS interessanti per le mappe per anno georeferenziato piu recente
    tms_used_resolutions  : used_resolutions[last_year_geo],
    //extent del TMS di anno georeferenziato piu recente
    tms_extent            : extent[last_year_geo],
    //origine TMS di anno georeferenziato piu recente (indica la coordinata Xmin - Ymin di extent TMS)
    tms_origin            : tms_origin[last_year_geo],

    //======================================================
    //PARAMETRI DI POSIZIONAMENTO E ZOOM INIZIALE MAPPA
    //======================================================

    //sistema di coordinate utilizzato (sigla EPSG)
    coord_system_code       : coordSystemCode,
    //oggetto della libreria Js OpenLayers rappresentante la proiezione in uso
    projection              : new ol.proj.Projection({ code:  coordSystemCode, extent: extent_italia_monte_mario }),
    //extent del confine comunale (inferiore a extent del TMS, che comprende anche aree fuori comune)
    territory_extent        : [1641378.837, 4936231.295, 1659667.952, 4956100.279],
    //punto su cui centrare la mappa quando si fa reset delle ricerche (corrisponde a PIAZZA GRANDE NR. 1)
    territory_center_point  : [1652769.73, 4945475.55],
    //livello di zoom iniziale
    initial_zoom            : 8,
    //livello minimo di zoom (il piu distante possibile dal suolo)
    min_zoom                : 7,
    //livello massimo di zoom (il piu vicino possibile al suolo)
    max_zoom                : 14,

    //======================================
    //PARAMETRI DI GEOSERVER
    //======================================
    //AMBIENTE TEST
    //url_geoserver     : "http://geoserver-t.comune.modena.it/geoserver",
    //AMBIENTE PROD
    url_geoserver         : "http://geoserver.comune.modena.it/geoserver",
    
    //parametri al momento non utilizzati
    //wfs_proxy_servlet_url : "wfsproxyservlet",
    //wfs_proxy_enabled     : true,

    //utente geoserver da usare per basicAuth
    //geoserver_auth_user   : "admin",
    //password crittografata di geoserver da usare per basicAuth
    //geoserver_auth_pass   : "YWRtaW46ZnJhbWFnaWEz",

    //================================================================
    //LAYERS DI BASE USATI NELLE RICERCHE DEL FORM DI POSIZIONAMENTO
    //================================================================

    html_page_elments           :
                                {
                                    //ID del DIV contenente la mappa
                                    //va indicato senza simbolo cancelletto, usato da OpenLayers
                                    map_div                         :   "map",
                                    
                                    //riferimento a elemento DOM <select> contente la lista fogli catastali
                                    fogli_input_select              :   "#lista_fogli",
                                    //riferimento a elemento DOM <select> contente la lista particelle catastali
                                    mappali_input_select            :   "#lista_mappali_select",
                                    //riferimento a elemento DOM <select> vuoto da mostrare
                                    //durante popolamento <select> lista particelle catastali
                                    mappali_input_select_alt_txt    :   "#lista_mappali_alt_text",
                                    //riferimento a elemento DOM button di conferma selezione 
                                    button_confirm_selection        :   "#conferma_selezione",
                                    //riferimento a elemento DOM button di reset selezione 
                                    button_reset_selection          :   "#annulla_selezione",
                                    //riferimento a elemento DOM contentente il messaggio "SELEZIONATO IL FOGLIO 33"
                                    selected_foglio_div             :   "#foglio_selezionato",
                                    //riferimento a elemento DOM contenente la lista delle particelle selezionate
                                    selected_particelle_div         :   "#lista_particelle_selezionate",

                                    //area complessiva del blocco particelle selezionate
                                    selection_block_div             :   "#selection_block",
                                    
                                    //attributo custom usato per indicare negli elementi <li> il valore del PARTKEY
                                    //questo rende molto piu semplice caricare dal DOM gli elementi da eliminare
                                    //es: document.querySelectorAll('[data-partkey="F257  257  100"]');
                                    form_list_element_partkey       :   "data-partkey",

                                    //attributo custom usato per indicare negli elementi <li> il valore di
                                    //'layer_particella.field_name_mappale', usato per ordinare la lista
                                    //quando aggiungo un elemento
                                    form_list_element_partnumber    :   "data-num-part",

                                    //indica se il progetto usa jquery o plain javascript
                                    use_jquery                      :   false,
                                    //questa opzione viene considerata solo se vale true opzione "use_jquery"
                                    //indica che si utilizza bootstrap-select per i campi <select> e quindi occorre
                                    //eseguire alcune funzioni aggiuntive per il corretto refresh di questo componente
                                    
                                    //NB: la versione nativa di <select> di bootstrap senza bootstrap-select 
                                    //non e' stata ancora testata, occorrerebbe fare delle prove se si decidesse di usarla
                                    use_bootstrap_select            :   false
                                },
    //workspace di geoserver comune a tutti i layers utilizzati nel progetto
    //parametro necessario nelle richieste WMS/WFS
    gs_default_workspace_name   :   "modena",
    //workspace URI di geoserver comune a tutti i layers utilizzati nel progetto
    //parametro necessario nelle richieste WMS/WFS
    gs_default_workspace_URI    :   "http://www.comune.modena.it/modena",

    /*****************************************************************************************
     * attributi relativi ai vari layer catalogati in geoserver e usati nel programma
     * 
     * NB: le convenzioni non sono vincolanti ma rendono piu veloce sviluppo e manutenzione
     * 
     * CONVENZIONI UTILIZZATE:
     * nome layer:                          'layer_name'
     * attributi layer:                     'field_name_<ETICHETTA_ATTRIBUTO>'
     * stile layer:                         'style'
     * valore di default di un attributo:   'def_value_<ETICHETTA_ATTRIBUTO>'
     * valore di un attributo 
     *  (per progetto o ente corrente):
     *                                      'value_<ETICHETTA_ATTRIBUTO>'
     * 
     ****************************************************************************************/

    baselayers_name:
                    {
                        layer_name_perimetrocom : "perimetro_comunale",
                        layer_name_viecivici    : "vie_civici_group"
                    },
    layer_aste:   
                    {
                        layer_name          :   "strade_tutte",
                        field_name_codvia   :   "XVIA"
                    },
    layer_civici:        
                    {   
                        layer_name          :   "civici",
                        field_name_codvia   :   "CODVIA",
                        field_name_numciv   :   "CIVICO",
                        field_name_espciv   :   "SUBCIV"
                    },
    layer_foglio:
                    {
                        //versione SDO
                        //layer_name          :   "fogli_sde",
                        //field_name_geom     :   "GEOMETRIA",
                        //field_name_comune   :   "COD_COM",
                        //field_name_sezione  :   "SEZ",

                        //style serve nella versione SDO.
                        //la versione shape utilizza lo stile di default
                        //style               :   "catasto_fogli",

                        //versione shape
                        //==============
                        layer_name          :   "fogli_catastali",
                        field_name_geom     :   "the_geom",
                        field_name_comune   :   "COMUNE",
                        field_name_sezione  :   "SEZIONE",
                        style               :   "catasto_fogli",
                        
                        //parametri comuni

                        //in versione SDO questo campo è bigdecimal quindi ordinamento viene corretto
                        //in versione shape questo campo è stringa quindi ci sono problemi di ordinamento
                        field_name_foglio   :   "FOGLIO",
                        field_name_allegato :   "ALLEGATO",
                        field_name_sviluppo :   "SVILUPPO",
                        def_value_allegato  :   "0",
                        def_value_sviluppo  :   "0",
                        
                        value_comune        :   "F257",
                        value_sezione       :   "_",

                        //se questo parametro e' a true, viene effettuato lo zoom sui fogli selezionati
                        //ogni volta che la selezione cambia
                        fit_selected_area_when_change:  true
                        
                    },
    layer_particella:
                    {
                        //versione SDO
                        //================
                        //layer_name              :   "particelle_sde",
                        //field_name_geom         :   "SHAPE",
                        //field_name_comune       :   "COD_BELF",
                        //field_name_sezione      :   "SEZIONE",
                        //field_name_mappale      :   "NUMERO",
                        //style                   :   "catasto_particelle_sde",

                        //versione shape
                        //==============
                        layer_name            :   "particelle_catastali",
                        field_name_geom       :   "the_geom",
                        field_name_comune     :   "COMUNE",
                        field_name_sezione    :   "SEZIONE",
                        field_name_mappale    :   "MAPPALE",
                        style                 :   "catasto_particelle_more_visible",
                        //style                 :   "catasto_particelle"

                        //parametri comuni

                        field_name_foglio       :   "FOGLIO",
                        field_name_partkey      :   "PARTKEY",

                        //se questo parametro e' a true, viene effettuato lo zoom sulle particelle selezionate
                        //ogni volta che la selezione cambia
                        fit_selected_area_when_change:  false
                    }
};
