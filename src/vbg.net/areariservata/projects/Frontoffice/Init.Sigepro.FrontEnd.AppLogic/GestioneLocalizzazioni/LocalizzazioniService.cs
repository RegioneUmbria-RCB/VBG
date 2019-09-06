using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
	public class LocalizzazioniService : Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.ILocalizzazioniService
	{
        IAliasResolver _aliasResolver;
		IStradarioRepository _stradarioRepository;
		ISalvataggioDomandaStrategy _persistenzaStrategy;

        public LocalizzazioniService(IStradarioRepository stradarioRepository, ISalvataggioDomandaStrategy persistenzaStrategy, IAliasResolver aliasResolver)
		{
			this._stradarioRepository = stradarioRepository;
			this._persistenzaStrategy = persistenzaStrategy;
            this._aliasResolver = aliasResolver;
		}

		public void EliminaLocalizzazione(int idDomanda, int idLocalizzazione)
		{
			var domanda = _persistenzaStrategy.GetById(idDomanda);

			domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(idLocalizzazione);

			_persistenzaStrategy.Salva(domanda);
		}

        public void EliminaLocalizzazioni(int idDomanda)
        {
            var domanda = _persistenzaStrategy.GetById(idDomanda);

            domanda.ReadInterface.Localizzazioni.Indirizzi
                .Select(x => x.Id)
                .ToList()
                .ForEach(x => domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(x));

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

        public StradarioDto GetById(int id)
        {
            var stradario = this._stradarioRepository.GetByCodiceStradario(this._aliasResolver.AliasComune, id);

            if (stradario == null)
            {
                return null;
            }

            return new StradarioDto
            {
                CodiceStradario = Convert.ToInt32(stradario.CODICESTRADARIO),
                CodViario = stradario.CODVIARIO,
                NomeVia = (stradario.PREFISSO + " " + stradario.DESCRIZIONE).Trim()
            };
        }

        public StradarioDto GetIndirizzoByCodViario(string codViario)
        {
            return this._stradarioRepository.GetByCodViario(this._aliasResolver.AliasComune, codViario);
        }
	}
}
