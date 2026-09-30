using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
//using System.Configuration;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.ItCity;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;


namespace VBG.Backend.SIT.AppLogic
{
    [SitImplementation("SIT_ITCITY")]
    public class SitItCity : SitBaseV2
    {
        private VerticalizzazioneSitItCity _vert = null;

        public SitItCity() : base(new NullValidazioneFormaleService())
        {
        }

        public override string[] GetListaCampiGestiti()
        {
            return new[]{
                SitIntegrationService.NomiCampiSit.CodiceCivico,
                SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Sub,
                SitIntegrationService.NomiCampiSit.Quartiere,
                SitIntegrationService.NomiCampiSit.Cap
            };
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._vert = verticalizzazioniFactory.Create<VerticalizzazioneSitItCity>(this.Alias, this.Software);

            if (!this._vert.Attiva)
            {
                throw new Exception("La verticalizzazione SIT_ITCITY non è attiva");
            }
        }

        public override RetSit ElencoCivici()
        {
            var codVia = this.DataSit.CodVia;

            if (String.IsNullOrEmpty(codVia))
            {
                return RetSit.Errore(MessageCode.ElencoCivici, "Dati non sufficienti per enumerare i civici. Selezionare almeno una via", false);
            }

            var srv = new ServiceWrapper<CiviciJSON>(this._vert.UrlServizioCivici, this._vert.Username, this._vert.Password);
            var response = srv.GetCivici(codVia);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "NOTFOUND",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Numero)).Select(x => x.Numero).Distinct().OrderBy(x => Convert.ToInt32(x)).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(this.DataSit.Civico))
            {
                return new RetSit(true);
            }

            var srv = new ServiceWrapper<CiviciJSON>(this._vert.UrlServizioCivici, this._vert.Username, this._vert.Password);
            var response = srv.VerificaCivico(codVia, civico);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, response.MessaggioErrore, false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoEsponenti()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.ElencoEsponenti, "Dati non sufficienti per recuperare gli esponenti. Selezionare una via e un civico", false);
            }

            var srv = new ServiceWrapper<CiviciJSON>(this._vert.UrlServizioCivici, this._vert.Username, this._vert.Password);
            var response = srv.GetEsponenti(codVia, civico);

            return new RetSit
            {
                ReturnValue = response.Esito,
                MessageCode = "NOTFOUND",
                Message = response.MessaggioErrore,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Sub)).Select(x => x.Sub).Distinct().OrderBy(x => x).ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }

        public override RetSit EsponenteValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            if (String.IsNullOrEmpty(this.DataSit.Esponente))
            {
                return new RetSit(true);
            }

            var srv = new ServiceWrapper<CiviciJSON>(this._vert.UrlServizioCivici, this._vert.Username, this._vert.Password);
            var response = srv.VerificaEsponente(codVia, civico, esponente);

            if (!response.Esito)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, response.MessaggioErrore, false);
            }

            return new RetSit
            {
                ReturnValue = true,
                DataCollection = response.Dati.Where(x => !String.IsNullOrEmpty(x.Sub) && x.Sub.ToLower() == esponente.ToLower()).Select(x => x.Sub).Distinct().ToList(),
                DataMap = new Dictionary<string, string>()
            };
        }
    }
}
