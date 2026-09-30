using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.Ravenna2;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;


namespace VBG.Backend.SIT.AppLogic
{
    [SitImplementation("SIT_RAVENNA2")]
    public class SitRavenna2 : SitBaseV2
    {
        private static class Constants
        {
            public const string ErroreLocalizzazioneNonUnivoca = "I dati immessi sono corretti ma non permettono di identificare univocamente una localizzazione. Immettere un numero maggiore di filtri e riprovare.";
        }

        private Ravenna3DbClient _dbClient;
        private string _urlZoomDaCivico;
        private string _urlZoomDaMappale;
        private readonly ILog _log = LogManager.GetLogger(typeof(SitRavenna2));


        public SitRavenna2()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var v = verticalizzazioniFactory.Create<VerticalizzazioneSitRavenna2>(this.Alias, this.Software);

            if (!v.Attiva)
            {
                throw new Exception(String.Format("La verticalizzazione " + VerticalizzazioneSitRavenna2.Constants.NomeVerticalizzazione + " non è attiva per l'alias {0} e il software {1}", this.Alias, this.Software));
            }

            this._dbClient = new Ravenna3DbClient(v);
            this._urlZoomDaCivico = v.UrlCartografiaDaCivico;
            this._urlZoomDaMappale = v.UrlCartografiaDaMappale;
        }

        public override Data.RetSit ElencoCivici()
        {
            var codVia = this.DataSit.CodVia;

            if (String.IsNullOrEmpty(codVia))
            {
                throw new Exception("Dati non sufficienti per enumerare i civici. Selezionare almeno una via");
            }

            var resultSet = this._dbClient.GetListaCivici(codVia);

            return resultSet.ToRetSit();
        }

        public override Data.RetSit ElencoEsponenti()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                throw new Exception("Dati non sufficienti per enumerare gli esponenti. Selezionare almeno una via e un civico");
            }

            var resultSet = this._dbClient.GetListaEsponenti(codVia, civico);

            return resultSet.ToRetSit();
        }

        public override Data.RetSit ElencoSezioni()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            var resultSet = this._dbClient.GetListaSezioni(codVia, civico, esponente);

            return resultSet.ToRetSit();
        }

        public override Data.RetSit ElencoFogli()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var sezione = this.DataSit.Sezione;

            if (String.IsNullOrEmpty(sezione))
            {
                throw new Exception("Dati non sufficienti per enumerare i fogli. Selezionare almeno una sezione");
            }

            var resultSet = this._dbClient.GetListaFogli(codVia, civico, esponente, sezione);

            return resultSet.ToRetSit();
        }

        public override Data.RetSit ElencoParticelle()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;

            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio))
            {
                throw new Exception("Dati non sufficienti per enumerare le particelle. Selezionare almeno una sezione e un foglio");
            }

            var resultSet = this._dbClient.GetListaParticelle(codVia, civico, esponente, sezione, foglio);

            return resultSet.ToRetSit();
        }

        public override DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            this._log.Debug($"Ricezione chiamata GetListaVie({filtro},[{codiciComuni}])");
            var listaVie = this._dbClient.GetListaVie();
            this._log.Debug($"Fine chiamata GetListaVie({filtro},[{codiciComuni}]) => {listaVie?.Length} vie trovate");
            return listaVie;
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            var result = this._dbClient.GetCivico(codVia, civico, esponente);

            if (result.PiuDiUnElementoTrovato)
            {
                this._log.DebugFormat($"Validazione del civico fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
            }

            if (!result.ElementoTrovato)
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, "Il civico inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            this.DataSit.ExtendWith(result);

            return new RetSit(true);
        }

        public override RetSit EsponenteValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            var result = this._dbClient.GetEsponente(codVia, civico, esponente);

            if (result.PiuDiUnElementoTrovato)
            {
                this._log.DebugFormat($"Validazione dell'esponente fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
            }

            if (!result.ElementoTrovato)
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "L'esponente inserito non è valido", false);
            }

            this.DataSit.ExtendWith(result);

            return new RetSit(true);
        }

        public override RetSit SezioneValidazione()
        {
            var sezione = this.DataSit.Sezione;

            var result = this._dbClient.GetSezione(sezione);

            if (result.PiuDiUnElementoTrovato)
            {
                this._log.DebugFormat($"Validazione della sezione fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
            }

            if (!result.ElementoTrovato)
            {
                return RetSit.Errore(MessageCode.SezioneValidazione, "La sezione inserita non è valida", false);
            }

            this.DataSit.ExtendWith(result);

            return new RetSit(true);
        }

        public override RetSit FoglioValidazione()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;

            var result = this._dbClient.GetFoglio(sezione, foglio);

            if (result.PiuDiUnElementoTrovato)
            {
                this._log.DebugFormat($"Validazione del foglio fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
                return new RetSit(
                   result.ElementoTrovato,
                  MessageCode.FoglioValidazione,
                 "Validazione del foglio fallita: " + Constants.ErroreLocalizzazioneNonUnivoca);
            }

            if (!result.ElementoTrovato)
            {
                this._log.DebugFormat($"Validazione del foglio fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
                return new RetSit(
                  result.ElementoTrovato,
                 MessageCode.FoglioValidazione,
                "Validazione del foglio fallita: Nessun Elemento Trovato");
            }

            this.DataSit.ExtendWith(result);

            return new RetSit(true);
        }

        public override RetSit ParticellaValidazione()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            var result = this._dbClient.GetParticella(codVia, civico, esponente, sezione, foglio, particella);

            if (result.PiuDiUnElementoTrovato)
            {
                this._log.DebugFormat($"Validazione della particella fallita: {Constants.ErroreLocalizzazioneNonUnivoca}");
            }

            if (!result.ElementoTrovato)
            {
                return RetSit.Errore(MessageCode.ParticellaValidazione, "La particella inserita non è valida", false);
            }

            this.DataSit.ExtendWith(result);

            return new RetSit(true);
        }

        public override RetSit ElencoFrazioni()
        {
            return RetSit.Errore(MessageCode.ElencoFrazioni, "Il SIT in uso non supporta la ricerca per frazioni", false);
        }

        public override RetSit FrazioneValidazione()
        {
            return new RetSit(true);
        }

        public override RetSit ElencoSub()
        {
            return RetSit.Errore(MessageCode.ElencoSub, "Il SIT in uso non supporta la ricerca per subalterni", false);
        }

        public override RetSit SubValidazione()
        {
            return new RetSit(true);
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            var l = new List<BaseDto<SitFeatures.TipoVisualizzazione, string>>();

            if (!String.IsNullOrEmpty(this._urlZoomDaCivico))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaCivico));
            }

            if (!String.IsNullOrEmpty(this._urlZoomDaMappale))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaMappale));
            }

            return l.ToArray();
        }

        public override string[] GetListaCampiGestiti()
        {
            return new[]{
                SitIntegrationService.NomiCampiSit.CodiceCivico,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
            };
        }
    }
}
