class CampoDinamicoEagle {

    constructor(mainElement) {

        // Il bottone principale che verrà utilizzato per mostrare la mappa
        this.mainElement = mainElement;
        const parent = this.mainElement.parentElement;

        this.hiddenInput = parent.querySelector('input[type=hidden]');

        const idCampoLocalizzazione = this.mainElement.dataset.campoLocalizzazione;
        const indice = this.mainElement.dataset.d2indice;

        if (!idCampoLocalizzazione) {
            console.error(`il campo dinamico ${this.mainElement.dataset.nomeCampo} non ha un controllo associato da cui leggere la localizzazione`);
            alert('Errore durante l\'inizializzazione di uno o più campi di localizzazione');
            return;
        }

        //this.campoLocalizzazione = document.querySelector(`[data-nome-campo="${idCampoLocalizzazione}"][data-d2indice="${indice}"]`);
        this.campiLocalizzazione = document.querySelectorAll(`[data-nome-campo="${idCampoLocalizzazione}"]`);

        if (!this.campiLocalizzazione) {
            console.error(`Campo dinamico ${this.mainElement.dataset.nomeCampo}: non è stato possibile trovare il campo ${idCampoLocalizzazione} all'indice ${indice} che dovrebbe contenere la localizzazione`);
            alert('Errore durante l\'inizializzazione del controllo eagle');
            return;
        }

        this.initializeEvents();
    }

    initializeEvents = () => {
        this.mainElement.addEventListener('click', (e) => {
            e.preventDefault();
            this.openPopup();
        });

        this.mainElement.GetValue = () => ({ valore: this.hiddenInput.value, valoreDecodificato: this.hiddenInput.value });
        this.mainElement.SetValue = (value) => this.hiddenInput.value = value;
    };


    popupWindow = null;

    openPopup = () => {
        const localizzazioni = [...this.campiLocalizzazione].map(x => x.value).join(',');

        if (!localizzazioni) {
            return;
        }
        const url = `eagle/?loc=${localizzazioni}`;

        if (this.popupWindow) {
            this.closePopupWindow();
        }

        this.popupWindow = window.open(url, "eagle", "width=" + screen.availWidth + ",height=" + screen.availHeight);

        this.popupWindow.addEventListener('load', () => {
            this.popupWindow.addEventListener('eagle.posizioneConfermata', (e) => {
                this.setEagleData(e.detail);
                this.closePopupWindow();
            });

            this.popupWindow.addEventListener('eagle.cancel', (e) => {
                this.clearEagleData(e.detail);
                this.closePopupWindow();
            });
        });

    };

    closePopupWindow = () => {
        this.popupWindow.close();
        this.popupWindow = null;
    }

    setEagleData = (locationData) => {
        this.hiddenInput.value = JSON.stringify(locationData);
        this.mainElement.dispatchEvent(new Event('eagle.valore-modificato'));
    }

    clearEagleData = (locationData) => {
    }

}