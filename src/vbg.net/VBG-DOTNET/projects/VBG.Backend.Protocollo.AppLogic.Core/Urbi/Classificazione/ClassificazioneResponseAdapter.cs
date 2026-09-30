using log4net;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Classificazione
{
    public class ClassificazioneResponseAdapter
    {
        private readonly ILog _logger;

        public ClassificazioneResponseAdapter(ILog logger)
        {
            this._logger = logger;
        }

        public ListaTipiClassificaType Adatta(xapirestTypeTitolario response, string replaceTitolario)
        {
            if (response.getElencoTitolario_Result.RESULT == "N")
            {
                this._logger.Error($"Errore durante il recupero delle classifiche: {response.getElencoTitolario_Result.MESSAGE}");
                return new ListaTipiClassificaType();
            }


            return new ListaTipiClassificaType
            {
                Classifica = response.getElencoTitolario_Result.SEQ_Titolario
                                        .Where(k => !String.IsNullOrEmpty(k.CodiceRicerca))
                                        .OrderBy(y => y.CodiceRicerca)
                                        .Select(x =>
                                        {
                                            var arrDescrizione = x.Descrizione.Split('/');
                                            var r = new ListaTipiClassificaClassifica
                                            {
                                                Codice = x.Codice,
                                                Descrizione = String.Format("[{0}] {1}", x.CodiceRicerca.Trim(), arrDescrizione[arrDescrizione.Length - 1])
                                            };
                                            return r;
                                        }).ToArray()
            };
        }
    }
}