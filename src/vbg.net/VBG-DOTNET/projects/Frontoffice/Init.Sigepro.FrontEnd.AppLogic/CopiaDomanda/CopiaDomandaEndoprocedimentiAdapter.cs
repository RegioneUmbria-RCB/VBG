using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.CopiaDomanda
{
    public class CopiaDomandaEndoprocedimentiAdapter : ICopiaDomandaDatiAdapter
    {
        private readonly IEndoAcquisitiService _endoAcquisitiService;
        private readonly IEndoprocedimentiService _endoprocedimentiService;

        public CopiaDomandaEndoprocedimentiAdapter(IEndoprocedimentiService endoprocedimentiService, IEndoAcquisitiService endoAcquisitiService)
        {
            this._endoprocedimentiService = endoprocedimentiService;
            _endoAcquisitiService = endoAcquisitiService;
        }

        public void Adatta(Istanze istanzaTemplate, DomandaOnline domanda)
        {
            var id = istanzaTemplate.EndoProcedimenti.Select(x => new SubEndoprocedimentoSelezionato(Convert.ToInt32(x.CODICEINVENTARIO)));
            domanda.WriteInterface.Endoprocedimenti.AggiungiESincronizza(id, new LogicaSincronizzazioneSubEndo(domanda, this._endoprocedimentiService, this._endoAcquisitiService));
        }
    }
}
