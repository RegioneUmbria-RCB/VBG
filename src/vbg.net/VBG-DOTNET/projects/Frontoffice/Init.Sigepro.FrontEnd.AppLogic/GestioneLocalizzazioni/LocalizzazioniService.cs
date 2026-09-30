using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public class LocalizzazioniService : Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni.ILocalizzazioniService
    {
        private readonly IAliasResolver _aliasResolver;
        private readonly IStradarioRepository _stradarioRepository;
        private readonly ISalvataggioDomandaStrategy _persistenzaStrategy;

        public LocalizzazioniService(IStradarioRepository stradarioRepository, ISalvataggioDomandaStrategy persistenzaStrategy, IAliasResolver aliasResolver)
        {
            this._stradarioRepository = stradarioRepository;
            this._persistenzaStrategy = persistenzaStrategy;
            this._aliasResolver = aliasResolver;
        }

        public void EliminaLocalizzazione(int idDomanda, int idLocalizzazione)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(idLocalizzazione);

            this._persistenzaStrategy.Salva(domanda);
        }

        public void EliminaLocalizzazioni(int idDomanda)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            this.EliminaLocalizzazioni(domanda);

            this._persistenzaStrategy.Salva(domanda);
        }

        public void EliminaLocalizzazioni(DomandaOnline domanda)
        {
            domanda.ReadInterface.Localizzazioni.Indirizzi
                .Select(x => x.Id)
                .ToList()
                .ForEach(x => domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(x));
        }


        public void AggiungiLocalizzazione(int idDomanda, NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentiCatastali = null)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(localizzazione, riferimentiCatastali);

            this._persistenzaStrategy.Salva(domanda);
        }

        public IEnumerable<StradarioDto> FindByMatchParziale(string aliasComune, string codiceComune, string comuneLocalizzazione, string indirizzo)
        {
            return this._stradarioRepository.GetByMatchParziale(aliasComune, codiceComune, comuneLocalizzazione, indirizzo);
        }

        public void AssegnaRiferimentiCatastaliALocalizzazione(int idDomanda, int idLocalizzazione, NuovoRiferimentoCatastale riferimentiCatastali)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Localizzazioni.AssegnaRiferimentiCatastaliALocalizzazione(idLocalizzazione, riferimentiCatastali);

            this._persistenzaStrategy.Salva(domanda);
        }

        public void EliminaRiferimentiCatastali(int idDomanda, int idRiferimentoCatastale)
        {
            var domanda = this._persistenzaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Localizzazioni.EliminaRiferimentoCatastale(idRiferimentoCatastale);

            this._persistenzaStrategy.Salva(domanda);
        }

        public virtual StradarioDto GetById(int id)
        {
            var stradario = this._stradarioRepository.GetByCodiceStradario(this._aliasResolver.AliasComune, id);

            if (stradario == null)
            {
                return null;
            }

            return new StradarioDto
            {
                CodiceStradario = stradario.CodiceStradario,
                CodViario = stradario.CodViario,
                NomeVia = (stradario.Prefisso + " " + stradario.Descrizione).Trim()
            };
        }

        public StradarioDto GetIndirizzoByCodViario(string codViario)
        {
            return this._stradarioRepository.GetByCodViario(this._aliasResolver.AliasComune, codViario);
        }
    }
}
