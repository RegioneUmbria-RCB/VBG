function convertDataURIToBinary(dataURI) {
    const newUrl = dataURI.replace(/[\n\r]/g, '');
    const raw = window.atob(newUrl);
    const rawLength = raw.length;

    const array = new Uint8Array(new ArrayBuffer(rawLength));
    for (let i = 0; i < rawLength; i++) {
        array[i] = raw.charCodeAt(i) & 0xff;
    }
    return array;
}

export const loadPdfInIFrame = async (id, base64) => {
    try {
        const pdfjsframe = document.getElementById(id);

        if (!base64 == "") {

            if (pdfjsframe.contentWindow.readyState !== 'loading') {
                loadData();
            } else {
                pdfjsframe.contentWindow.addEventListener('DOMContentLoaded', function () {
                    loadData();
                });
            }

            function loadData() {
                pdfjsframe.contentWindow.PDFViewerApplication.open(convertDataURIToBinary(base64));

                pdfjsframe.contentDocument.getElementById('outerContainer').style.visibility = 'visible';
                pdfjsframe.contentDocument.getElementById('loadingContainer').style.display = 'none';
            }
        }
    } catch (error) { console.error(error); }
}