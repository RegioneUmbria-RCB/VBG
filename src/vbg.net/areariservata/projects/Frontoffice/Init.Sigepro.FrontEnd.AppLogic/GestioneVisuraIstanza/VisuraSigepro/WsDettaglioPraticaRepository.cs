using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.VisuraSigepro
{
    internal class WsDettaglioPraticaRepository// : IDettaglioPraticaRepository
    {
        ILog _log = LogManager.GetLogger(typeof(WsDettaglioPraticaRepository));
        IConfigurazioneVbgRepository _configurazioneVbgRepository;
        IstanzeServiceCreator _istanzeServiceCreator;

        internal WsDettaglioPraticaRepository(IConfigurazioneVbgRepository configurazioneVbgRepository, IstanzeServiceCreator istanzeServiceCreator)
        {
            this._configurazioneVbgRepository = configurazioneVbgRepository;
            this._istanzeServiceCreator = istanzeServiceCreator;
        }

        public Istanze GetByUuid(string uuid)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDettaglioPraticaByUuid(ws.Token, uuid);
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public RisultatoVisuraListaDto GetListaPratiche(RichiestaListaPratiche richiesta)
        {
            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetListaPratiche(ws.Token, richiesta);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durente la chiamata a GetListaPratiche: {ex.ToString()}");

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        public Istanze GetById(int idPratica, bool leggiDatiConfigurazione)
        {
            _log.Debug("Invocazione del web service di visura, lettura dettaglio");

            using (var ws = _istanzeServiceCreator.CreateClient())
            {
                try
                {
                    _log.DebugFormat("Url del web service di visura: {0}", ws.Service.Endpoint.Address);
                    _log.DebugFormat("Id pratica = {0}, leggiDatiConfigurazione = {1}", idPratica, leggiDatiConfigurazione);

                    var istanza = ws.Service.GetDettaglioPratica(ws.Token, idPratica);

                    if (istanza == null)
                        return null;

                    if (istanza.Movimenti != null && istanza.Movimenti.Length > 0)
                    {
                        // Rimuovo dalla lista i movimenti che non hanno una data

                        var movimentiDaTenere = new List<Movimenti>();

                        foreach (var movimento in istanza.Movimenti)
                        {
                            if (movimento.DATA.HasValue)
                                movimentiDaTenere.Add(movimento);
                        }

                        movimentiDaTenere.Sort((a, b) =>
                        {
                            if (!a.DATA.HasValue && !b.DATA.HasValue)
                                return 0;

                            if (a.DATA.HasValue && !b.DATA.HasValue)
                                return -1;

                            if (!a.DATA.HasValue && b.DATA.HasValue)
                                return 1;

                            var id1 = a.DATA.Value.ToString("yyyyMMdd") + "-" + a.CODICEMOVIMENTO.PadLeft(10, '0');
                            var id2 = b.DATA.Value.ToString("yyyyMMdd") + "-" + b.CODICEMOVIMENTO.PadLeft(10, '0');

                            return id1.CompareTo(id2);
                        });

                        istanza.Movimenti = movimentiDaTenere.ToArray();
                    }

                    if (leggiDatiConfigurazione)
                        istanza.ConfigurazioneComune = _configurazioneVbgRepository.LeggiConfigurazioneComune(istanza.SOFTWARE);

                    return istanza;
                }
                catch (Exception ex)
                {
                    _log.ErrorFormat("WsDettaglioPraticaRepository.getById: Errore durante l'invocazione del web service: {0}, id pratica = {1} , leggiDatiConfigurazione = {2}", ex, idPratica, leggiDatiConfigurazione);

                    ws.Service.Abort();

                    throw;
                }
            }

        }
    }
}
