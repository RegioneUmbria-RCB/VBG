using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Genova;
using VBG.Backend.SIT.AppLogic.Genova.Authentication;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic
{
    /// <summary>
    /// SIT del Comune di Genova, basato sui servizi REST di Toponomastica
    /// (endpoint georef_toponomastica, risorse rstGetElenco*/rstValida*).
    /// Gestisce esclusivamente la gerarchia toponomastica
    /// via -> civico -> esponente -> colore -> scala -> interno -> lettera interno.
    /// </summary>
    [SitImplementation("SIT_GENOVA")]
    public class SitGenova : SitBaseV2
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(SitGenova));

        private GenovaToponomasticaClient _client = null!;
        private DettagliVia[]? _vieCache;

        public SitGenova()
            : base(new ValidazioneFormaleTramiteCodViarioECivicoService())
        {
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var v = verticalizzazioniFactory.Create<VerticalizzazioneSitGenova>(this.Alias, this.Software);

            if (!v.Attiva)
            {
                throw new Exception("La verticalizzazione " + VerticalizzazioneSitGenova.Constants.NomeVerticalizzazione + $" non è attiva per l'alias {this.Alias} e il software {this.Software}");
            }

            var autenticazioneClient = new GenovaWSO2Authenticator(v);

            this._client = new GenovaToponomasticaClient(v.UrlWsToponomastica, autenticazioneClient);
        }

        public override DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            this._log.Debug($"Ricezione chiamata GetListaVie({filtro},[{codiciComuni}])");

            this._vieCache ??= this._client.GetElencoStrade()
                .Where(s => String.IsNullOrEmpty(s.DataFineValidita) || s.DataFineValidita == GenovaConstants.ValoreNonPresente)
                .Select(s => new DettagliVia
                {
                    Toponimo = s.Toponimo,
                    Denominazione = s.Descrizione,
                    CodiceViario = s.CodiceViario
                })
                .ToArray();

            this._log.Debug($"Fine chiamata GetListaVie => {this._vieCache.Length} vie trovate");

            return this._vieCache;
        }

        public override RetSit ElencoCivici()
        {
            var codVia = this.DataSit.CodVia;

            if (String.IsNullOrEmpty(codVia))
            {
                throw new Exception("Dati non sufficienti per enumerare i civici. Selezionare almeno una via");
            }

            return this._client.GetElencoCivici(codVia).ToRetSit();
        }

        public override RetSit ElencoEsponenti()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                throw new Exception("Dati non sufficienti per enumerare gli esponenti. Selezionare almeno una via e un civico");
            }

            return this._client.GetElencoEsponenti(codVia, civico).ToRetSit();
        }

        public override RetSit ElencoColori()
        {
            var (codVia, civico, esponente) = this.DatiFinoAdEsponente();

            return this._client.GetElencoColori(codVia, civico, esponente).ToRetSit();
        }

        public override RetSit ElencoScale()
        {
            var (codVia, civico, esponente, colore) = this.DatiFinoAColore();

            return this._client.GetElencoScale(codVia, civico, esponente, colore).ToRetSit();
        }

        public override RetSit ElencoInterni()
        {
            var (codVia, civico, esponente, colore, scala) = this.DatiFinoAScala();

            return this._client.GetElencoInterni(codVia, civico, esponente, colore, scala).ToRetSit();
        }

        public override RetSit ElencoEsponentiInterno()
        {
            var (codVia, civico, esponente, colore, scala) = this.DatiFinoAScala();
            var interno = this.DataSit.Interno;

            if (String.IsNullOrEmpty(interno))
            {
                throw new Exception("Dati non sufficienti per enumerare le lettere degli interni. Selezionare almeno via, civico, esponente, colore, scala e interno");
            }

            return this._client.GetElencoLettInterno(codVia, civico, esponente, colore, scala, interno).ToRetSit();
        }

        public override RetSit CivicoValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, "Il civico inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            if (!this._client.ValidaCivico(codVia, civico))
            {
                return RetSit.Errore(MessageCode.CivicoValidazione, "Il civico inserito non è valido", false);
            }

            return new RetSit(true);
        }

        public override RetSit EsponenteValidazione()
        {
            var (codVia, civico, esponente) = this.DatiFinoAdEsponente();

            if (!this._client.ValidaEsponente(codVia, civico, esponente))
            {
                return RetSit.Errore(MessageCode.EsponenteValidazione, "L'esponente inserito non è valido", false);
            }

            return new RetSit(true);
        }

        public override RetSit ColoreValidazione()
        {
            var (codVia, civico, esponente, colore) = this.DatiFinoAColore();

            if (!this._client.ValidaColore(codVia, civico, esponente, colore))
            {
                return RetSit.Errore(MessageCode.ColoreValidazione, "Il colore inserito non è valido", false);
            }

            return new RetSit(true);
        }

        public override RetSit ScalaValidazione()
        {
            var (codVia, civico, esponente, colore, scala) = this.DatiFinoAScala();

            if (!this._client.ValidaScala(codVia, civico, esponente, colore, scala))
            {
                return RetSit.Errore(MessageCode.ScalaValidazione, "La scala inserita non è valida", false);
            }

            return new RetSit(true);
        }

        public override RetSit InternoValidazione()
        {
            var (codVia, civico, esponente, colore, scala) = this.DatiFinoAScala();
            var interno = this.DataSit.Interno;

            if (String.IsNullOrEmpty(interno))
            {
                return RetSit.Errore(MessageCode.InternoValidazione, "L'interno inserito non è valido oppure i dati immessi non sono sufficienti", false);
            }

            if (!this._client.ValidaInterno(codVia, civico, esponente, colore, scala, interno))
            {
                return RetSit.Errore(MessageCode.InternoValidazione, "L'interno inserito non è valido", false);
            }

            return new RetSit(true);
        }

        public override RetSit EsponenteInternoValidazione()
        {
            var (codVia, civico, esponente, colore, scala) = this.DatiFinoAScala();
            var interno = this.DataSit.Interno;
            var esponenteInterno = this.DataSit.EsponenteInterno;

            if (String.IsNullOrEmpty(interno) || String.IsNullOrEmpty(esponenteInterno))
            {
                return RetSit.Errore(MessageCode.EsponenteInternoValidazione, "La lettera dell'interno inserita non è valida oppure i dati immessi non sono sufficienti", false);
            }

            if (!this._client.ValidaLettInterno(codVia, civico, esponente, colore, scala, interno, esponenteInterno))
            {
                return RetSit.Errore(MessageCode.EsponenteInternoValidazione, "La lettera dell'interno inserita non è valida", false);
            }

            return new RetSit(true);
        }

        public override RetSit ElencoFrazioni()
        {
            return RetSit.Errore(MessageCode.ElencoFrazioni, "Il SIT in uso non supporta la ricerca per frazioni", false);
        }

        public override RetSit ElencoSub()
        {
            return RetSit.Errore(MessageCode.ElencoSub, "Il SIT in uso non supporta la ricerca per subalterni", false);
        }

        public override RetSit ElencoSezioni()
        {
            return RetSit.Errore(MessageCode.ElencoSezioni, "Il SIT in uso non supporta la ricerca per sezioni", false);
        }

        public override string[] GetListaCampiGestiti()
        {
            return
            [
                SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Colore,
                SitIntegrationService.NomiCampiSit.Scala,
                SitIntegrationService.NomiCampiSit.Interno,
                SitIntegrationService.NomiCampiSit.EsponenteInterno,
            ];
        }

        private (string CodVia, string Civico, string Esponente) DatiFinoAdEsponente()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                throw new Exception("Dati non sufficienti. Selezionare almeno una via e un civico");
            }

            return (codVia, civico, this.DataSit.Esponente);
        }

        private (string CodVia, string Civico, string Esponente, string Colore) DatiFinoAColore()
        {
            var (codVia, civico, esponente) = this.DatiFinoAdEsponente();

            return (codVia, civico, esponente, this.DataSit.Colore);
        }

        private (string CodVia, string Civico, string Esponente, string Colore, string Scala) DatiFinoAScala()
        {
            var (codVia, civico, esponente, colore) = this.DatiFinoAColore();

            return (codVia, civico, esponente, colore, this.DataSit.Scala);
        }
    }
}
