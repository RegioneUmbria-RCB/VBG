using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaLocalizzazioniAdapter : ICopiaDomandaDatiAdapter
    {
        public CopiaDomandaLocalizzazioniAdapter()
        {

        }

        public void Adatta(Istanze istanzaTemplate, DomandaOnline domanda)
        {
            foreach (IstanzeStradario localizzazione in istanzaTemplate.Stradario)
            {
                var nuovaLocalizzazione = localizzazione.ToNuovaLocalizzazione();
                var mappali = istanzaTemplate.Mappali.Where(x => x.FkIdIstanzeStradario.Value == Convert.ToInt32(localizzazione.ID));

                if( mappali != null && mappali.Count() > 0 )
                {
                    foreach (var mappale in mappali)
                    {
                        domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(nuovaLocalizzazione, mappale.ToNuovoRiferimentoCatastale());
                    }
                } else
                {
                    domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazione(nuovaLocalizzazione);
                }

                SetNuovoUUIDLocalizzazioneSuDatiDinamici(istanzaTemplate, localizzazione.Uuid, domanda.ReadInterface.Localizzazioni.Indirizzi.Last().Uuid);
            }
        }

        internal void SetNuovoUUIDLocalizzazioneSuDatiDinamici(Istanze istanzaTemplate, string vecchioUuidStradario, string nuovoUuidStradario)
        {
            istanzaTemplate.IstanzeDyn2Dati.Where(x => x.Valore == vecchioUuidStradario).ToList().ForEach(
                x => {
                    x.Valore = nuovoUuidStradario;
                    x.Valoredecodificato = nuovoUuidStradario;
                    }
                );
        }
    }
}
