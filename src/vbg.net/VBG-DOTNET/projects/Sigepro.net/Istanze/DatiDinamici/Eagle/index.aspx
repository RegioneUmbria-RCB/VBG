<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.Eagle.index" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title>Esempio</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='<%=ResolveClientUrl("~/lib/bootstrap-5.2.2/css/bootstrap.min.css") %>' rel="stylesheet"/>

    <link rel="stylesheet" href="css/main.css" />
    <link rel="stylesheet" href="css/custom.css" />
    <link rel="stylesheet" href="css/spinner.css" />
    
    <style>
        .__leaflet-control-layers-list {
            max-height: 40vh;
            overflow-y: auto;
            overflow-x: hidden;
        }
    </style>

    <script type="text/javascript" src="https://eagle-collaudo.regione.fvg.it/eaglemap/1.23.0/igismap.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
</head>

<body>
    <div class="layout">
        <div id="eaglemap" class="map"></div>
        <div class="azioni">
            <div id="selezione-particella">
                <%--
                <input type="text" name="codice-particella" id="codice-particella"
                    placeholder="Codice particella"
                    value="<%=Request.QueryString["loc"] %>" />
                <button class="btn btn-primary disabled" id="cmd-localizza-particella">Localizza</button>
                --%>
                <%--<button class="btn btn-primary" style="display: none" id="cmd-conferma-selezione">Conferma localizzazione</button>--%>
                <button class="btn btn-light" style="display: none" id="cmd-chiudi">Chiudi</button>
            </div>
            <div id="spinner" style="display: none">
                <div id="spinner-status"></div>
                <div class="lds-grid"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
            </div>
            <div class="risultati" id="risultati"></div>
        </div>
    </div>
</body>
<script type="module">

    import { EagleMap } from './js/eaglemap.js';
    import { mergeSchede } from './js/merge-schede.js';

    window.focus();

    const cmdChiudi = document.getElementById('cmd-chiudi');
    /*const cmdConfermaLocalizzazione = document.getElementById('cmd-conferma-selezione');*/

    const map = new EagleMap();
    const eagleConfig = {
        parametriEagle: {
            projectName: "demo",
            center: { lat: 46.1, lng: 13 },
            zoomLevel: 9,
            locateMinZoom: 19,
            backgroundLayer: { type: "OSM", layer: "Mapnik" },
            mapLayers: ["Comuni"],
            igisServiceUrl: 'https://irdat.regione.fvg.it/WebGIS',
            igisServiceTemplate: 'https://eaglefvg.regione.fvg.it/configurationservice/servicesconfigdata/igisservice/Embedded/SUS_Portale.xml',
            credentials: 'AlxMIV4lIZQd2W4zwnlQ2JQNvJu9KqsaCuZQzCXGP9hZYrfg0z3uC1Vy3oMF4pVh',
            protocol: 'https'
        },

        contenitoreRisultati: document.getElementById('risultati')
    };

    const mostraSpinner = (messaggio) => {
        const contenitoreSpinner = document.getElementById('spinner');
        const spinnerStatus = document.getElementById('spinner-status');

        spinnerStatus.innerHTML = messaggio;

        contenitoreSpinner.style.display = 'block';
    };

    const nascondiSpinner = () => {
        const contenitoreSpinner = document.getElementById('spinner');
        contenitoreSpinner.style.display = 'none';
    };

    mostraSpinner('Inizializzazione mappa...');

    await map.init(eagleConfig);

    let results = [];

    //cmdConfermaLocalizzazione.addEventListener('click', (e) => {
    //    e.preventDefault();
    //    dispatchEvent(new CustomEvent('eagle.posizioneConfermata', { detail: results }));
    //});

    cmdChiudi.addEventListener('click', (e) => {
        e.preventDefault();
        dispatchEvent(new Event('eagle.cancel'));
        self.close();
    });

    setTimeout(async () => {

        const contenitoreRisultati = document.getElementById('risultati');

        /*
        const particelleCercate = document.getElementById('codice-particella')
            .value
            .split(',')
            .map(x => x.trim());

        console.log(particelleCercate);
        */
        const particelleCercate = [
            '<%=Request.QueryString["loc"] %>'
        ];

        try {
            const renderTarget = document.createElement('div');
            renderTarget.classList.add('accordion');
            const bboxes = [];

            for (const particella of particelleCercate) {
                mostraSpinner(`Localizzazione particella ${particella}...`);
                const locateResult = await map.localizzaParticella(particella);

                results.push(...locateResult.schede);
                bboxes.push(locateResult.bbox);
            }

            console.log('bboxes', bboxes);

            contenitoreRisultati.innerHTML = `Unione delle schede...`;

            results = mergeSchede(results);

            // map.fitBounds(bboxes);

            console.log(JSON.stringify(results));


            contenitoreRisultati.innerHTML = '';
            contenitoreRisultati.append(renderTarget);
            results.forEach(x => renderTarget.append(x.render()));

            cmdConfermaLocalizzazione.style.display = 'block';
            cmdChiudi.style.display = 'block';

        } catch (err) {
            console.log(err);
            alert(err);
            contenitoreRisultati.innerHTML = '';
        }
        finally {
            nascondiSpinner();
        }

    }, 100);


</script>
<script src='<%=ResolveClientUrl("~/lib/bootstrap-5.2.2/js/bootstrap.bundle.min.js") %>'></script>

</html>
