function PageContext(idComune, software, token) {
    return {
        idComune : idComune,
        software : software,
        token    : token
    };
}

function AshxPathService(path) {
    return { path : path};
}


function ConfirmationDialogElementService(elementId) {
    return { element : $('#' + elementId) };
}

function DivVisualizzazioneService(elementId){
    return {idDivVisualizzazione : elementId};
}