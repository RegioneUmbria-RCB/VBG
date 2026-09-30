var configuration = {
    projectName: "demo",
    center: { lat: 46.1, lng: 13 },
    zoomLevel: 9,
    locateMinZoom: 19,
    backgroundLayer: { type: "OSM", layer: "Mapnik" },
    mapLayers: ["Comuni"],
    igisServiceUrl: 'https://irdat.regione.fvg.it/WebGIS',
    igisServiceTemplate: 'http://eaglefvg.regione.fvg.it/configurationservice/servicesconfigdata/igisservice/Embedded-test/TestLegenda.xml',
    credentials: 'AlxMIV4lIZQd2W4zwnlQ2JQNvJu9KqsaCuZQzCXGP9hZYrfg0z3uC1Vy3oMF4pVh',
    protocol: 'https'
};

var igismap;
var options;

function init() {
    igismap = new IgisMap("eaglemap", configuration, onLoad, this);
}

function onLoad(result, scope) {
    if (result.isError) {
        console.error(result);
        alert("Errore di inizializzazione: " + JSON.stringify(result.messages));
        return;
    }

    visualizzaControlli();
}

function visualizzaControlli() {
    //igismap.showLegend({ position: "topright" });
    igismap.showLegend({
        position: 'topleft',
        layers: true,
        check: true,
    });
    igismap.showScale({ position: "bottomright" });
    igismap.showNavigation({ position: "bottomright" });
}

function schedaOggetto() {
    const cood = new Coordinate(46.079776, 13.234572);
    var options;
    igismap.schedaOggetto(cood, options, this.callbackSchedaOggetto, this);
};

function schedaOggettoByCond_province() {
    const cond = "'[NOME]'='Udine'";
    const livello = "Province";
    igismap.schedaOggettoByCond(cond, livello, this.callbackSchedaOggetto, this);
};

function schedaOggettoByCond_comune() {
    const cond = "'[NOME]'='Udine'";
    const livello = "Comuni";
    igismap.schedaOggettoByCond(cond, livello, this.callbackSchedaOggetto, this);
};

function schedaOggettoByCond_particelle() {
    var layer = "catasto-particelle";
    var condition = "[CODPARTICELLA]='L050_00330012300000000'";
    igismap.schedaOggettoByCond(condition, layer, (e) => console.log(e), this);
}

function schedaOggettoByShape_comuni() {
    const shape = {
        "type": "Feature",
        "properties": {},
        "geometry": {
            "type": "Polygon",
            "coordinates": [
                [
                    [
                        13.226680755615234,
                        46.05298193687039
                    ],
                    [
                        13.251571655273438,
                        46.0564366503051
                    ],
                    [
                        13.248138427734375,
                        46.074183321902574
                    ],
                    [
                        13.221702575683594,
                        46.07084881222031
                    ],
                    [
                        13.226680755615234,
                        46.05298193687039
                    ]
                ]
            ]
        }
    };
    const id = 'Comuni';
    igismap.schedaOggettoByShape(shape.geometry, id, this.callbackSchedaOggetto, this);
};

function schedaOggettoByShape_particelle() {
    const feature = {
        "type": "Feature",
        "properties": {},
        "geometry": {
            "type": "Polygon",
            "coordinates": [
                [
                    [
                        13.218408823013306,
                        46.07974287104546
                    ],
                    [
                        13.219047188758848,
                        46.07974287104546
                    ],
                    [
                        13.219047188758848,
                        46.08037172721711
                    ],
                    [
                        13.218408823013306,
                        46.08037172721711
                    ],
                    [
                        13.218408823013306,
                        46.07974287104546
                    ]
                ]
            ]
        }
    };
    const layer = 'catasto-particelle';
    igismap.draw.add(feature); //esempio
    igismap.schedaOggettoByShape(feature.geometry, layer, callbackSchedaOggetto, this);
}

function schedaOggettoByFeatureId() {
    const layer = 'Province';
    const id = 1; //__UNIQUEVAL__
    igismap.schedaOggettoByFeatureId(id, layer, 'Comuni', this.callbackSchedaOggetto, this);
};

function schedaOggettoByCond_FeatureId() {
    igismap.schedaOggettoByCond("'[NOME]'='Udine'", "Province", (r) => {
        console.log(r);
        const id = r.data[0].features[0].__UNIQUEVAL__;
        igismap.schedaOggettoByFeatureId(id, 'Province', 'Comuni', this.callbackSchedaOggetto, this);
    }, this);
};

function schedaOggettoByCond_FeatureId_particelle() {
    var layer = "catasto-particelle";
    var cond = "[CODPARTICELLA]='L050_00330012300000000'";
    igismap.schedaOggettoByCond(cond, layer, (r) => {
        console.log(r);
        const id = r.data[0].features[0].__UNIQUEVAL__;
        const options = { nomiLivelli: ['Comuni', 'QU_5000'] };
        igismap.schedaOggettoByFeatureId(id, layer, options, this.callbackSchedaOggetto, this);
    }, this);
};

function locate() {
    var layer = "catasto-particelle";
    var condition = "[CODPARTICELLA]='L050_00330012300000000'";
    var options = { title: "Particella 1" };
    igismap.locate(layer, condition, options, (e) => console.log(e), this);
}

function callbackSchedaOggetto(r) {
    console.log(r);

    //esempio inquadramento
    //var inquadramento = new Inquadramento();
    //var bbox = r.data[0].bbox;
    //inquadramento.bBox = new LocationRect(bbox.top, bbox.left, bbox.bottom, bbox.right);
    //igismap.posiziona(inquadramento);
}


