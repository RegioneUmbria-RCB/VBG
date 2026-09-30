class ArMultiUpload {
    constructor(_rootElement, _options) {
        this._rootElement = _rootElement;
        this._options = _options;
        this._defaultOptions = {
            templateSelector: '.upload-template',
            uploadFormSelector: '.upload-form',
            removeFileSelector: '.remove-file',
            addFileSelector: '.add-file'
        };
        this._options = $.extend({}, this._defaultOptions, this._options);
        this._form = this._rootElement.find(this._options.uploadFormSelector);
        this._template = this._rootElement.find(this._options.templateSelector);
        this._addFile = this._rootElement.find(this._options.addFileSelector);
    }
    addFile() {
        const newItem = this._template.children('div').clone();
        const removeFile = newItem.find(this._options.removeFileSelector);
        this._form.append(newItem);
        removeFile.on('click', (e) => {
            newItem.remove();
        });
    }
    init() {
        this.addFile();
        this._addFile.on('click', (e) => {
            e.preventDefault();
            this.addFile();
        });
    }
}
(function ($) {
    $.fn.ArMultiUpload = function (options) {
        return this.each((idx, el) => {
            const jqEl = $(el);
            let multiUpload = jqEl.data('__ArMultiUpload');
            if (!multiUpload) {
                multiUpload = new ArMultiUpload(jqEl, options);
                jqEl.data('__ArMultiUpload', multiUpload);
                multiUpload.init();
            }
        });
    };
})(jQuery);
//# sourceMappingURL=multi-upload.js.map