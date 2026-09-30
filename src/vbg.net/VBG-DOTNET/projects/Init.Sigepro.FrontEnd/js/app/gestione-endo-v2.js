class GestioneAlberoEndo {
    constructor(_rootElement) {
        this._rootElement = _rootElement;
        this.CSS_CLASS_ALBERO_CHIUSO = "albero-chiuso";
        this.CSS_CLASS_ALBERO_APERTO = "albero-aperto";
    }
    init() {
        const nodiEndo = this._rootElement.find('.famigliaEndo, .tipoEndo');
        nodiEndo.parent().addClass(this.CSS_CLASS_ALBERO_CHIUSO);
        nodiEndo.on('click', (e) => {
            const el = $(e.currentTarget).parent();
            const isClosed = el.hasClass(this.CSS_CLASS_ALBERO_CHIUSO);
            if (isClosed) {
                el.removeClass(this.CSS_CLASS_ALBERO_CHIUSO);
                el.addClass(this.CSS_CLASS_ALBERO_APERTO);
            }
            else {
                el.removeClass(this.CSS_CLASS_ALBERO_APERTO);
                el.addClass(this.CSS_CLASS_ALBERO_CHIUSO);
            }
        });
        let checkSelezionate = this._rootElement.find('input[type=checkbox]').filter((e, element) => {
            return $(element).is(':checked');
        });
        let nodiPadre = checkSelezionate.closest('.' + this.CSS_CLASS_ALBERO_CHIUSO);
        while (nodiPadre.length > 0) {
            nodiPadre.removeClass(this.CSS_CLASS_ALBERO_CHIUSO);
            nodiPadre.addClass(this.CSS_CLASS_ALBERO_APERTO);
            nodiPadre = nodiPadre.closest('.' + this.CSS_CLASS_ALBERO_CHIUSO);
        }
    }
}
$.fn.alberoEndoprocedimenti = function () {
    return this.each(function () {
        const dataKey = "__alberoEndoprocedimenti";
        if (!$.data(this, dataKey)) {
            const albero = new GestioneAlberoEndo($(this));
            $.data(this, dataKey, albero);
            albero.init();
        }
    });
};
//# sourceMappingURL=gestione-endo-v2.js.map