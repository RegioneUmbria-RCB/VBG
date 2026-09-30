using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione
{
    public class LogicaSincronizzazioneSubEndo : LogicaSincronizzazioneEndo
    {
        private readonly DomandaOnline _domanda;
        internal LogicaSincronizzazioneSubEndo(DomandaOnline domanda, IEndoprocedimentiService endoprocedimentiService, IEndoAcquisitiService endoAcquisitiService) : base(domanda, endoprocedimentiService, endoAcquisitiService)
        {
            this._domanda = domanda;
        }

        internal void Sincronizza(IEnumerable<SubEndoprocedimentoSelezionato> subEndoSelezionati)
        {
            var listaId = subEndoSelezionati.Select(x => x.Id).Distinct();

            base.Sincronizza(listaId);

            this._domanda.WriteInterface.DatiExtra.Set(EndoprocedimentiService.Constants.StrutturaSubEndoKey, new StrutturaSubEndo(subEndoSelezionati));
        }
    }
}
