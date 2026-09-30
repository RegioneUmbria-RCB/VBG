function estraiFileNameDaHeaders(contentDisposition) {
    const pattern = 'FILENAME=';
    const fileNameIdx = contentDisposition.toUpperCase().indexOf(pattern);
    let fileName = contentDisposition.substring(fileNameIdx + pattern.length);

    if (fileName.startsWith('"')) {
        fileName = fileName.substring(1, fileName.length - 1);
    }

    return fileName;
}

function scaricaFileDaBlob(blob, fileName) {
    var url = window.URL.createObjectURL(blob);
    var a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    a.remove();
}

export const scaricaDocumentoConRetry = async (url, numeroMassimoTentativi) => {
    let blob = {};
    const timeout = (ms) => new Promise(resolve => setTimeout(resolve, ms));

    for (let tentativo = 0; tentativo < numeroMassimoTentativi; tentativo++) {

        const response = await fetch(url);

        if (response.status !== 200) {
            const errore = await response.text();
            console.error(errore);
            throw errore;
        }

        blob = await response.blob();

        if (blob.size === 0) {

            await timeout(1000);
            continue;
        }

        scaricaFileDaBlob(blob, estraiFileNameDaHeaders(response.headers.get('content-disposition')));

        return true;
    }

    return false
}