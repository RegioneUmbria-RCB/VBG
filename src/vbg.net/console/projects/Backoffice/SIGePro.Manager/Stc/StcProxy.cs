using Init.SIGePro.Manager.StcServiceReference;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Manager.Stc
{
    public class StcProxy
    {
        ILog _log = LogManager.GetLogger(typeof(StcProxy));
        VerticalizzazioneStc _verticalizzazione;
        StcToken _tokenPicker;
        string _idEnte;
        string _idSportello;

        public StcProxy(VerticalizzazioneStc verticalizzazione, string idEnte, string idSportello)
        {
            this._verticalizzazione = verticalizzazione;
            this._tokenPicker = new StcToken(verticalizzazione);
            this._idEnte = idEnte;
            this._idSportello = idSportello;
        }

        public string PraticaCollegata(int idPraticaOrig, int idProcedimentoOrig, int idNodoDest, string idEnteDest, string idSportelloDest)
        {
            var token = this._tokenPicker.GetToken();
            
            using (var ws = CreateClient())
            {
                try
                {
                    var response = ws.RichiestaPraticaCollegata(new StcServiceReference.RichiestaPraticaCollegataRequest
                    {
                        idPraticaMitt = idPraticaOrig.ToString(),
                        idProcedimentoMitt = idProcedimentoOrig.ToString(),
                        sportelloDestinatario = new SportelloType
                        {
                            idNodo = idNodoDest.ToString(),
                            idEnte = idEnteDest,
                            idSportello = idSportelloDest
                        },
                        sportelloMittente = new SportelloType
                        {
                            idNodo = this._verticalizzazione.NlaIdnodo,
                            idEnte = this._idEnte,
                            idSportello = this._idSportello
                        },
                        token = token
                    });

                    if (response.dettaglio == null || response.dettaglio.dettaglioPratica == null)
                    {
                        throw new Exception("la richiesta non ha restituito risultati");
                    }

                    return response.dettaglio.dettaglioPratica.idPratica;
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durante la chiamata a RichiestaPraticaCollegata: idPraticaOrig={idPraticaOrig}, idProcedimentoOrig={idProcedimentoOrig}, idNodoDest={idNodoDest}, idEnteDest={idEnteDest}, idSportelloDest={idSportelloDest}, errore-> {ex.ToString()}");

                    throw;
                }


            }
        }

        private StcServiceReference.StcClient CreateClient()
        {
            var binding = new BasicHttpBinding("StcBinding");
            var endpoint = new EndpointAddress(_verticalizzazione.StcWsUrl);

            return new StcServiceReference.StcClient(binding, endpoint);
        }

    }
}
