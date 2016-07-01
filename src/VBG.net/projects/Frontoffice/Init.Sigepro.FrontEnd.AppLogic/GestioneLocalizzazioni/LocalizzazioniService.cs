using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
	public class LocalizzazioniService
	{
		IStradarioRepository _stradarioRepository;
		ISalvataggioDomandaStrategy _persistenzaStrategy;

		public LocalizzazioniService(IStradarioRepository stradarioRepository, ISalvataggioDomandaStrategy persistenzaStrategy)
		{
			_stradarioRepository = stradarioRepository;
			_persistenzaStrategy = persistenzaStrategy;
		}

		public void EliminaLocalizzazione(int idDomanda, int idLocalizzazione)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(idLocalizzazione);

			_persistenzaStrategy.Salva(domanda);

		}

		public void AggiungiLocalizzazione(int idDomanda, NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentiCatastali = null)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(localizzazione, riferimentiCatastali);

			_persistenzaStrategy.Salva(domanda);
		}

		public IEnumerable<StradarioDto> FindByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
		{
			return this._stradarioRepository.GetByMatchParziale(aliasComune, codiceComune, comuneLocalizzazione, indirizzo);
		}

		public void AssegnaRiferimentiCatastaliALocalizzazione(int idDomanda, int idLocalizzazione, NuovoRiferimentoCatastale riferimentiCatastali)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Localizzazioni.AssegnaRiferimentiCatastaliALocalizzazione(idLocalizzazione, riferimentiCatastali);

			_persistenzaStrategy.Salva(domanda);
		}

		public void EliminaRiferimentiCatastali(int idDomanda, int idRiferimentoCatastale)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Localizzazioni.EliminaRiferimentoCatastale(idRiferimentoCatastale);

			_persistenzaStrategy.Salva(domanda);
		}
	}
}
