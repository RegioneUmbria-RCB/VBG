using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Common;


namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione
{
	public class LogicaSincronizzazioneOneri : ILogicaSincronizzazioneOneri
	{
		private readonly IOneriRepository _oneriRepository;


        public LogicaSincronizzazioneOneri(IOneriRepository oneriRepository)
		{
			this._oneriRepository = oneriRepository;
            this.ComportamentoOneriSenzaImporto = ComportamentoSincronizzazioneOneriSenzaImporto.Includi;
        }

        public ComportamentoSincronizzazioneOneriSenzaImporto ComportamentoOneriSenzaImporto { get; set; }

        public void SincronizzaOneri(DomandaOnline domanda)
        {
            var readInterface = domanda.ReadInterface;
            var oneriWriteinterface = domanda.WriteInterface.Oneri;

            // TODO: Se almeno uno degli oneri è stato pagato online non è più possibile sincronizzare i valori degli oneri
            // TODO ...
            // TODO ...

            // Sincronizzo la lista degli oneri di endo e intervento
            var codiceIntervento = readInterface.AltriDati.Intervento.Codice;
            var listaEndoNonAcquisiti = readInterface.Endoprocedimenti.NonAcquisiti.Select(x => x.Codice).ToList();
            Func<Onere, bool> filtraOneriExpression = (Onere x) => this.ComportamentoOneriSenzaImporto == ComportamentoSincronizzazioneOneriSenzaImporto.Includi ||
                                                                   x.GetImporto(readInterface.DatiDinamici) > 0.0f;

            var listaOneri = this._oneriRepository
                                    .GetByIdInterventoIdEndo(codiceIntervento, listaEndoNonAcquisiti, readInterface.AltriDati.CodiceComune)
                                    .Where(filtraOneriExpression)
                                    //.Select(x => new Onere(x))
                                    .ToList();

            listaOneri.Sort((a, b) => a.Descrizione.CompareTo(b.Descrizione));

            var listaNuoviId = listaOneri.Select(x => new IdentificativoOnereSelezionato(x.Codice, x.InterventoOEndoOrigine, x.TipoOnere == Onere.TipoOnereEnum.Intervento ? "I" : "E"));

            oneriWriteinterface.EliminaOneriWhereCodiceCausaleNotIn(listaNuoviId);

            foreach (var onere in listaOneri)
            {
                var causale = new AreaRiservataService.BaseDtoOfInt32String
                {
                    Codice = onere.Codice,
                    Descrizione = onere.Descrizione
                };

                var interventoOEndoOrigine = new AreaRiservataService.BaseDtoOfInt32String
                {
                    Codice = onere.CodiceInterventoOEndoOrigine,
                    Descrizione = onere.InterventoOEndoOrigine
                };

                var provenienza = onere.TipoOnere == Onere.TipoOnereEnum.Intervento ? ProvenienzaOnere.Intervento : ProvenienzaOnere.Endo;
                var importo = onere.GetImporto(domanda.ReadInterface.DatiDinamici);
                var note = onere.Note;

                oneriWriteinterface.CreaOAggiornaDatiOnere(causale, provenienza, interventoOEndoOrigine, importo, note);
            }
        }
    }
}
