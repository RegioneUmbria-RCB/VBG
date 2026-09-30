using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Manager.WsPosizioniDebitorie;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    public class PosizioniDebitorieService : WebServiceProxyBase<PosizionidebitorieClient>, IPosizioniDebitorieService
    {
        private readonly IResolveToken _resolveToken;
        private readonly ILog _log = LogManager.GetLogger(typeof(PosizioniDebitorieService));

        protected override string WebServiceUrl => ParametriConfigurazione.Get.WsPosizioniDebitorieServiceUrl;

        public PosizioniDebitorieService(IResolveToken resolveToken)
            : base(StaticKernelContainer.GetService<IBindingFactory>())
        {
            this._resolveToken = resolveToken;
        }

        protected override PosizionidebitorieClient CreateService(BasicHttpBinding binding, EndpointAddress endpoint)
        {
            return new PosizionidebitorieClient(binding, endpoint);
        }

        public EsitoInserimentoPosizioneDebitoriaSingola InserisciPosizioneDebitoria(InserisciPosizioneDebitoriaDaOnere request)
        {
            return this.CallService<EsitoInserimentoPosizioneDebitoriaSingola>((ws) =>
            {
                var result = ws.InserisciPosizioneDaOnere(new InserisciPosizioneDaOnereRequest
                {
                    token = this._resolveToken.Token,
                    riferimentoOnere = request.OnereId.ToString()
                });

                if (result?.esito.esito != 0)
                {
                    var errore = new ErroreCreazionePosizioneDebitoriaSingola(result.esito.listaErrori);
                    // Loggare gli errori
                    this._log.Error($"Non è stato possibile generare una posizione debitoria per l'onere {request.OnereId} " +
                       $"per i seguenti errori: {String.Join(" -- ", errore.Errori.ToArray())}");

                    return errore;
                }

                return new RiferimentoPosizioneDebitoriaSingola(result.idDettaglioPosizioneDebitoria);
            });
        }

        public EsitoInserimentoPosizioneDebitoriaRateizzata InserisciPosizioneDebitoriaRateizzata(IEnumerable<InserisciPosizioneDebitoriaDaOnere> request)
        {
            return this.CallService<EsitoInserimentoPosizioneDebitoriaRateizzata>((ws) =>
            {
                var listaOneriId = request.Select(x => x.OnereId.ToString()).ToArray();

                var result = ws.InserisciPosizioneDaOnereRateizzato(new InserisciPosizioneDaOnereRateizzatoRequest
                {
                    token = this._resolveToken.Token,
                    riferimentoOnere = listaOneriId
                });

                if (result?.esito.esito != 0)
                {
                    var errore = new ErroreCreazionePosizioneDebitoriaRateizzata(result.esito.listaErrori);
                    // Loggare gli errori
                    this._log.Error($"Non è stato possibile generare una posizione debitoria per gli oneri {String.Format(", ", listaOneriId)} " +
                       $"per i seguenti errori: {String.Join(" -- ", errore.Errori.ToArray())}");

                    return errore;
                }

                return new RiferimentoPosizioneDebitoriaRateizzata(result.idDettaglioPosizioneDebitoria);
            });
        }
    }
}
