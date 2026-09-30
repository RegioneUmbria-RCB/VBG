class IndicatoreCaricamentoPagina {
    show() {
        this.toggle(true);
    }

    hide() {
        this.toggle(false);
    }

    toggle(val) {
        const el = document.getElementById("indicatore-caricamento-pagina");

        if (el) {
            el.style.display = val ? "flex" : "none";
        }
    }
}


window._indicatoreCaricamentoPagina = new IndicatoreCaricamentoPagina();