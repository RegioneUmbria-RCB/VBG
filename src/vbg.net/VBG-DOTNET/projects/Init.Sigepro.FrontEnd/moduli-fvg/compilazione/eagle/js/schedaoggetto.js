import { nextUUId } from './unique-id-generator.js';

export class DatsetScheda {

    values = [];
    excluded = false;
    onStatoElementoModificato = () => { };

    constructor(feature) {
        const labels = feature.__LABELS__;
        this.uniqueId = nextUUId();

        for (const key in feature) {
            if (Object.hasOwnProperty.call(feature, key) && !key.startsWith('__') && key !== 'bbox') {
                const etichetta = Object.hasOwnProperty.call(labels, key) ? labels[key] : key;
                const valore = feature[key];

                if (!valore) {
                    continue;
                }

                this.values.push({
                    etichetta,
                    valore: this.isLink(valore) ? this.renderLink(valore) : valore
                });
            }
        }
    }

    isLink(valore) {
        return valore.toString().startsWith('http') || valore.toString().startsWith('https');
    }

    renderLink(valore) {
        return `<a href="${valore}" target="_blank">${valore}</a>`;
    }

    render = () => {
        const parent = document.createElement('div');
        parent.classList.add('pb-2', 'mb-2', 'border-bottom')

        const dl = document.createElement('dl');

        dl.classList.add('mb-0', 'pb-0');

        this.values.forEach(x => {

            if (x.etichetta.toLowerCase() === 'id' || x.etichetta.toLowerCase() === 'id_ogg') {
                return;
            }

            const dt = document.createElement('dt');
            dt.innerHTML = x.etichetta;

            const dd = document.createElement('dd');
            dd.innerHTML = x.valore;

            dl.append(dt);
            dl.append(dd)
        });

        const div = document.createElement('div');
        div.classList.add('form-check', 'form-switch', 'ms-2', 'me-2');
        div.innerHTML = `<label class="form-check-label" for="_${this.uniqueId}">Escludi/Non applicabile</label>`;
        div.innerHTML += `<input class="form-check-input" type="checkbox" role="switch" id="_${this.uniqueId}">`;


        const check = div.querySelector('input');
        check.checked = this.excluded;

        check.addEventListener('change', () => {

            dl.classList.toggle('disabled', check.checked);

            this.excluded = check.checked;
            this.onStatoElementoModificato();
        });

        /*
        <div class="form-check form-switch">
          <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault">
          <label class="form-check-label" for="flexSwitchCheckDefault">Default switch checkbox input</label>
        </div>
        */
        parent.appendChild(dl);
        parent.appendChild(div);
        return parent;
    };



    getItemCount = () => {
        return this.values.filter(x => x.etichetta.toLowerCase() !== 'id' && x.etichetta.toLowerCase() !== 'id_ogg').length;
    }

}


export class SchedaOggetto {

    constructor(data) {
        const feature = data.features[0];

        this.uniqueId = nextUUId();

        // console.log(`uid oggetto ${this.uniqueId}`);
        this.layerName = data.layerName;
        this.title = feature.__TITLE__;
        this.dataSets = [];

        for (const feat of data.features) {
            this.dataSets.push(new DatsetScheda(feat));
        }

        console.log(this);
    }

    render() {
        const root = document.createElement('div');
        root.classList.add('accordion-item');

        const itemsCount = this.dataSets.reduce((acc, curr) => acc + curr.getItemCount(), 0);

        root.innerHTML = `
            <h2 class="accordion-header sticky-top" id="heading${this.uniqueId}">
                <div class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${this.uniqueId}" aria-controls="collapse${this.uniqueId}">
                <div>
                    <div>${this.title}</div>
                    <div id='_${this.uniqueId}' class='small text-muted'>${itemsCount} valori ${(this.dataSets.length > 1) ? (' in ' + this.dataSets.length + ' elementi') : ''}
                        <span class='small text-muted'></span>
                    </div>
                </div>
                </div>
            </h2>`;

        const spanElementiDisabilitati = root.querySelector('#_' + this.uniqueId + '>span');

        const acccordionItem = document.createElement('div');
        acccordionItem.id = `collapse${this.uniqueId}`;
        acccordionItem.classList.add('accordion-collapse', 'collapse', 'collapsed');
        acccordionItem.dataset.bsParent = '#heading' + this.uniqueId;

        root.append(acccordionItem);

        for (const dati of this.dataSets) {

            dati.onStatoElementoModificato = () => {
                const count = this.dataSets.filter(x => x.excluded).length;

                spanElementiDisabilitati.innerHTML = '';

                if (count > 0) {
                    spanElementiDisabilitati.innerHTML = `(${count} esclusi da utente)`;
                }
            };

            acccordionItem.append(dati.render());
        }

        return root;
    }



    mergeValues(altraScheda) {

        //for (let ds of altraScheda.dataSets) {
        //    if (this.isNewDataset(ds)) {
        //        this.dataSets.push(ds);
        //    }
        //}

        if (altraScheda.dataSets.length) {
            this.dataSets.push(...altraScheda.dataSets);
        }
    }

    isNewDataset(newDataset) {

        for (var ds of this.dataSets) {

            let tuttiValoriUguali = true;

            for (var valore1 of newDataset.values) {
                if (ds.values.findIndex(x => x.etichetta == valore1.etichetta && x.valore == valore1.valore) == -1) {

                    tuttiValoriUguali = false;

                    break;
                }
            }

            if (tuttiValoriUguali) {
                return false;
            }
        }

        return true;
    }


}
