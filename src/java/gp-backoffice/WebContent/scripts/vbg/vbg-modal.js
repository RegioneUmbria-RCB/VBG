/**
 * 
 */

(() => {
    const initializeModals = (rootElement) => {

        const root = rootElement || document;

        root.querySelectorAll('.vbg-modal').forEach((el) => {
            let escListener = (e) => {
                if (e.key === "Escape") { // escape key maps to keycode `27`
                    el.hide();
                }
            };

            el.hide = () => {
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

            if (!el.dataset.autoOpen || el.dataset.autoOpen.toLowerCase()==="false") {
                el.hide();
            } else {
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
        
        // Tutti gli elementi che hanno come attributo "data-role=open-vbg-modal" e data-target-modal-id="$id"
        // possono aprire il popup indicato in data-target
        root.querySelectorAll('*[data-role=open-vbg-modal]').forEach((toggle) => {
            toggle.addEventListener('click', (e) => {
                const targetModalId = toggle.dataset.targetModalId;
                
                if (targetModalId) {
                    const targetModal = document.getElementById(targetModalId);
                    
                    if (targetModal) {
                        targetModal.show();
                        
                    }
                }
                
                e.preventDefault();
                return false;
            })
        });
    }
    
    window.vbg = {
        ...window.vbg || {},
        initializeModals: initializeModals
    };


})();
