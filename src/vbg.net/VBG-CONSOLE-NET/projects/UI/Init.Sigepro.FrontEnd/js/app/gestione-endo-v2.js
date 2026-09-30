define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.GestioneAlberoEndo = void 0;
    var GestioneAlberoEndo = /** @class */ (function () {
        function GestioneAlberoEndo(_rootElement) {
            this._rootElement = _rootElement;
            this.CSS_CLASS_ALBERO_CHIUSO = "albero-chiuso";
            this.CSS_CLASS_ALBERO_APERTO = "albero-aperto";
        }
        GestioneAlberoEndo.prototype.init = function () {
            var _this = this;
            var nodiEndo = this._rootElement.find('.famigliaEndo, .tipoEndo');
            nodiEndo.parent().addClass(this.CSS_CLASS_ALBERO_CHIUSO);
            nodiEndo.on('click', function (e) {
                var el = $(e.currentTarget).parent(), isClosed = el.hasClass(_this.CSS_CLASS_ALBERO_CHIUSO);
                if (isClosed) {
                    el.removeClass(_this.CSS_CLASS_ALBERO_CHIUSO);
                    el.addClass(_this.CSS_CLASS_ALBERO_APERTO);
                }
                else {
                    el.removeClass(_this.CSS_CLASS_ALBERO_APERTO);
                    el.addClass(_this.CSS_CLASS_ALBERO_CHIUSO);
                }
            });
            var checkSelezionate = this._rootElement.find('input[type=checkbox]').filter(function (e, element) {
                return $(element).is(':checked');
            });
            var nodiPadre = checkSelezionate.closest('.' + this.CSS_CLASS_ALBERO_CHIUSO);
            while (nodiPadre.length > 0) {
                nodiPadre.removeClass(this.CSS_CLASS_ALBERO_CHIUSO);
                nodiPadre.addClass(this.CSS_CLASS_ALBERO_APERTO);
                nodiPadre = nodiPadre.closest('.' + this.CSS_CLASS_ALBERO_CHIUSO);
            }
        };
        return GestioneAlberoEndo;
    }());
    exports.GestioneAlberoEndo = GestioneAlberoEndo;
    $.fn['alberoEndoprocedimenti'] = function () {
        return this.each(function () {
            var dataKey = "__alberoEndoprocedimenti";
            if (!$.data(this, dataKey)) {
                var albero = new GestioneAlberoEndo($(this));
                $.data(this, dataKey, albero);
                albero.init();
            }
        });
    };
});
//# sourceMappingURL=gestione-endo-v2.js.map