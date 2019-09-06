/// <reference path="../../../scripts/typings/jquery/jquery.d.ts" />
import $ = require('jquery');

interface IMultiUploadOptions {
    templateSelector?: string;
    uploadFormSelector?: string;
    removeFileSelector?: string;
    addFileSelector?: string;
}

class ArMultiUpload {

    private _defaultOptions: IMultiUploadOptions = {
        templateSelector: '.upload-template',
        uploadFormSelector: '.upload-form',
        removeFileSelector: '.remove-file',
        addFileSelector: '.add-file'
    };

    private _form: JQuery;
    private _template: JQuery;
    private _addFile: JQuery;

    constructor(private _rootElement:JQuery, private _options: IMultiUploadOptions) {
        this._options = $.extend({}, this._defaultOptions, this._options);

        this._form = _rootElement.find(this._options.uploadFormSelector);
        this._template = _rootElement.find(this._options.templateSelector);
        this._addFile = _rootElement.find(this._options.addFileSelector);
    }

    private addFile(): void {
        var newItem = this._template.children('div').clone(),
            removeFile = newItem.find(this._options.removeFileSelector);

        this._form.append(newItem);

        removeFile.on('click', (e) => {
            newItem.remove();
        });

    }

    public init(): void {

        this.addFile();

        // Aggiunta di un elemento
        this._addFile.on('click', (e) => {
            e.preventDefault();

            this.addFile();
        });
   }
}

(function ($) {
    $.fn.ArMultiUpload = function (options) {
        
        return this.each(function (idx, el) {
            var jqEl = $(el),
                multiUpload = jqEl.data('__ArMultiUpload') as ArMultiUpload;

            if (multiUpload == null) {

                multiUpload = new ArMultiUpload(jqEl, options);

                jqEl.data('_ArMultiUpload', multiUpload);

                multiUpload.init();
            }

        });
    }
})(jQuery);