define(["require", "exports", "jquery"], function (require, exports, $) {
    "use strict";
    Object.defineProperty(exports, "__esModule", { value: true });
    exports.RicercaModulistica = void 0;
    var CategoriaModulistica = /** @class */ (function () {
        function CategoriaModulistica(jqueryElement, numeroFigli) {
            this.jqueryElement = jqueryElement;
            this.numeroFigli = numeroFigli;
            this.figliVisibili = numeroFigli;
        }
        CategoriaModulistica.prototype.verificaVisibilita = function () {
            if (this.figliVisibili > 0) {
                this.jqueryElement.removeClass('collapsed');
                this.jqueryElement.addClass('expanded');
            }
            else {
                this.jqueryElement.addClass('collapsed');
                this.jqueryElement.removeClass('expanded');
            }
            this.jqueryElement.toggle(this.figliVisibili > 0);
        };
        CategoriaModulistica.prototype.figlioNascosto = function () {
            this.figliVisibili--;
            this.verificaVisibilita();
        };
        CategoriaModulistica.prototype.figlioVisibile = function () {
            this.figliVisibili++;
            this.verificaVisibilita();
        };
        return CategoriaModulistica;
    }());
    var ElementoModulistica = /** @class */ (function () {
        function ElementoModulistica(categoria, jqueryElement, titolo, descrizione) {
            this.categoria = categoria;
            this.jqueryElement = jqueryElement;
            this.visibile = true;
            this.titolo = titolo.toUpperCase();
            this.descrizione = descrizione.toUpperCase();
        }
        ElementoModulistica.prototype.contiene = function (testo) {
            var ucase = testo.toUpperCase();
            if (ucase === '') {
                return true;
            }
            return this.titolo.indexOf(ucase) > 0 || this.descrizione.indexOf(ucase) > 0;
        };
        ;
        ElementoModulistica.prototype.mostra = function (mostra) {
            this.jqueryElement.toggle(mostra);
            if (mostra && !this.visibile) {
                this.categoria.figlioVisibile();
            }
            if (!mostra && this.visibile) {
                this.categoria.figlioNascosto();
            }
            this.visibile = mostra;
        };
        ;
        return ElementoModulistica;
    }());
    var RicercaModulistica = /** @class */ (function () {
        function RicercaModulistica() {
            this.datiModulistica = new Array();
            this.categorieModulistica = new Array();
        }
        RicercaModulistica.prototype.filtraValori = function (testo) {
            testo = testo.toUpperCase();
            var valoriTrovati = this.datiModulistica.map(function (item) { return item.contiene(testo); });
            this.datiModulistica.forEach(function (item, idx) { return item.mostra(valoriTrovati[idx]); });
            $('#nessun-risultato').toggle(valoriTrovati.filter(function (x) { return x; }).length === 0);
            if (testo === '') {
                this.collapseAll();
            }
        };
        RicercaModulistica.prototype.handleTextSearch = function () {
            var _this = this;
            var textbox = $('#ricercaTestuale'), categorieNode = $('.categoria-modulistica');
            categorieNode.each(function (idx, item) {
                var elCat = $(item), children = elCat.find('.dati-modulistica'), cat = new CategoriaModulistica(elCat, children.length);
                _this.categorieModulistica.push(cat);
                children.each(function (idx, dati) {
                    var el = $(dati), modulistica = new ElementoModulistica(cat, el, el.find('>h3').text(), el.find('>div').text());
                    _this.datiModulistica.push(modulistica);
                });
            });
            textbox.on('keyup', function () { return _this.filtraValori(textbox.val()); });
        };
        RicercaModulistica.prototype.expandOrCollapse = function (element) {
            var section = element.parent();
            if (section.hasClass('collapsed')) {
                this.expand(section);
            }
            else {
                this.collapse(section);
            }
        };
        RicercaModulistica.prototype.collapse = function (section) {
            section.addClass('collapsed');
            section.removeClass('expanded');
        };
        RicercaModulistica.prototype.expand = function (section) {
            section.removeClass('collapsed');
            section.addClass('expanded');
        };
        RicercaModulistica.prototype.collapseAll = function () {
            $('.categoria-modulistica').removeClass('expanded');
            $('.categoria-modulistica').addClass('collapsed');
        };
        RicercaModulistica.prototype.collegaHandlerCollapse = function () {
            var _this = this;
            $('.categoria-modulistica>h2').on('click', function (e) {
                _this.expandOrCollapse($(e.target));
                e.preventDefault();
            });
            $('.categoria-modulistica>h2>i').on('click', function (e) {
                _this.expandOrCollapse($(e.target).parent());
                e.preventDefault();
            });
        };
        return RicercaModulistica;
    }());
    exports.RicercaModulistica = RicercaModulistica;
});
//# sourceMappingURL=RicercaModulistica.js.map