using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;
using Init.Utils;
using log4net;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
    public class SigeproSitService : ISitService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SigeproSitService));
        private readonly SitServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _aliasSoftwareResolver;
        private CaratteristicheSit _features;

        public SigeproSitService(ISoftwareResolver aliasSoftwareResolver, SitServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public EsitoValidazioneSit ValidaCampo(string nomeCampo, string codiceStradario, string civico, string esponente, string circoscrizione, string cap)
        {
            return this._serviceCreator.Call(ws =>
            {
                var dataSit = new Sit
                {
                    /*IdComune = this._aliasSoftwareResolver.AliasComune,*/
                    CodVia = codiceStradario,
                    Civico = civico,
                    Esponente = esponente,
                    Circoscrizione = circoscrizione,
                    CAP = cap
                };

                var result = ws.Service.ValidateField(ws.Token, nomeCampo, dataSit, this._aliasSoftwareResolver.Software);

                if (!String.IsNullOrEmpty(result.Message))
                {
                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (ValidaCampo): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(dataSit));
                    return null;
                }

                return EsitoValidazioneSit.FromSitClass(result.DataSit);
            });
        }

        public string[] GetListaCampi(string nomeCampo, string codiceStradario, string civico, string esponente, string circoscrizione, string cap)
        {
            return this._serviceCreator.Call(ws =>
            {
                var dataSit = new Sit
                {
                    /*IdComune = this._aliasSoftwareResolver.AliasComune,*/
                    CodVia = codiceStradario,
                    Civico = civico,
                    Esponente = esponente,
                    Circoscrizione = circoscrizione,
                    CAP = cap
                };

                var result = ws.Service.GetListField(ws.Token, nomeCampo, dataSit, this._aliasSoftwareResolver.Software);

                if (!String.IsNullOrEmpty(result.Message))
                {
                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (GetListaCampi): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(dataSit));
                    return null;
                }

                return result.Field;
            });
        }

        public CaratteristicheSit GetFeatures()
        {
            if (this._features != null)
                return this._features;

            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    var features = ws.Service.GetFeatures(ws.Token, this._aliasSoftwareResolver.Software);

                    this._features = new CaratteristicheSit(features.CampiGestiti, features.VisualizzazioniFrontoffice);

                    return this._features;
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (GetCampiSupportati): {0}", ex.ToString());

                    throw;
                }
            });
        }

        public EsitoValidazioneSit ValidaCampo(string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    var result = ws.Service.ValidateField(ws.Token, nomeCampo, parametriRicerca.ToSit(), this._aliasSoftwareResolver.Software);

                    if (String.IsNullOrEmpty(result.Message))
                        return EsitoValidazioneSit.FromSitClass(result.DataSit);


                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (ValidaCampo): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(parametriRicerca.ToSit()));
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (ValidaCampo): {0}, parametri: {1}", ex.ToString(), StreamUtils.SerializeClass(parametriRicerca.ToSit()));
                }

                return null;
            });
        }

        public string[] RicercaValori(string nomeCampo, IParametriRicercaLocalizzazione parametriRicerca)
        {
            return this._serviceCreator.Call(ws =>
            {
                try
                {
                    var result = ws.Service.GetListField(ws.Token, nomeCampo, parametriRicerca.ToSit(), this._aliasSoftwareResolver.Software);

                    if (String.IsNullOrEmpty(result.Message))
                        return result.Field;

                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (GetListaCampi): {0}, parametri: {1}", result.Message, StreamUtils.SerializeClass(parametriRicerca.ToSit()));
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();

                    this._log.ErrorFormat("Errore durante l'interrogazione del sit (GetListaCampi): {0}, parametri: {1}", ex.ToString(), StreamUtils.SerializeClass(parametriRicerca.ToSit()));
                }

                return null;
            });
        }
    }
}
