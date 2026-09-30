import { statiPosizioniDebitorie } from './stati-posizioni-debitorie.js';

export const getStileIconaDaCodiceStato = (codiceStato) => {
    if (codiceStato == statiPosizioniDebitorie.INCORSO) {
        return {
            icon: "fa fa-clock-o",
            descStato: 'Da pagare',
            className: "warning"
        }
    }

    if (codiceStato == statiPosizioniDebitorie.CONCLUSO_CON_ESITO_POSITIVO) {
        return {
            icon: "fa fa-check",
            descStato: 'Pagato',
            className: "success"
        }
    }

    if (codiceStato == statiPosizioniDebitorie.CONCLUSO_CON_ESITO_NEGATIVO) {
        return {
            icon: "fa fa-times-circle-o",
            descStato: 'Annullato/Con errore',
            className: "critical"
        }
    }

    return {
        icon: "fa fa-times-circle-o",
        descStato: 'Sconosciuto',
        className: "default"
    }
};
