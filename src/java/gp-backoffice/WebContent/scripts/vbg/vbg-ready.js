(() => {



    const ready = (fn) => {

        const MAX_CHECK = 10;       // se entro 10 ripetizioni l'applicazione non è stata inizializzata allora invoco comunque la callback di ready
        const CHECK_TIMEOUT = 20;   // effettuo un check ogni 20ms

        const checkInitialized = (retryId) => {

            if (initialize._isInitialised || retryId >= MAX_CHECK) {
                if (document.readyState != 'loading') {
                    fn();
                } else {
                    document.addEventListener('DOMContentLoaded', fn);
                }
            } else {
                setTimeout(() => checkInitialized(++retryId), CHECK_TIMEOUT);
            }
        }

        checkInitialized(0);
    };

    const initialize = (fn) => {

        initialize._isInitialised = false;

        if (document.readyState != 'loading') {
            fn();
            initialize._isInitialised = true;
        } else {
            document.addEventListener('DOMContentLoaded', () => {
                fn();
                initialize._isInitialised = true;
            });
        }
    };

    initialize._isInitialised = false;


    window.vbg = {
        ...window.vbg || {},
        ready: ready,
        initialize: initialize
    };
})();
