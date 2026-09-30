// -----------------------------------------------------------------------
// <copyright file="SitSilverBrowser.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.SIT.AppLogic.Data;
using VBG.Backend.SIT.AppLogic.Errors;
using VBG.Backend.SIT.AppLogic.Manager;
using VBG.Backend.SIT.AppLogic.SilverBrowser;
using VBG.Backend.SIT.AppLogic.SilverBrowser.SilverBrowserClasses;
using VBG.Backend.SIT.AppLogic.ValidazioneFormale;
using VBG.Backend.SIT.Verticalizzazioni;

namespace VBG.Backend.SIT.AppLogic
{
    public static class RisultatiExtensions
    {
        public static IEnumerable<string> WhereIgnoreCase(this IEnumerable<string> risultati, string partial)
        {
            if (String.IsNullOrEmpty(partial.Trim()))
                return risultati;

            return risultati.Where(x => x.ToUpperInvariant().Contains(partial.ToUpperInvariant()));
        }
    }

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    [SitImplementation("SIT_SILVERBROWSER")]
    public class SitSilverBrowser : SitBaseV2
    {
        private SilverBrowserClient _client;
        private string _urlZoomDaPunto;
        private string _urlZoomDaParticella;
        private string _urlZoomDaPuntoBo;
        private string _urlZoomDaParticellaBo;
        private readonly ILog _log = LogManager.GetLogger(typeof(SitSilverBrowser));

        public SitSilverBrowser() : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {
        }

        public override RetSit ElencoCivici()
        {
            var codiceVia = this.DataSit.CodVia;

            if (string.IsNullOrEmpty(codiceVia))
            {
                throw new ArgumentException($"{nameof(codiceVia)} non può essere vuoto");
            }

            try
            {
                var civici = this._client.ListaCivici(new CodiceViario(codiceVia)).Select(x => x.numero).WhereIgnoreCase(this.DataSit.Civico);

                return new RetSit(true, civici);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoCivici -> errore durante la lettura con i parametri codiceVia={0}: {1}", codiceVia, ex.ToString());

                throw;
            }
        }

        public override RetSit ElencoEsponenti()
        {
            var codiceVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;

            if (string.IsNullOrEmpty(codiceVia))
            {
                throw new ArgumentException($"{nameof(codiceVia)} non può essere vuoto");
            }

            if (string.IsNullOrEmpty(civico))
            {
                throw new ArgumentException($"{nameof(civico)} non può essere vuoto");
            }

            try
            {
                var esponenti = this._client
                                    .ListaEsponenti(new CodiceViario(codiceVia), civico)
                                    .Select(x => x.esponente)
                                    .WhereIgnoreCase(this.DataSit.Esponente);

                return new RetSit(true, esponenti);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoEsponenti -> errore durante la lettura con i parametri codiceVia={0}, civico={1}: {2}", codiceVia, civico, ex.ToString());

                throw;
            }
        }

        public override RetSit ElencoSezioni()
        {
            try
            {
                var sezioni = this._client.ListaSezioni().Select(x => x.sez).WhereIgnoreCase(this.DataSit.Sezione); ;

                return new RetSit(true, sezioni);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER->ElencoSezioni: errore durante la lettura {0}", ex.ToString());

                throw;
            }
        }

        public override RetSit ElencoFogli()
        {
            try
            {
                var sezioni = this._client.ListaFogli().Select(x => x.foglio).WhereIgnoreCase(this.DataSit.Foglio); ;

                return new RetSit(true, sezioni);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER->ElencoFogli: errore durante la lettura {0}", ex.ToString());

                throw;
            }
        }

        public override RetSit ElencoParticelle()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;

            if (string.IsNullOrEmpty(foglio))
            {
                throw new ArgumentException($"{nameof(foglio)} non può essere vuoto");
            }

            try
            {
                var particelle = this._client.ListaParticelle(sezione, foglio).Select(x => x.numero).WhereIgnoreCase(this.DataSit.Particella); ;

                return new RetSit(true, particelle);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoParticelle -> errore durante la lettura con i parametri sezione={0}, foglio={1}: {2}", sezione, foglio, ex.ToString());

                throw;
            }
        }

        public override RetSit ElencoSub()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;

            // Condition.Requires(sezione).IsNotEmpty();
            if (string.IsNullOrEmpty(foglio))
            {
                throw new ArgumentException($"{nameof(foglio)} non può essere vuoto");
            }

            if (string.IsNullOrEmpty(particella))
            {
                throw new ArgumentException($"{nameof(particella)} non può essere vuoto");
            }

