using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione
{
    public class LogicaSincronizzazioneSubEndo : LogicaSincronizzazioneEndo
    {
        DomandaOnline _domanda;
        internal LogicaSincronizzazioneSubEndo(DomandaOnline domanda, IEndoprocedimentiService endoprocedimentiService) : base(domanda, endoprocedimentiService)
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
