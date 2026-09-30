function __XmlFileLoader() {
    var obj = {};

    obj.loadXmlFile = function (xmlFile, onParseCompleted) {
        onParseCompleted((window.DOMParser) ? this._loadXmlFile_other(xmlFile) : this._loadXmlFile_ie(xmlFile));
    };

    obj._loadXmlFile_ie = function (xmlFile) {
        var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = "false";
        xmlDoc.loadXML(xmlFile);

        return xmlDoc;
    };

    obj._loadXmlFile_other = function (xmlFile) {
        var parser = new DOMParser();
        return parser.parseFromString(xmlFile, "text/xml");
    };

    return obj;
}


function FoglioParticellaXmlResultsParser(tipoCatastoCercato) {
    var obj = {};

    obj._tipoCatastoCercato = tipoCatastoCercato;
    obj.nomeTipo = 'FoglioParticellaXmlResultsParser';
    obj._xmlFileLoader = new __XmlFileLoader();

    obj.parse = function (find_type, xmlResult, onParseSuccess) {
        console.log("FoglioParticellaXmlResultsParser->parse xmlResult=" + xmlResult);

        var self = this;
        var results = [];

        this._xmlFileLoader.loadXmlFile(xmlResult, function (xmlDoc) {

            console.log("Tipo ricerca effettuata:" + find_type);

            var elements = xmlDoc.getElementsByTagName("item");

            if (!elements.length || find_type !== "fgl_part") {
                onParseSuccess(results, true);
                return;
            }

            for (i = 0; i < elements.length; i++) {               
                if ((self._tipoCatastoCercato == 'F') && (elements[i].firstChild.nodeValue.match(/FABBRICATI/) != null))
                    results.push(elements[i].getAttributeNode("order").nodeValue);

                if ((self._tipoCatastoCercato == 'T') && (elements[i].firstChild.nodeValue.match(/PARTICELLE/) != null))
                    results.push(elements[i].getAttributeNode("order").nodeValue);
            }

/*
            for (i = 0; i < elements.length; i++) {
                switch (find_type) {
                    case "topo":
                    case "via_civ":
                        results.push(elements[i].getAttributeNode("order").nodeValue);
                        break;
                    case "fgl_part":
                        if ((self._tipoCatastoCercato == 'F') && (elements[i].firstChild.nodeValue.match(/FABBRICATI/) != null))
                            results.push(elements[i].getAttributeNode("order").nodeValue);

                        if ((self._tipoCatastoCercato == 'T') && (elements[i].firstChild.nodeValue.match(/PARTICELLE/) != null))
                            results.push(elements[i].getAttributeNode("order").nodeValue);
                        break;
                }
            }
*/
            onParseSuccess(results, true);
        });
    };

    return obj;
}


function NullXmlResultParser() {
    var obj = {};

    obj.nomeTipo = 'NullXmlResultParser';
    obj.parse = function (find_type, xmlResult, onParseSuccess) {
        onParseSuccess([]);
    };

    return obj;
}

function ViaCivicoXmlResultsParser() {
    var obj = {};

    obj.nomeTipo = 'ViaCivicoXmlResultsParser';
    obj._xmlFileLoader = new __XmlFileLoader();

    obj.parse = function (find_type, xmlResult, onParseSuccess) {
        console.log("ViaCivicoXmlResultsParser->parse xmlResult=" + xmlResult);

        var self = this;
        var results = [];

        this._xmlFileLoader.loadXmlFile(xmlResult, function (xmlDoc) {

            console.log("Tipo ricerca effettuata:" + find_type);

            var elements = xmlDoc.getElementsByTagName("item");

            if (!elements.length || find_type !== "via_civ") {
                onParseSuccess(results, true);
                return;
            }

            for (i = 0; i < elements.length; i++) {
                results.push(elements[i].getAttributeNode("order").nodeValue);
            }

            onParseSuccess(results, true);
        });
    };

    return obj;
}

function IdPuntoXmlResultsParser(idPunto, nomeLayer) {
    var obj = {};

    obj._idPunto = idPunto;
    obj._nomeLayer = nomeLayer;
    obj.nomeTipo = 'IdPuntoXmlResultsParser';
    obj._xmlFileLoader = new __XmlFileLoader();

    obj.parse = function (find_type, xmlResult, onParseSuccess) {
        console.log("IdPuntoXmlResultsParser->parse xmlResult=" + xmlResult);

        var self = this;
        var results = [];

        this._xmlFileLoader.loadXmlFile(xmlResult, function (xmlDoc) {

            console.log("Tipo ricerca effettuata:" + find_type);

            var elements = xmlDoc.getElementsByTagName("item");

            if (!elements.length || find_type !== self._nomeLayer) {
                onParseSuccess(results, false);
                return;
            }

            for (i = 0; i < elements.length; i++) {
                results.push(elements[i].getAttributeNode("order").nodeValue);
            }

            onParseSuccess(results, false);
        });
    };

    return obj;
}