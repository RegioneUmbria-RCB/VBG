function PopupDettaglioAttivitaService(div, geoinServiceProxy) {
    var obj = {
        inizializza: function () {
            div.hide();
            div.dialog({
                height: 295,
                width: 550,
                modal: true,
                autoOpen: false
            });
        },

        mostraDettagli: function (idPunto) {
            var self = this;

            geoinServiceProxy.getDettagliIstanzaDaIdPunto(idPunto, function (data) {
                self.setDenominazione(data.Denominazione);
                self.setAttiva(data.Attiva);
                self.setOperante(data.Operante);
                self.setRichiedente(data.Richiedente);
                self.setTipoSoggetto(data.TipoSoggetto);
                self.setAzienda(data.Azienda);

                div.dialog('open');
            });
        },

        setDenominazione: function (value) {
            div.dialog('option', 'title', value);
        },

        setAttiva: function (value) {
            div.find('#txtAttiva').html(value === '1' ? 'Si' : 'No');
        },

        setOperante: function (value) {
            div.find('#txtOperante').html(value === '1' ? 'Si' : 'No');
        },

        setRichiedente: function (value) {
            div.find('#txtRichiedente').html(value);
        },

        setTipoSoggetto: function (value) {
            if (value === '')
                value = '-';

            div.find('#txtInQualitaDi').html(value);
        },

        setAzienda: function (value) {
            if (value === '')
                value = '-';

            div.find('#txtAzienda').html(value);
        }

    };

    obj.inizializza();

    return obj;
}