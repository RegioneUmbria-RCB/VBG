using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.SIGePro.Manager.DTO.Visura.V2;
using log4net;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza
{
    public interface IIstanzePresentateRepository
    {
        List<FoVisuraCampiDto> GetFiltri(string alias, string software, TipoContestoVisuraEnum contestoVisura);
        RichiestaPraticheListaResponse GetListaPratiche(string idComune, string software, RichiestaPraticheListaRequest richiesta);
        RichiestaPraticaResponse GetDettaglioPratica(string alias, string software, string codiceIstanza);
        BinaryFile GetDocumentoPratica(string aliasComune, string software, string codiceOggetto);
    }

    internal class WsIstanzePresentateRepository : IIstanzePresentateRepository
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsIstanzePresentateRepository));
        private readonly IStcService _stcService;
        private readonly CampiRicercaPraticheServiceCreator _serviceCreator;

        public WsIstanzePresentateRepository(IStcService stcService, CampiRicercaPraticheServiceCreator serviceCreator)
        {
            this._stcService = stcService;
            this._serviceCreator = serviceCreator;
        }


        public List<FoVisuraCampiDto> GetFiltri(string alias, string software, TipoContestoVisuraEnum contestoVisura)
        {
            return this._serviceCreator.Call(ws =>
            {
                FoVisuraCampiDto[] lista = null;

                switch (contestoVisura)
                {
                    case TipoContestoVisuraEnum.FiltriVisura:
                        lista = ws.Service.GetFiltriVisuraV2(ws.Token, software);
                        break;
                    case TipoContestoVisuraEnum.ListaVisura:
                        lista = ws.Service.GetCampiListaVisuraV2(ws.Token, software);
                        break;
                    case TipoContestoVisuraEnum.FiltriArchivio:
                        lista = ws.Service.GetFiltriArchivioV2(ws.Token, software);
                        break;
                    case TipoContestoVisuraEnum.ListaArchivio:
                        lista = ws.Service.GetCampiListaArchivioV2(ws.Token, software);
                        break;
                }

                return new List<FoVisuraCampiDto>(lista);
            });

        }

        public RichiestaPraticheListaResponse GetListaPratiche(string idComune, string software, RichiestaPraticheListaRequest richiesta)
        {
            try
            {
                return this._stcService.RichiestaPraticheLista(richiesta);

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'invocazione del servizio di visura STC (GetListaPraticheV2). Dettagli dell'errore:r\n{0} ", ex.ToString());
                throw;
            }

        }

        public RichiestaPraticaResponse GetDettaglioPratica(string alias, string software, string codiceIstanza)
        {
            try
            {
                this._log.DebugFormat("Invocazione di STC (GetDettaglioPratica) con alias={0} e id={1}", alias, codiceIstanza);

                return this._stcService.RichiestaPratica(codiceIstanza);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore durante l'invocazione del servizio di visura STC (GetDettaglioPratica): {ex}");
                throw;
            }


        }

        public BinaryFile GetDocumentoPratica(string aliasComune, string software, string codiceOggetto)
        {
            try
            {
                var allegatoStc = this._stcService.AllegatoBinario(codiceOggetto);

                return new BinaryFile(allegatoStc.fileName, allegatoStc.mimeType, allegatoStc.binaryData);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat($"Errore in GetDocumentoPratica durante la chiamata ad stc: {ex}");

                throw;
            }
        }

    }
}
