export const vbgRisolviEtichette = async (root, alias, software) => {

	const etichette = [...root
						.querySelectorAll('[data-etichetta]')]
						.map( element => {
							return element.dataset.etichetta;
						});

	const postParams = { request: { alias, software, etichette } };

	const response = await fetch('../services/rest/layout/testi', {
    	method: 'POST',
        headers: {
        	'Accept': 'application/json',
            'Content-Type': 'application/json',
		},
        body: JSON.stringify(postParams)
	});
                	
	if( response.status == 200){
		_impostaEtichette(root, await response.json() );
	}
	
	// gestire status != 200
}


function _impostaEtichette(root, etichette){
	
	etichette.forEach( etichetta => {
		let nome = etichetta.response.etichetta;
		let valore = etichetta.response.testo;
		
		let label = root.querySelectorAll('[data-etichetta="' + nome + '"]');
		if( label != null ){
			label.forEach( el => {
				el.textContent = valore;	
			} );
		}
	});
}