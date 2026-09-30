using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;
using static VBG.Backend.SIT.AppLogic.Forli.ForliToponomastica;



namespace VBG.Backend.SIT.AppLogic.Forli
{
    [SitImplementation("SIT_FORLI")]
    public class SitForli : SitBaseV2
    {
        // Dichiarate protected per poter eseguire i test!
        protected ForliCatasto _catasto;
        protected ForliToponomastica _toponomastica;

        private string _urlCartografiaDaCivico;
        private string _urlCartografiaDaMappale;

        private static class ErrMessages
        {
            public const string CodVia = "Selezionare almeno una via";
            public const string Civico = "Selezionare almeno un civico";
            public const string Esponente = "Selezionare almeno una esponente";
            public const string Interno = "Selezionare almeno un interno";

            public const string Foglio = "Selezionare almeno un foglio";
            public const string Particella = "Selezionare almeno una particella";
            public const string Sub = "Selezionare almeno un subalterno";
        }

        public SitForli() : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {

        }

        public override string[] GetListaCampiGestiti()
        {
            return new[]{
                SitIntegrationService.NomiCampiSit.CodiceCivico,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
            };
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var vert = verticalizzazioniFactory.Create<VerticalizzazioneSitForli>(this.Alias, this.Software);

            if (!vert.Attiva)
            {
                throw new Exception($"La verticalizzazione {VerticalizzazioneSitForli.Constants.NomeVerticalizzazione} non è attiva");
            }

            if (String.IsNullOrEmpty(vert.ConnectionstringCatasto))
            {
                throw new ConfigurationErrorsException($"Il parametro {VerticalizzazioneSitForli.Constants.ConnectionstringCatasto} della verticalizzazione {VerticalizzazioneSitForli.Constants.NomeVerticalizzazione} non è configurato");
            }

            if (String.IsNullOrEmpty(vert.ConnectionstringToponomastica))
            {
                throw new ConfigurationErrorsException($"Il parametro {VerticalizzazioneSitForli.Constants.ConnectionstringToponomastica} della verticalizzazione {VerticalizzazioneSitForli.Constants.NomeVerticalizzazione} non è configurato");
            }

            this._catasto = new ForliCatasto(vert.ConnectionstringCatasto);
            this._toponomastica = new ForliToponomastica(vert.ConnectionstringToponomastica);
            this._urlCartografiaDaCivico = vert.UrlCartografiaDaCivico;
            this._urlCartografiaDaMappale = vert.UrlCartografiaDaMappale;
        }

        #region Toponomastica
        public override DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            return this._toponomastica.GetListaVie(filtro, codiciComuni);
        }

        public override RetSit ElencoCivici()
        {
            var codVia = this.DataSit.GetOrThrow((sit) => sit.CodVia, ErrMessages.CodVia);

            var civici = this._toponomastica.GetListaCivici(codVia);

            return new RetSit(civici.Any(), civici);
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.GetOrThrow((sit) => sit.CodVia, ErrMessages.CodVia);
            var civico = this.DataSit.GetOrThrow((sit) => sit.Civico, ErrMessages.Civico);

            var esponenti = this._toponomastica.GetListaEsponenti(codVia, civico);

            var count = esponenti.Count();

            if (count == 0)
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, "Il civico inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            if (count == 1)
            {
                var el = esponenti.First();

                this.DataSit.Esponente = el.Lettera;
                this.DataSit.Civico = el.Civico;
                this.DataSit.CodCivico = el.CivKey;
            }

            return new RetSit(true);
        }

        public override RetSit ElencoEsponenti()
        {
            var codVia = this.DataSit.GetOrThrow((sit) => sit.CodVia, ErrMessages.CodVia);
            var civico = this.DataSit.GetOrThrow((sit) => sit.Civico, ErrMessages.Civico);

            var esponenti = this._toponomastica.GetListaEsponenti(codVia, civico);

            return new RetSit(esponenti.Any(), esponenti.Select(x => x.Lettera));
        }

        public override RetSit EsponenteValidazione()
        {
            var codVia = this.DataSit.GetOrThrow((sit) => sit.CodVia, ErrMessages.CodVia);
            var civico = this.DataSit.GetOrThrow((sit) => sit.Civico, ErrMessages.Civico);
            var esponente = this.DataSit.Esponente;

            var esponenteTrovato = this._toponomastica.GetEsponente(codVia, civico, esponente);

            if (esponenteTrovato == null)
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, "L'esponente inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            this.DataSit.Civico = esponenteTrovato.Civico;
            this.DataSit.Esponente = esponenteTrovato.Lettera;
            this.DataSit.CodCivico = esponenteTrovato.CivKey;

            return new RetSit(true);
        }

