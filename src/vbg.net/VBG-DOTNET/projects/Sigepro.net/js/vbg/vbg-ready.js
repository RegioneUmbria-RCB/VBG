(() => {
    const ready = (fn) => {
        if (document.readyState != 'loading') {
            fn();
        } else {
            document.addEventListener('DOMContentLoaded', fn);
        }

    }


    window.vbg = {
        ...window.vbg || {},
        ready: ready
    };
})();
