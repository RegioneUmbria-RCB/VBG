System.register(['./menu-panel'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
        return new (P || (P = Promise))(function (resolve, reject) {
            function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
            function rejected(value) { try { step(generator.throw(value)); } catch (e) { reject(e); } }
            function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
            step((generator = generator.apply(thisArg, _arguments)).next());
        });
    };
    var menu_panel_1;
    var MenuBuilder2;
    return {
        setters:[
            function (menu_panel_1_1) {
                menu_panel_1 = menu_panel_1_1;
            }],
        execute: function() {
            class MenuBuilder2 {
                constructor(_rootElement, _options) {
                    this._rootElement = _rootElement;
                    this._options = _options;
                    this._bloccaPannelli = false;
                    this._menuTemplate = null;
                    this._menuPanels = new Array();
                    this._aggiungiPreferiti = new Array();
                }
                loadTemplate(url) {
                    return __awaiter(this, void 0, Promise, function* () {
                        var html = yield jQuery.ajax({
                            url: url,
                            dataType: "html"
                        });
                        return jQuery.templates(html);
                    });
                }
                build() {
                    return __awaiter(this, void 0, Promise, function* () {
                        var data = yield jQuery.ajax({
                            url: this._options.menuUrl
                        });
                        this._menuTemplate = yield this.loadTemplate(this._options.templateUrl);
                        var searchResultTemplate = yield this.loadTemplate(this._options.searchResultTemplateUrl);
                        let html = this._menuTemplate.render(data);
                        this._rootElement.append(html);
                        // Associo a tutti i pannelli di ricerca testuale il template da utilizzare
                        this._rootElement.find('.MENU_RICERCA').each((idx, elem) => {
                            jQuery.data(elem, 'searchResultTemplate', searchResultTemplate);
                        });
                        // Inizializzo i pannelli
                        this._rootElement.find('.dropdown').each((idx, el) => {
                            this._menuPanels.push(new menu_panel_1.MenuPanel(jQuery(el)));
                        });
                    });
                }
            }
            exports_1("MenuBuilder2", MenuBuilder2);
        }
    }
});
//# sourceMappingURL=menu-builder.js.map