export const getDettaglioPosizione = async (urlStato, idPosizione) => {

    let formData = new FormData();
    formData.append('idDettPosizioneDebitoria', idPosizione);

    const response = await fetch(urlStato, {
        method: 'GET',
        cache: 'no-cache',
        headers: {
            // 'Content-Type': 'application/json'
            'Content-Type': 'application/x-www-form-urlencoded'
        }/*,
        body: formData // body data type must match "Content-Type" header
        */
    });

    return response.json();
}