            try
            {
                var sub = this._client.ListaSub(sezione, foglio, particella).Select(x => x.sub).WhereIgnoreCase(this.DataSit.Sub); ;

                return new RetSit(true, sub);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("SIT_SILVERBROWSER.ElencoSub -> errore durante la lettura con i parametri sezione={0}, foglio={1}, particella={2}: {3}", sezione, foglio, particella, ex.ToString());

                throw;
            }
        }

        public override RetSit CivicoValidazione()
        {
            return this.EsponenteValidazione();
        }

        public override RetSit EsponenteValidazione()
        {
            var codVia = this.DataSit.CodVia;
            var civico = this.DataSit.Civico;
            var esponente = this.DataSit.Esponente;

            if (String.IsNullOrEmpty(codVia) || String.IsNullOrEmpty(civico))
            {
                return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "codice via o civico non valorizzati");
            }

            try
            {
                var codViario = new CodiceViario(codVia);
                var datiCivico = String.IsNullOrEmpty(esponente) ? this._client.VerificaCivico(codViario, civico) : this._client.VerificaCivicoConEsponente(codViario, civico, esponente);

                if (datiCivico == null)
                {
                    var errMsg = String.Format("Impossibile validare il civico {0}{1} nella via {2}", civico, esponente, codVia);

                    return new ErrorMessage(MessageCode.CivicoValidazione).ToRetSit(false, errMsg);
                }

                this.DataSit.ExtendWith(datiCivico.ToDatiLocalizzazione());

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione del civico: codiceVia={0}, civico={1}, esponente={3}: {4}", codVia, civico, esponente, ex.ToString());

                throw;
            }
        }



        public override RetSit ParticellaValidazione()
        {
            this.DataSit.Foglio = this.DataSit.Foglio.PadLeft(4, '0');
            this.DataSit.Particella = this.DataSit.Particella.PadLeft(5, '0');

            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;


            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
            {
                return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "foglio o particella non valorizzati");
            }

            try
            {
                var datiParticella = this._client.VerificaParticella(foglio, particella);

                if (datiParticella == null)
                {
                    var errMsg = String.Format("Impossibile validare la particella {0} (foglio: {1})", foglio, particella);

                    return new ErrorMessage(MessageCode.ParticellaValidazione).ToRetSit(false, errMsg);
                }

                this.DataSit.ExtendWith(datiParticella.ToDatiLocalizzazione());

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione della particella: foglio={0}, particella={1}: {3}", foglio, particella, ex.ToString());

                throw;
            }
        }

        public override RetSit SubValidazione()
        {
            var sezione = this.DataSit.Sezione;
            var foglio = this.DataSit.Foglio;
            var particella = this.DataSit.Particella;
            var sub = this.DataSit.Sub;


            if (String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella) || String.IsNullOrEmpty(sub))
            {
                return new ErrorMessage(MessageCode.EsponenteValidazione).ToRetSit(false, "foglio, particella o sub non valorizzati");
            }

            try
            {
                var datiSub = this._client.VerificaSub(sezione, foglio, particella, sub);

                if (datiSub == null)
                {
                    var errMsg = String.Format("Impossibile validare il sub {0} (sezione: {0}, foglio: {1}, particella: {2})", sub, sezione, foglio, particella);

                    return new ErrorMessage(MessageCode.ParticellaValidazione).ToRetSit(false, errMsg);
                }

                this.DataSit.ExtendWith(datiSub.ToDatiLocalizzazione());

                return new RetSit(true);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la validazione della particella: foglio={0}, particella={1}: {3}", foglio, particella, ex.ToString());

                throw;
            }
        }



        public override string[] GetListaCampiGestiti()
        {
            return new[]
            {
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Sezione,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
                SitIntegrationService.NomiCampiSit.CodiceCivico,
                SitIntegrationService.NomiCampiSit.CodiceVia,
                // SitIntegrationService.NomiCampiSit.Fabbricato
                /*,
				SitIntegrationService.NomiCampiSit.Coordinate*/
			};
        }

        public override void SetupVerticalizzazione(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            var verticalizzazione = verticalizzazioniFactory.Create<VerticalizzazioneSitSilverBrowser>(this.IdComune, this.Software);

            if (!verticalizzazione.Attiva)
            {
                throw new Exception(String.Format("La verticalizzazione SIT_SILVERBROWSER non è attiva per l'alias {0} e il software {1}", this.Alias, this.Software));
            }

            var url = verticalizzazione.ServiceBaseUrl;

            if (!url.EndsWith("/"))
                url += "/";

            this._client = new SilverBrowserClient(url);
            this._urlZoomDaPunto = verticalizzazione.MapZoomDaPunto;
            this._urlZoomDaParticella = verticalizzazione.MapZoomDaParticella;
            this._urlZoomDaPuntoBo = verticalizzazione.MapZoomDaPuntoBo;
            this._urlZoomDaParticellaBo = verticalizzazione.MapZoomDaParticellaBo;

        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniFrontoffice()
        {
            return this.GetVisualizzazioniFrontofficeInternal().ToArray();
        }

        public override BaseDto<SitFeatures.TipoVisualizzazione, string>[] GetVisualizzazioniBackoffice()
        {
            return this.GetVisualizzazioniBackofficeInternal().ToArray();
        }

        private IEnumerable<BaseDto<SitFeatures.TipoVisualizzazione, string>> GetVisualizzazioniFrontofficeInternal()
        {
            if (!String.IsNullOrEmpty(this._urlZoomDaPunto))
            {
                yield return new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPunto);
            }

            if (!String.IsNullOrEmpty(this._urlZoomDaParticella))
            {
                yield return new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaPunto);
            }
        }

        private IEnumerable<BaseDto<SitFeatures.TipoVisualizzazione, string>> GetVisualizzazioniBackofficeInternal()
        {
            if (!String.IsNullOrEmpty(this._urlZoomDaPuntoBo))
            {
                yield return new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaIndirizzo, this._urlZoomDaPuntoBo);
            }

            if (!String.IsNullOrEmpty(this._urlZoomDaParticellaBo))
            {
                yield return new BaseDto<SitFeatures.TipoVisualizzazione, string>(SitFeatures.TipoVisualizzazione.PuntoDaMappale, this._urlZoomDaParticellaBo);
            }
        }

        public override DettagliVia[] GetListaVie(FiltroRicercaListaVie filtro, string[] codiciComuni)
        {
            var listaVie = this._client.ListaVie();

            return listaVie.Select(x => new DettagliVia
            {
                CodiceViario = $"{this.Alias}{x.codice.ToString().PadLeft(8, '0')}",
                Denominazione = x.nome,
                Toponimo = x.dug
            }).ToArray();
        }
    }
}
