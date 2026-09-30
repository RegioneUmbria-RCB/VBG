import { SchedaOggetto } from "./schedaoggetto.js?_=" + Date.now();
import { extendBBox } from "./extend-bbox.js";

export class EagleMap {

    _igismap;
    config;


    init(config) {

        this.config = config;

        return new Promise((resolve, reject) => {

            this._igismap = new IgisMap("eaglemap", config.parametriEagle,
                (result, scope) => {

                    if (result.isError) {
                        console.error(result);
                        alert("Errore di inizializzazione: " + JSON.stringify(result.messages));
                        reject(JSON.stringify(result.messages));

                        return;
                    }

                    this._igismap.showLegend({
                        position: 'topleft',
                        layers: true,
                        check: true,
                    });
                    this._igismap.showScale({ position: "bottomright" });
                    this._igismap.showNavigation({ position: "bottomright" });

                    resolve();

                }, this);

        });

    }

    localizzaParticella(particella) {

        return new Promise((resolve, reject) => {
            if (particella.trim().length == 0) {
                resolve(null);
            }

            const layer = "catasto-particelle";
            const condition = `[CODPARTICELLA]='${particella}'`;
            const options = { title: "Particella 1" };

            this._igismap.locate(layer, condition, options, (e) => {
                console.log('_igismap.locate: ', e);
                this.schedaOggettoDaParticella(particella, resolve, reject);
            }, this);

        });
    }

    schedaOggettoDaParticella(particella, resolve, reject) {

        const options = {
            nomiLivelli: [
                'Comuni',
                'Dset_11008',
                'Dset_11011',
                'Dset_11012',
                'Dset_11015',
                'Dset_11036',
                'Dset_11016',
                'Dset_11007',
                'Dset_11379',
                'Dset_11380',
                'Dset_11017',
                'Dset_11381',
                'Dset_11018',
                'Dset_11019',
                'Dset_11009',
                'Dset_11020',
                'Dset_11021',
                'Dset_11022',
                'Dset_11010',
                'Dset_11027',
                'Dset_11028',
                'Dset_11031',
                'Dset_11033',
                'Dset_11034'
            ]
        };

        const layer = "catasto-particelle";
        const condition = `[CODPARTICELLA]='${particella}'`;

        this._igismap.schedaOggettoByCond(condition, layer,
            (r) => {

                if (!r.data || !r.data[0]) {
                    // alert(`Particella ${particella} non trovata`);
                    //this.config.contenitoreRisultati.innerHTML = '';

                    reject(`Particella ${particella} non trovata`);
                    return;
                }

                const id = r.data[0].features[0].__UNIQUEVAL__;
                const bbox = r.data[0].features[0].bbox;

                console.log('dati particella', r.data[0].features[0]);
                this._igismap.schedaOggettoByFeatureId(id, layer, options, (e) => {

                    console.log('scheda oggetto ', e);

                    if (e.isError) {
                        console.error(r);
                        reject(e.message);
                        return;
                    }

                    resolve({
                        bbox: bbox,
                        schede: e.data.map(x => new SchedaOggetto(x))
                    });

                }, this);
            }
            , this);
    }

    renderSchedaOggetto(r) {

        this.config.contenitoreRisultati.innerHTML = "";
        console.log('scheda oggetto ', r);

        if (r.isError) {
            console.error(r);
            alert("Errore: " + r.message);
            return;
        }

        const renderTarget = document.createElement('div');
        renderTarget.classList.add('accordion');
        r.data.forEach(x => {
            renderTarget.append(new SchedaOggetto(x).render());
        });

        this.config.contenitoreRisultati.append(renderTarget);

        //esempio inquadramento
        //var inquadramento = new Inquadramento();
        //var bbox = r.data[0].bbox;
        //inquadramento.bBox = new LocationRect(bbox.top, bbox.left, bbox.bottom, bbox.right);
        //igismap.posiziona(inquadramento);
    }

    fitBounds(bboxes) {
        const bounds = extendBBox(bboxes);

        this._igismap._mapRender._map.fitBounds(
            bounds
        );
    }

    centerOnBBox(bboxes) {
        const bounds = extendBBox(bboxes);

        this._igismap._mapRender._map.setView(bounds.getCenter(), 16);
    }
}
