import { copiaStiliDaDocument } from '../copia-stili-da-document.js';

export class VbgEmailAutocompleteInput extends HTMLElement {

    htmlTemplate = `
    <div class='vbg-form'>
        <div class='vbg-email-autocomplete-input form-group' style='display: block'>
            <!--<label>Nuovo indirizzo:</label>-->
            <div style='display: inline-block;position: relative;'>
                <input type='text' id='input-indirizzo' class='form-control' placeholder='Inserisci un indirizzo e-mail, fai click su "Aggiungi" o premi invio per aggiungerlo ai destinatari' />
                <i id='spinner' style='position: relative;left: -22px;' class="fas fa-sync-alt fa-spin"></i>
                <a id='btn-aggiungi' class='btn btn-primary'>Aggiungi</a>
                <ul id='autocomplete-list'/>
            <div>
        </div>
    </div>
    `;
    input;
    ul;
    spinner;
    autocompleteUrl = '';
    autocompleteParameterName = 'partial';
    selectedIndex = -1;
    searchTimeout;
    btnAggiungi;

    constructor() {
        super();

        const shadow = this.attachShadow({ mode: 'open' });
        const template = document.createElement('template');
        template.innerHTML = this.htmlTemplate;

        this.shadowRoot.appendChild(template.content.cloneNode(true));

        copiaStiliDaDocument(this.shadowRoot);
    }

    connectedCallback() {
        this.input = this.shadowRoot.querySelector('input');
        this.ul = this.shadowRoot.querySelector('ul');
        this.btnAggiungi = this.shadowRoot.querySelector('#btn-aggiungi');
        this.spinner = this.shadowRoot.querySelector('#spinner');

        this.nascondiBottoneAggiungi();
        this.nascondiRisultati();
        this.nascondiSpinner();

        this.input.addEventListener('keydown', (e) => {

            if (e.key === ';') {
                e.preventDefault();
                return false;
            }
        });

        this.input.addEventListener('keyup', (e) => {

            this.toggleBottoneAggiungi(this.validaEmail(this.input.value));

            if (!this.handleSpecialKey(e)) {

                // Utilizzo un timeout per evitare di far partire 
                // tante richieste consecutive. Ogni nuova richiesta 
                // di autocomplete cerca di cancellare il timeout precedente
                if (this.searchTimeout) {
                    clearTimeout(this.searchTimeout);
                    this.searchTimeout = null;
                }

                this.searchTimeout = setTimeout(() => {
                    this.refreshAutocompleteList(e)
                }, 250);
            }


        });

        this.input.addEventListener('blur', () => setTimeout(() => this.nascondiRisultati(), 250));

        this.btnAggiungi.addEventListener('click', (e) => {
            const event = new CustomEvent('indirizzo-aggiunto', { detail: this.input.value });
            this.dispatchEvent(event);

            this.input.value = '';
            this.input.focus();
            this.nascondiBottoneAggiungi();
        });
    }

    validaEmail(email) {

        console.log(this.input.value);

        const regex = /^\S+@\S+\.\S+$/;
        const match = email.match(regex);
        return match && match.length > 0;
    }

    focus() {
        this.input.focus();
    }

    handleSpecialKey(e) {

        console.log(e);

        if (e.key === 'ArrowDown') {
            this.setSelected(++this.selectedIndex);
            return true;
        }

        if (e.key === 'ArrowUp') {
            this.setSelected(--this.selectedIndex);
            return true;
        }

        if (e.key === 'ArrowLeft') {
            return true;
        }

        if (e.key === 'ArrowRight') {
            return true;
        }

        if (e.key === 'Escape') {

            this.nascondiRisultati();

            return true;
        }

        if (e.key === 'Enter' && this.selectedIndex !== -1) {

            this.clickSelected();

            return true;
        }

        if (e.key === 'Enter' && this.selectedIndex === -1 && this.validaEmail(this.input.value)) {

            this.btnAggiungi.click();

            return true;
        }


        return false;
    }

    async refreshAutocompleteList(event) {

        const partial = this.input.value;

        this.nascondiRisultati();

        if (partial.length < 3) {
            return;
        }

        try {

            this.mostraSpinner();

            const result = await fetch(`${this.autocompleteUrl}?${this.autocompleteParameterName}=${encodeURIComponent(partial)}`, {
                method: 'GET'
            });

            if (result.status !== 200) {
                if (result.status === 500) {
                    console.log(await result.text());
                }
                return;
            }

            const data = await result.json();

            this.nascondiRisultati();


            data.items.forEach((item, idx) => {
                const li = document.createElement('li');
                const titolo = document.createElement('div');
                titolo.classList.add('titolo');
                titolo.innerText = item.titolo;

                const email = document.createElement('div');
                email.classList.add('email');
                email.innerText = item.email;

                li.append(titolo);
                li.append(email);

                this.ul.append(li);

                li.addEventListener('click', () => {
                    this.input.value = email.innerText;
                    this.input.focus();
                    this.nascondiRisultati();
                    this.mostraBottoneAggiungi();
                });

                li.addEventListener('mouseenter', () => { this.setSelected(idx) });
                li.addEventListener('mouseleave', () => { this.setSelected(-1) });

            });

            this.nascondiSpinner();

            if (data.items.length) {
                this.mostraRisultati();
                this.selectedIndex = -1;
            }
        } catch (err) {
            console.error(err);
        }
    }

    setSelected(index) {
        const li = this.ul.querySelectorAll('li');

        if (index > (li.length - 1)) {
            index = li.length - 1;
        }

        if (index < 0) {
            index = 0;
        }

        li.forEach((item, idx) => {
            item.classList.toggle('selected', idx === index);
        });

        this.selectedIndex = index;
    }

    clickSelected() {

        const li = this.ul.querySelectorAll('li');

        if (this.selectedIndex >= 0 && this.selectedIndex < li.length) {
            li[this.selectedIndex].click();
        }
    }

    nascondiRisultati() {
        this.selectedIndex = -1;
        this.ul.style.display = 'none';
        this.ul.innerHTML = '';
    }

    mostraRisultati() {
        this.ul.style.display = 'block';
    }

    nascondiBottoneAggiungi() {
        console.log('nascondiBottoneAggiungi');
        this.btnAggiungi.style.display = 'none';
    }

    mostraBottoneAggiungi() {
        console.log('mostraBottoneAggiungi');
        this.btnAggiungi.style.display = 'inline-block';
    }

    toggleBottoneAggiungi(condizione) {
        if (condizione) {
            this.mostraBottoneAggiungi();
        } else {
            this.nascondiBottoneAggiungi();
        }
    }

    nascondiSpinner() {
        this.spinner.style.display = 'none';
    }

    mostraSpinner() {
        this.spinner.style.display = 'inline-block';
    }
}

customElements.define("vbg-email-autocomplete-input", VbgEmailAutocompleteInput);