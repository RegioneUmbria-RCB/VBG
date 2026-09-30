(() => {
    const initializeInputFilters = (rootElement) => {
        
        const root = rootElement || document;
        
        root.querySelectorAll('*[data-filter-input=UPPER_ALPHAS_AND_NUMBERS]').forEach((el) => {
            
            function toUpperCase(e) {
                const target = e.target;
                
                target.value = target.value.toUpperCase();
            }
            
            function checkInput(e) {
                e = e || event;
                var char = e.type == 'keypress'
                    ? String.fromCharCode(e.keyCode || e.which)
                    : (e.clipboardData || window.clipboardData).getData('Text');

                if (/[^a-zA-Z\d_\-]/gi.test(char)) {
                    return false;
                }
            }

            el.onkeypress = el.onpaste = checkInput;
            el.addEventListener('keyup', toUpperCase);
        });

        root.querySelectorAll('*[data-filter-input=UPPER_ALPHAS_NUMBERS_DOTS_UNDERSCORE]').forEach((el) => {

            function toUpperCase(e) {
                const target = e.target;

                target.value = target.value.toUpperCase();
            }

            function checkInput(e) {
                e = e || event;
                var char = e.type == 'keypress'
                    ? String.fromCharCode(e.keyCode || e.which)
                    : (e.clipboardData || window.clipboardData).getData('Text');

                if (/[^a-zA-Z\d_\-.]/gi.test(char)) {
                    return false;
                }
            }

            el.onkeypress = el.onpaste = checkInput;
            el.addEventListener('keyup', toUpperCase);
            el.style.textTransform = 'uppercase';
        });
    };
    
    window.vbg = {
        ...window.vbg || {},
        initializeInputFilters: initializeInputFilters
    };
    
})();