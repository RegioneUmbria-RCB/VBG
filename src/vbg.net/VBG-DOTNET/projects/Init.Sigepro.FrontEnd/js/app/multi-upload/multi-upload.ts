interface IMultiUploadOptions {
    templateSelector: string;
    uploadFormSelector: string;
    removeFileSelector: string;
    addFileSelector: string;
}

class ArMultiUpload {

    private readonly _defaultOptions: IMultiUploadOptions = {
        templateSelector: '.upload-template',
        uploadFormSelector: '.upload-form',
        removeFileSelector: '.remove-file',
        addFileSelector: '.add-file'
    };

    private readonly _form: JQuery<HTMLElement>;
    private readonly _template: JQuery<HTMLElement>;
    private readonly _addFile: JQuery<HTMLElement>;

    constructor(private readonly _rootElement: JQuery, private readonly _options: IMultiUploadOptions) {
        this._options = $.extend({}, this._defaultOptions, this._options);

        this._form = this._rootElement.find(this._options.uploadFormSelector);
        this._template = this._rootElement.find(this._options.templateSelector);
        this._addFile = this._rootElement.find(this._options.addFileSelector);
    }

    private addFile(): void {
        const newItem = this._template.children('div').clone();
        const removeFile = newItem.find(this._options.removeFileSelector);

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

interface JQuery {
    ArMultiUpload(options: IMultiUploadOptions): JQuery;
}

(function ($) {
    $.fn.ArMultiUpload = function (options: IMultiUploadOptions) {

        return this.each((idx: number, el: any) => {
            const jqEl = $(el);
            let multiUpload = jqEl.data('__ArMultiUpload') as ArMultiUpload;

            if(!multiUpload) {
                multiUpload = new ArMultiUpload(jqEl, options);
                jqEl.data('__ArMultiUpload', multiUpload);
                multiUpload.init();
            }
        });
    }
})(jQuery);