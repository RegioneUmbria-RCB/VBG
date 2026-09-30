using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.StcServiceReference;
using SIGePro.Manager.Verticalizzazioni;
using log4net;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Stc
{
    public class StcProxy
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(StcProxy));
        private readonly VerticalizzazioneStc _verticalizzazione;
        private readonly StcToken _tokenPicker;
        private readonly string _idEnte;
        private readonly string _idSportello;
        private readonly IBindingFactory _bindingFactory;

        public StcProxy(VerticalizzazioneStc verticalizzazione, string idEnte, string idSportello)
        {
            this._bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
            this._verticalizzazione = verticalizzazione;
            this._tokenPicker = new StcToken(verticalizzazione, this._bindingFactory);
            this._idEnte = idEnte;
            this._idSportello = idSportello;

        }

        public string PraticaCollegata(int idPraticaOrig, int idProcedimentoOrig, int idNodoDest, string idEnteDest, string idSportelloDest)
        {
            var token = this._tokenPicker.GetToken();

            using (var ws = this.CreateClient())
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
            var binding = this._bindingFactory.CreateAndConfigure("StcBinding");
            var endpoint = new EndpointAddress(this._verticalizzazione.StcWsUrl);

            return new StcServiceReference.StcClient(binding, endpoint);
        }

    }
}
