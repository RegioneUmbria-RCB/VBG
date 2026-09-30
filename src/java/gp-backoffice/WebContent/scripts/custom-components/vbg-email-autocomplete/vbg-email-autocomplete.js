import { copiaStiliDaDocument } from '../copia-stili-da-document.js';
import { VbgEmailAutocompleteInput } from './vbg-email-autocomplete-input.js';

class VbgEmailAutocomplete extends HTMLElement {

    ul;
    campoValore;
    addressList = [];
    value = '';
    autocomplete;
    htmlTemplate = `<div class='vbg-email-autocomplete' id='container'>
                        <div class='vbg-form'>
                            <div class='form-group'>
                                <ul id="lista-destinatari" ></ul>
                            
                                <vbg-email-autocomplete-input id='input'></vbg-email-autocomplete-input>
                            </div>

                            <slot>
                            </slot>

                        </div>
                    </div>`;
    container;

    constructor() {
        super();

        const shadow = this.attachShadow({ mode: 'open' });
        const template = document.createElement('template');
        template.innerHTML = this.htmlTemplate;

        this.shadowRoot.appendChild(template.content.cloneNode(true));

        this.container = this.shadowRoot.querySelector('#container');

        this.ul = this.shadowRoot.querySelector('#lista-destinatari');

        copiaStiliDaDocument(this.shadowRoot);
    }

    connectedCallback() {

        // Inizializzo la lista degli indirizzi già presenti
        this.campoValore = this.shadowRoot.querySelector('slot').assignedElements().filter(x => x.nodeName === 'INPUT')[0];
        this.autocomplete = this.shadowRoot.querySelector('#input');

        if (!this.hasAttribute('autocomplete-url')) {
            throw 'Parametro autocomplete-url non inizializzato';
        }

        this.autocomplete.autocompleteUrl = this.getAttribute('autocomplete-url');
        this.autocomplete.autocompleteParameterName = this.getAttribute('autocomplete-parameter') || 'partial';

        this.ul.style.display = 'none';

        const indirizzi = this.campoValore.getAttribute('value').split(';').filter(x => x.trim().length > 0);

        this.campoValore.value = '';

        indirizzi.forEach(x => this.aggiungiIndirizzo(x));

        this.autocomplete.addEventListener('indirizzo-aggiunto', (e) => this.aggiungiIndirizzo(e.detail));
    }

    aggiungiIndirizzi(listaIndirizzi) {
        listaIndirizzi.split(';')
            .filter(x => x.trim().length > 0)
            .forEach(x => this.aggiungiIndirizzo(x));
    }

    aggiungiIndirizzo(address) {
	
	 	if(address.indexOf(';') > 0){
			this.aggiungiIndirizzi(address);
			return;
		}
        const li = document.createElement('li');
        li.innerText = address;

        const deleteElement = document.createElement('a');
        deleteElement.innerHTML = '<i class="fas fa-backspace"></i>';
        deleteElement.addEventListener('click', (e) => {
            e.preventDefault();
            const idx = this.addressList.indexOf(address);

            if (idx >= 0) {
                this.addressList.splice(idx, 1);
                this.ul.removeChild(li);
                this.aggiornaValori();
                this.autocomplete.focus();
            }
        });

        li.append(deleteElement);

        this.ul.append(li);

        this.addressList.push(address);

        this.aggiornaValori();
    }

    aggiornaValori() {
        this.value = this.addressList.join(';');
        this.campoValore.value = this.value;
        this.setAttribute('value', this.value);

        this.ul.style.display = this.addressList.length > 0 ? 'inline-block' : 'none';
    }
}

customElements.define("vbg-email-autocomplete", VbgEmailAutocomplete);