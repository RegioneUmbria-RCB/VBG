/**
 * 
 */

(() => {
    const initializeModals = (rootElement) => {

        const root = rootElement || document;

        root.querySelectorAll('.vbg-modal').forEach((el) => {
            let escListener = (e) => {
                console.log('listener');
                if (e.key === "Escape") { // escape key maps to keycode `27`
                    el.hide();
                }
            };

            let alwaysVisible = el.dataset?.alwaysVisible ?? false;

            el.hide = () => {

                if (alwaysVisible) {
                    return;
                }

                el.style.display = 'none';
                document.removeEventListener('keyup', escListener);
                el.dispatchEvent(new Event('hidden'));
            };

            el.show = () => {
                el.style.display = 'flex';

                if (!el.dataset.noAutoHide) {
                    document.addEventListener('keyup', escListener);
                }
                
                el.dispatchEvent(new Event('shown'));
            };

            
            if (!alwaysVisible) {
                el.hide();
            }
            else {
                el.show();
            }

            /*
            el.addEventListener('click', (e) => {
                if (e.target == el && !el.dataset.noAutoHide) {
                    el.hide();
                }
            });
            */

            el.querySelectorAll('*[data-role=toggle-popup]').forEach((toggle) => {
                toggle.addEventListener('click', (e) => {
                    el.hide();
                    e.preventDefault();
                    return false;
                })
            });
        });
    }
    
    window.vbg = {
        ...window.vbg || {},
        initializeModals: initializeModals
    };


})();