        public override RetSit ElencoInterni()
        {
            var result = Enumerable.Empty<ElementoListaInterni>();
            var civKey = this.DataSit.CodCivico; // Se ho la civKey ho abbastanza dati per recuperare la lista esponenti

            if (!String.IsNullOrEmpty(civKey))
            {
                result = this._toponomastica.GetListaInterniByCivKey(civKey);
            }
            else
            {
                var codVia = this.DataSit.GetOrThrow(s => s.CodVia, ErrMessages.CodVia);
                var civico = this.DataSit.GetOrThrow(s => s.Civico, ErrMessages.Civico);
                var esponente = this.DataSit.Esponente;

                result = this._toponomastica.GetListaInterniByIndirizzo(codVia, civico, esponente);
            }

            return new RetSit(result.Any(), result.Select(x => x.Interno));
        }

        public override RetSit InternoValidazione()
        {
            var codVia = this.DataSit.GetOrThrow(s => s.CodVia, ErrMessages.CodVia);
            var civico = this.DataSit.GetOrThrow(s => s.Civico, ErrMessages.Civico);
            var esponente = this.DataSit.Esponente; // L'esponente è null se il civico non prevede esponente
            var interno = this.DataSit.GetOrThrow(s => s.Interno, ErrMessages.Interno);

            var res = this._toponomastica.GetInterno(codVia, civico, esponente, interno);

            if (res == null)
            {
                return RetSit.Errore(MessageCode.InternoValidazione, "L'interno inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            this.DataSit.Civico = res.Civico;
            this.DataSit.Esponente = res.Lettera;
            this.DataSit.CodCivico = res.CivKey;
            this.DataSit.Interno = res.Interno;

            return new RetSit(true);
        }
        #endregion

        #region Catasto
        public override RetSit ElencoFogli()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;

            var fogli = this._catasto.GetListaFogli(tipoCatasto);

            return new RetSit(fogli.Any(), fogli);
        }

        public override RetSit FoglioValidazione()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;
            var foglio = this.DataSit.GetOrThrow(x => x.Foglio, ErrMessages.Foglio);

            var res = this._catasto.ValidaFoglio(tipoCatasto, foglio);

            if (!res)
            {
                return RetSit.Errore(MessageCode.FoglioValidazione, "Il foglio inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            return new RetSit(true);
        }


        public override RetSit ElencoParticelle()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;
            var foglio = this.DataSit.GetOrThrow(x => x.Foglio, ErrMessages.Foglio);

            var fogli = this._catasto.GetListaParticelle(tipoCatasto, foglio);

            return new RetSit(fogli.Any(), fogli);
        }

        public override RetSit ParticellaValidazione()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;
            var foglio = this.DataSit.GetOrThrow(x => x.Foglio, ErrMessages.Foglio);
            var particella = this.DataSit.GetOrThrow(x => x.Particella, ErrMessages.Particella);

            var res = this._catasto.ValidaParticella(tipoCatasto, foglio, particella);

            if (!res)
            {
                return RetSit.Errore(MessageCode.FoglioValidazione, "La particella inserita non è valida oppure i dati immessi non sono sufficienti", false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoSub()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;
            var foglio = this.DataSit.GetOrThrow(x => x.Foglio, ErrMessages.Foglio);
            var particella = this.DataSit.GetOrThrow(x => x.Particella, ErrMessages.Particella);

            var fogli = this._catasto.GetListaSubalterni(tipoCatasto, foglio, particella);

            return new RetSit(fogli.Any(), fogli);
        }

        public override RetSit SubValidazione()
        {
            var tipoCatasto = this.DataSit.TipoCatasto;
            var foglio = this.DataSit.GetOrThrow(x => x.Foglio, ErrMessages.Foglio);
            var particella = this.DataSit.GetOrThrow(x => x.Particella, ErrMessages.Particella);
            var sub = this.DataSit.GetOrThrow(x => x.Sub, ErrMessages.Sub);

            var res = this._catasto.ValidaSubalterno(tipoCatasto, foglio, particella, sub);

            if (!res)
            {
                return RetSit.Errore(MessageCode.SubValidazione, "Il subalterno inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            return new RetSit(true);
        }
        #endregion

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
        {
            return base.GetVisualizzazioniBackoffice();
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            var l = new List<BaseDto<SitFeatures.TipoVisualizzazione, string>>();

            if (!String.IsNullOrEmpty(this._urlCartografiaDaCivico))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlCartografiaDaCivico));
            }

            if (!String.IsNullOrEmpty(this._urlCartografiaDaMappale))
            {
                l.Add(new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlCartografiaDaMappale));
            }

            return l.ToArray();
        }
    }
}
