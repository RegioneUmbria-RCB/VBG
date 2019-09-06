
function Layer(plugin, panelKey, layerKey, rendererKey) {
    this._plugin        = plugin;
    this._panelKey      = panelKey;
    this._layerKey      = layerKey;
    this._rendererKey   = rendererKey;

    console.log("Registrato layer con panel:" + this._panelKey + ", layer " + this._layerKey + " e renderer " + this._rendererKey);

    this.mostra = function (listaId) {
        console.log('Layer->mostra listaId:' + listaId);

        this._plugin.overlay_elements(this._panelKey, this._layerKey, this._rendererKey, listaId);
        this._plugin.overlay_refresh(this._panelKey);
    };

    this.nascondi = function () {
        console.log('Layer->nascondi');

        this._plugin.overlay_delete(this._panelKey);
        //this._plugin.overlay_refresh(this._panelKey);
    };

    this.aggiungiPunto = function (idPunto) {
        console.log('Layer->aggiungiPunto idPunto:' + idPunto);

        var self = this;

        this._plugin.add_element(this._layerKey, idPunto);
        console.log("Aggiunto punto con id " + idPunto + " al layer " + this._layerKey);
    };

    this.spostaPunto = function (idPunto) {
        console.log('Layer->spostaPunto idPunto:' + idPunto);

        this._plugin.update_element(this._layerKey, idPunto);
    };

    this.eliminaPunto = function (idPunto) {
        console.log("Layer->eliminaPunto idPunto" + idPunto + " dal layer " + this._layerKey);

        this._plugin.remove_element(this._layerKey, idPunto, true);

        var self = this;

        //setTimeout(function () { self._plugin.overlay_refresh(self._panelKey); }, 700);

    };

    this.aggiorna = function () {
        this._plugin.overlay_refresh(this._panelKey);
    };
}