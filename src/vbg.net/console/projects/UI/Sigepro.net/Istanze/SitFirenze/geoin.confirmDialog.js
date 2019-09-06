/// <reference path="~/js/jquery.min.js" />
/// <reference path="~/js/jquery-ui.min.js" />
/// 
/// Rappresenta un dialog che permette l'interazione con l'utente
/// nel caso di aggiunta o eliminazione di un punto
///
function ConfirmDialog(element) {

    this._element = element;

    this.confermaSpostamentoPuntoCorrente = function (onConfirm) {
        var title = "Spostamento di un punto",
            html = "Si desidera spostare il punto selezionato nella nuova posizione?";

        this._confirmDialog(title, html, onConfirm);
    };

    this.confermaEliminazionePunto = function (onConfirm) {
        var title = "Rimozione di un punto",
            html = "Si desidera rimuovere il punto selezionato alla mappa?";

        this._confirmDialog(title, html, onConfirm);
    };

    this.confermaAggiuntaPunto = function (onConfirm) {
        var title = "Aggiunta di un punto",
            html = "Si desidera aggiungere il punto selezionato alla mappa?";

        this._confirmDialog(title, html, onConfirm);
    };

    this._confirmDialog = function (title, html, onConfirm, onAbort, dialogSize) {

        if (!dialogSize)
            dialogSize = { height: 180, width: 450 };

        this
            ._element
            .html(html)
            .dialog({
                title: title,
                resizable: false,
                height: dialogSize.height,
                width: dialogSize.width,
                modal: true,
                buttons: {
                    "Conferma": function () {
                        if (onConfirm) onConfirm();
                        $(this).dialog("close");
                    },
                    "Annulla": function () {
                        if (onAbort) onAbort();
                        $(this).dialog("close");
                    }
                }
            });
    };
}