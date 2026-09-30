import { copiaStiliDaDocument } from "./copia-stili-da-document.js";

export class VbgModal extends HTMLElement {
    constructor() {
        super();

        let root = this.attachShadow({ mode: 'open' });
        root.appendChild(VbgModal.__template.content.cloneNode(true));

        Object.defineProperties(this, {
            __isOpen: {
                enumerable: false,
                writable: true,
                value: false
            },
            __internalNodes: {
                enumerable: false,
                writable: false,
                value: {
                    container: root.querySelector('#container'),
                    header: root.querySelector('#header'),
                    body: root.querySelector('#body'),
                    footer: root.querySelector('#footer'),
                }
            },
            __eventHandlers: {
                enumerable: false,
                writable: false,
                value: {
                    container: (e) => {
                        e.stopPropagation();
                        if (e.target.isSameNode(this.__internalNodes.container)) {
                            this.close();
                        }
                    },
                    body: (e) => {
                        e.stopPropagation();
                    },
                    esckey: ({ keyCode }) => {
                        if (keyCode == 27) {
                            this.close();
                        }
                    }
                }
            }
        });
        root.querySelector('#close_btn').onclick = e => this.close();
        this.__internalNodes.body.addEventListener('click', this.__eventHandlers.body);
        this.__internalNodes.container.addEventListener('click', this.__eventHandlers.container);

        copiaStiliDaDocument(this.shadowRoot);
    }
    get isOpen() {
        return this.hasAttribute('open');
    }
    set isOpen(value) {
        if (value) {
            this.open();
        } else {
            this.close();
        }
    }
    open(scrollTop = true) {
        if (!this.hasAttribute('open')) {
            this.setAttribute('open', '');
            return;
        }
        if (this.__isOpen) {
            return;
        }
        let allow = true;
        if (typeof this.beforeOpen === "function") {
            allow = this.beforeOpen(this);
        }
        if (allow) {
            document.body.style.overflowY = 'hidden';
            this.__internalNodes.container.style.display = 'flex';
            window.addEventListener('keyup', this.__eventHandlers.esckey);
            if (scrollTop) {
                this.__internalNodes.body.scrollTop = 0;
            }
            this.__isOpen = true;
            this.dispatchEvent(new CustomEvent('show', { detail: this }));
        } else {
            this.style.left = null;
        }
    }
    close() {
        if (this.hasAttribute('open')) {
            this.removeAttribute('open');
            return;
        }
        if (!this.__isOpen) {
            return;
        }
        let allow = true;
        if (typeof this.beforeClose === 'function') {
            allow = this.beforeClose(this);
        }
        if (allow) {
            document.body.style.overflowY = null;
            this.__internalNodes.container.style.display = 'none';
            this.__isOpen = false;
            this.dispatchEvent(new CustomEvent('hide', { detail: this }));
        } else {
            this.style.left = 0;
        }
    }
    // connectedCallback() {}
    disconnectedCallback() {
        window.removeEventListener('keyup', this.__eventHandlers.esckey);
    }
    // adoptedCallback() {}
    static get observedAttributes() {
        return ['open', 'noeasyclose'];
    }
    attributeChangedCallback(name, oldval, newval) {
        // console.log("Changed", name, ":", oldval, "->", newval);
        switch (name) {
            case 'open':
                if (newval === null) {
                    this.close();
                } else {
                    this.open();
                }
                break;
            case 'noeasyclose':
                if (newval === null) {
                    this.__internalNodes.body.addEventListener('click', this.__eventHandlers.body);
                    this.__internalNodes.container.addEventListener('click', this.__eventHandlers.container);
                } else {
                    this.__internalNodes.body.removeEventListener('click', this.__eventHandlers.body);
                    this.__internalNodes.container.removeEventListener('click', this.__eventHandlers.container);
                }
                break;
        }
    }
}
Object.defineProperties(VbgModal, {
    __template: {
        enumerable: false,
        writable: false,
        value: document.createElement('template')
    },
    tagName: {
        enumerable: true,
        writable: false,
        value: 'vbg-modal'
    }
});
VbgModal.__template.innerHTML =
    `
    <div class='vbg-modal' id='container' data-no-auto-hide='1'>

        <div id='body' class='vbg-modal-body'>
            <div id='header' class='vbg-modal-header'>
                <i class='fa fa-close' id='close_btn'></i>
            </div>


            <slot name='body'></slot>

            <div id='footer' class='vbg-modal-footer'>
                <slot name='footer'>
                </slot>
            </div>
        </div>
    </div>
    `;
customElements.define(VbgModal.tagName, VbgModal);