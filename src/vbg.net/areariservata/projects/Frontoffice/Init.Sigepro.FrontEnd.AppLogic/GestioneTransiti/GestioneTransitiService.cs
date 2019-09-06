using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public class GestioneTransitiService : IGestioneTransitiService
    {
        private static class Constants
        {
            public const string NomeChiaveDatiRicercati = "GestioneTransiti.DatiUltimaRicerca";
        }

        private readonly IAutorizzazioniTransitiProxy _wsProxy;
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;

        public GestioneTransitiService(IAutorizzazioniTransitiProxy wsProxy, ISalvataggioDomandaStrategy salvataggioStrategy)
        {
            this._wsProxy = wsProxy;
            this._salvataggioStrategy = salvataggioStrategy;
        }

        public RiferimentiPraticaCercata GetRiferimentiPRaticaCercata(int idDomanda)
        {
            var domanda = _salvataggioStrategy.GetById(idDomanda);

            return domanda.ReadInterface.DatiExtra.Get<RiferimentiPraticaCercata>(Constants.NomeChiaveDatiRicercati);
        }

        public void SalvaDatiAutorizzazioneTrovata(int idDomanda, DatiAutorizzazioneTrovata datiAutorizzazione)
        {
            var domanda = _salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.DatiExtra.Set(Constants.NomeChiaveDatiRicercati, datiAutorizzazione.DatiRicerca);
            domanda.WriteInterface.AltriDati.ImpostaIdDomandaCollegata(datiAutorizzazione.IdPraticaRiferimento);

            var nomeCampo = "CODICE_ISTANZA_COLLEGATA"; //TODO: Implementare metodo per risolvere il nome di un campo dall'id
            var codiceIstanza = datiAutorizzazione.IdPraticaRiferimento.ToString();

            domanda.WriteInterface.DatiDinamici.AggiornaOCrea(datiAutorizzazione.IdCampoDinamico, 0, 0,codiceIstanza, codiceIstanza, nomeCampo);

            this._salvataggioStrategy.Salva(domanda);
        }
        
        public AutorizzazioneTransito TrovaAutorizzazione(string codiceFiscale, string partitaIva, string numeroAutorizzazione, DateTime dataAutorizzazione)
        {
            return this._wsProxy.TrovaAutorizzazione(codiceFiscale, partitaIva, numeroAutorizzazione, dataAutorizzazione);
        }
    }
}
