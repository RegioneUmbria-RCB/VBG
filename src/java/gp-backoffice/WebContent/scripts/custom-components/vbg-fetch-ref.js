export class VbgFetchRef extends HTMLElement {
    constructor() {
        super();

        this.requestFormatPipe = {
            JSON: (requestObject, data) => {
                return {
                    ...requestObject,
                    headers: {
                        'Content-Type': 'application/json'
                        // 'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: JSON.stringify(data)
                }
            },

            FORM: (requestObject, data) => {
                let formData = new FormData();
                for (var name in data) {
                    if (data.hasOwnProperty(name)) {
                        formData.append(name, data[name]);
                    }
                }

                return {
                    ...requestObject,
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: formData
                }
            }
        }
    }

    get url() { return this._url; }
    set url(val) { this._url = (val || ''); }

    get method() { return this._method; }
    set method(v) { this._method = (v || 'POST').toUpperCase(); }

    get responseFormat() { return this._responseFormat; }
    set responseFormat(v) { this._responseFormat = (v || 'JSON').toUpperCase(); }

    get requestFormat() { return this._requestFormat; }
    set requestFormat(v) { this._requestFormat = (v || 'JSON').toUpperCase(); }

    connectedCallback() {
        this.url = this.getAttribute('url');
        this.method = this.getAttribute('method');
        this.responseFormat = this.getAttribute('response-format');
        this.requestFormat = this.getAttribute('request-format');
    }


    async read(request) {

        let path = this.url;

        if (path === '') {
            throw `vbg-fetch-ref: attributo url non impostato per l'elemento con id ${this.id}`;
        }

        if (this.method === 'GET' && request.data) {
            let qs = '';

            for (var name in request.data) {
                if (request.data.hasOwnProperty(name)) {
                    qs += `${name}=${encodeURIComponent(request.data[name])}&`;
                }
            }

            if (qs.length > 1) {
                qs = qs.substr(0, qs.length - 1);
            }

            path += (this.url.indexOf('?') > 0 ? "&" : "?") + qs;
        }

        let requestOptions = {
            method: this.method
        };

        if (this.method !== 'GET') {
            const formatPipe = this.requestFormatPipe[this.requestFormat] ?
                this.requestFormatPipe[this.requestFormat] :
                this.requestFormatPipe["JSON"];

            requestOptions = formatPipe(requestOptions, request.data);
        }

        const response = await fetch(path, requestOptions);

        if (response.status !== 200) {
            return null;
        }

        if (this.responseFormat == 'JSON') {
            return response.json();
        }

        if (this.responseFormat == 'BLOB') {
            return response.blob();
        }

        return response.text();
    }


    getAttributeValue(attributeName) {
        if (this.hasAttribute(attributeName)) {
            return this.getAttribute(attributeName);
        }

        return '';
    }

}

Object.defineProperties(VbgFetchRef, {
    _tagName: {
        enumerable: true,
        writable: false,
        value: 'vbg-fetch-ref'
    }
});



customElements.define(VbgFetchRef._tagName, VbgFetchRef);