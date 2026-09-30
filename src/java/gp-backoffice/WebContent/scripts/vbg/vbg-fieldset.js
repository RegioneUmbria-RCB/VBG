(() => {
    const inizializzaFieldset = () => {
        
        const espandi = (elemento) => {
        	elemento.classList.remove('collassato');		
        }
        
        const collassa = (elemento) => {
        	elemento.classList.add('collassato');
        }
        
        document.querySelectorAll('fieldset.collassabile').forEach( elemento => {
        	
        	const legend = elemento.querySelector('fieldset>legend');
        	
        	const frecciaGiu = document.createElement('i');
        	frecciaGiu.className = 'fa fa-chevron-down';
        	
        	const frecciaSu = document.createElement('i');
        	frecciaSu.className = 'fa fa-chevron-up';
        	
        	legend.prepend(frecciaGiu);
        	legend.prepend(frecciaSu);
        	
        	legend.addEventListener('click', () => {
        		
        		if( elemento.classList.contains('collassato') ) {
        			espandi(elemento);
        		} else {
        			collassa(elemento);
        		}

        	});
        	if( elemento.dataset.collassato !== 'false' ) {
        		collassa(elemento);
        	}
        	
        });
        
    }


    window.vbg = {
        ...window.vbg || {},
        inizializzaFieldset: inizializzaFieldset
    };
})();