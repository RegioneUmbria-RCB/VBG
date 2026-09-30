using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.Reserved.Visura;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using static Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_autorizzazioni;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_documenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_endoprocedimenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_localizzazioni;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_movimenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_oneri;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_soggetti;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class VisuraExCtrl : Ninject.Web.UserControlBase
    {
        [Inject]
        public IVisuraService _visuraService { get; set; }
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }
        [Inject]
        public IAuthenticationDataResolver _authDataResolver { get; set; }

        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public delegate void ScadenzaSelezionataDelegate(object sender, string idScadenza);
        public event ScadenzaSelezionataDelegate ScadenzaSelezionata;

        private readonly ILog _log = LogManager.GetLogger(typeof(VisuraExCtrl));

        /*
        public bool DaArchivio
        {
            get { object o = ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
            set { ViewState["DaArchivio"] = value; }
        }
        */

        public bool MostraDatiCatastaliEstesi
        {
            get
            {
                var obj = ConfigurationManager.AppSettings["MostraDatiCatastaliEstesi"];

                if (String.IsNullOrEmpty(obj))
                    return false;

                try
                {
                    return Convert.ToBoolean(obj);
                }
                catch (Exception)
                {
                    return false;
                }
            }
        }

        //public bool MostraDocumentiNonValidi
        //{
        //    get { object o = this.ViewState["MostraDocumentiNonValidi"]; return o == null ? true : (bool)o; }
        //    set { this.ViewState["MostraDocumentiNonValidi"] = value; this.visuraEndoprocedimenti.MostraDocumentiNonValidi = value; }
        //}

        public bool MostraScadenze
        {
            get { var o = this.ViewState["MostraScadenze"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraScadenze"] = value; }
        }

        public bool MostraPraticheCollegate
        {
            get { return this.visuraMovimenti.MostraPraticheCollegate; }
            set { this.visuraMovimenti.MostraPraticheCollegate = value; }
        }

        public LivelloAccessoVisura LivelloAccesso
        {
            get { return LivelloAccessoVisura.FromSerializationCode(this.ViewState["LivelloAccesso"]?.ToString()); }
            set { this.ViewState["LivelloAccesso"] = value.ToSerializationCode(); }
        }

        public Func<IDocumentoIstanzaOggettoDiVerifica, bool> CallbackValidazioneAllegati { get; set; } = (ia) => ia.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
                                                                                                                ia.EsitoVerifica == StatoVerificaDocumentoEnum.NonValido ||
                                                                                                                ia.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;

        public Istanze DataSource { get; set; }

        public VisuraTabList TabsPagina { get; } = VisuraTabList.Default;

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public void EffettuaVisuraIstanza(string codiceIstanza)
        {
            var istanza = this._visuraService.GetByUuid(codiceIstanza, true);

            this.EffettuaVisuraIstanza(istanza);
        }

        public void EffettuaVisuraIstanza(Istanze istanza)
        {
            this.DataSource = istanza;
            this.DataBind();

            try
            {
                if (!this.IsPostBack)
                    this.ltrIntestazioneDettaglio.Text = this._configurazione.Parametri.MessaggioIntestazioneVisura;
            }
            catch (Exception ex1)
            {
                this._log.Error(ex1.ToString());
            }
        }

        public override void DataBind()
        {
            //try
            //{
            if (this.DataSource == null)
                return;

            var visuraSuStessoComune = this.DataSource.IDCOMUNE == ((ReservedBasePage)this.Page).UserAuthenticationResult.IdComuneDb;

            var praticheCollegate = this.DataSource.Movimenti.Where(x => !String.IsNullOrEmpty(x.UuidPraticaCollegata));

            if (this.MostraPraticheCollegate && this._configurazione.Parametri.VerticalizzazioneArpaCalabriaAttiva && praticheCollegate.Any())
            {
                var url = UrlBuilder.Url(this.Request.Url.ToString().Split('?')[0], qb =>
               {

                   foreach (var key in this.Request.QueryString.Keys)
                   {
                       var val = this.Request.QueryString[key.ToString()];

                       if (key.ToString() == QsUuidIstanza.QuerystringParameterName)
                       {
                           val = praticheCollegate.First().UuidPraticaCollegata;
                       }

                       qb.Add(key.ToString(), val);
                   }
               });

                this.Response.Redirect(url);
            }

            this.datiGenerali.MostraDatiProtocollo = this.LivelloAccesso != LivelloAccessoVisura.AccessoAnonimo;
            this.datiGenerali.DataSource = new VisuraDatiGeneraliDataSource
            {
                ComunePratica = this.DataSource.ComuneIstanza.COMUNE,
                DataPratica = this.DataSource.DATA,
                DataProtocollo = this.DataSource.DATAPROTOCOLLO,
                Intervento = this.DataSource.Intervento.SC_DESCRIZIONE,
                Istruttore = this.DataSource.Istruttore?.RESPONSABILE,
                NumeroPratica = this.DataSource.NUMEROISTANZA,
                NumeroProtocollo = this.DataSource.NUMEROPROTOCOLLO,
                Oggetto = this.DataSource.LAVORI,
                Operatore = this.DataSource.Operatore?.RESPONSABILE,
                PosizioneArchivio = this.DataSource.POSIZIONEARCHIVIO,
                ResponsabileProcedimento = this.DataSource.ResponsabileProc?.RESPONSABILE,
                Stato = this.DataSource.Stato.Stato
            };
            this.datiGenerali.DataBind();

            if (!this.LivelloAccesso.DatiGenerali)
            {
                this.datiGenerali.Visible = false;
                this.TabsPagina.RimuoviTab(VisuraTabListNames.DatiGenerali);
            }

            // Soggetti dell'istanza
            var soggetti = new List<VisuraSoggettiListItem>();

            soggetti.Add(new VisuraSoggettiListItem(this.DataSource.Richiedente, this.DataSource.TipoSoggetto, this.DataSource.AziendaRichiedente));

            if (this.DataSource.Professionista != null)
            {
                var ts = new TipiSoggetto();
                ts.TIPOSOGGETTO = "Intermediario";
                soggetti.Add(new VisuraSoggettiListItem(this.DataSource.Professionista, ts));
            }

            if (this.DataSource.Richiedenti != null)
            {
                var richiedenti = this.DataSource.Richiedenti.Select(x => new VisuraSoggettiListItem(x.Richiedente, x.TipoSoggetto, x.AnagrafeCollegata, x.Procuratore));

                soggetti.AddRange(richiedenti);
            }

            if (!this.LivelloAccesso.DatiGenerali || !this.LivelloAccesso.SogettiPratica)
            {
                soggetti = new List<VisuraSoggettiListItem>();
            }

            this.visuraSoggetti.DataSource = soggetti;
            this.visuraSoggetti.DataBind();



            // Localizzazioni
            this.visuraLocalizzazioni.MostraDatiCatastaliEstesi = this.MostraDatiCatastaliEstesi;
            this.visuraLocalizzazioni.DataSource = new VisuraLocalizzazioniDataSource
            {
                Stradario = this.DataSource.Stradario,
                Mappali = this.DataSource.Mappali
            };
            this.visuraLocalizzazioni.DataBind();

            var numeroLocalizzazioni = this.visuraLocalizzazioni.NumeroRecords;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Localizzazioni, numeroLocalizzazioni.ToString());

            if (numeroLocalizzazioni == 0 || this.LivelloAccesso == LivelloAccessoVisura.AccessoAnonimo)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Localizzazioni);
                this.visuraLocalizzazioni.Visible = false;
            }


            if (visuraSuStessoComune && this.LivelloAccesso.Schede)
            {
                // Schede dinamiche
                this.schedeDinamicheReadonly.Istanza = this.DataSource;
                this.schedeDinamicheReadonly.DataBind();

                this.TabsPagina.SetBadgeValue(VisuraTabListNames.Schede, this.schedeDinamicheReadonly.ConteggioSchede.ToString());

                if (this.schedeDinamicheReadonly.ConteggioSchede == 0)
                {
                    this.schedeDinamicheReadonly.Visible = false;
                    this.TabsPagina.RimuoviTab(VisuraTabListNames.Schede);
                }
            }
            else
            {
                this.schedeDinamicheReadonly.Visible = false;
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Schede);
            }

            // Procedimenti
            var endo = this.DataSource.EndoProcedimenti.Select(x => new VisuraEndoListItem
            {
                Id = Convert.ToInt32(x.CODICEINVENTARIO),
                CodiceIstanza = Convert.ToInt32(this.DataSource.CODICEISTANZA),
                Endoprocedimento = x.Endoprocedimento.Procedimento,
                Allegati = x.IstanzeAllegati?.Where(y => y.ContieneOggetto && this.CallbackValidazioneAllegati(y))
            });

            this.visuraEndoprocedimenti.PermettiDownload = visuraSuStessoComune;
            this.visuraEndoprocedimenti.DataSource = endo;
            this.visuraEndoprocedimenti.DataBind();

            var numeroEndo = endo?.Count() ?? 0;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Endoprocedimenti, numeroEndo.ToString());

            if (numeroEndo == 0 || !this.LivelloAccesso.Endoprocedimenti)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Endoprocedimenti);
                this.visuraEndoprocedimenti.Visible = false;
            }

            // Documenti
            var documenti = this.DataSource
                                .DocumentiIstanza
                                .Where(x => x.ContieneOggetto && this.CallbackValidazioneAllegati(x));


            var documentiVisura = documenti.Select(x => new VisuraDocumentiListItem
            {
                CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                Data = x.DATA,
                Descrizione = x.DOCUMENTO,
                Md5 = x.Oggetto.Md5,
                NomeFile = x.Oggetto.NOMEFILE,
                UrlDownload = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(x.CODICEOGGETTO))
            });

            this.visuraDocumenti.PermettiDownload = visuraSuStessoComune;
            this.visuraDocumenti.DataSource = documentiVisura;
            this.visuraDocumenti.DataBind();

            var numeroDocumenti = documentiVisura?.Count() ?? 0;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Documenti, numeroDocumenti.ToString());

            if (numeroDocumenti == 0 || this.LivelloAccesso.Documenti == false)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Documenti);
                this.visuraDocumenti.Visible = false;
            }

            // Oneri
            var oneri = this.DataSource.Oneri
                                    .Where(x => x.DATAPAGAMENTO.HasValue && x.ImportoPagato.GetValueOrDefault(0) > 0)
                                    .Select(x => new VisuraOneriListItem
                                    {
                                        Causale = x.CausaleOnere.CoDescrizione,
                                        Importo = (float)x.ImportoPagato.GetValueOrDefault(0),
                                        DataPagamento = x.DATAPAGAMENTO,
                                        DataScadenza = x.DATASCADENZA
                                    });

            this.visuraOneri.DataSource = oneri;
            this.visuraOneri.DataBind();

            var numeroOneri = oneri?.Count() ?? 0;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Oneri, numeroOneri.ToString());

            if (numeroOneri == 0 || this.LivelloAccesso.Oneri == false)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Oneri);
                this.visuraOneri.Visible = false;
            }


            // movimenti
            var movimenti = this.DataSource.Movimenti
                                      .Where(x => x.PUBBLICA == "1" && x.DATA.HasValue)
                                      .Select(x => new VisuraMovimentiListItem
                                      {
                                          Id = Convert.ToInt32(x.CODICEMOVIMENTO),
                                          CodiceIstanza = Convert.ToInt32(this.DataSource.CODICEISTANZA),
                                          Descrizione = x.MOVIMENTO,
                                          Data = x.DATA,
                                          Parere = x.PUBBLICAPARERE == "1" ? x.PARERE : String.Empty,
                                          NumeroProtocollo = x.NUMEROPROTOCOLLO,
                                          DataProtocollo = x.DATAPROTOCOLLO,
                                          UuidPraticaCollegata = x.UuidPraticaCollegata,
                                          Allegati = x.MovimentiAllegati?.Where(y => y.FlagPubblica.GetValueOrDefault(0) == 1 &&
                                                                                     y.ContieneOggetto &&
                                                                                     this.CallbackValidazioneAllegati(y))
                                      });


            this.visuraMovimenti.PermettiDownload = visuraSuStessoComune;
            this.visuraMovimenti.DataSource = movimenti;
            this.visuraMovimenti.DataBind();

            var numeroMovimenti = movimenti?.Count() ?? 0;

            if (numeroMovimenti == 0 || this.LivelloAccesso.MovimentiEffettuati == false)
            {
                this.titoloMovimenti.Visible = false;
                this.visuraMovimenti.Visible = false;
            }

            // Autorizzazioni
            var autorizzazioni = this.DataSource.Autorizzazioni.Select(x => new VisuraAutorizzazioniListItem
            {
                Data = x.AUTORIZDATA,
                Descrizione = x.Registro.TR_DESCRIZIONE,
                Note = x.AUTORIZRESPONSABILE,
                Numero = x.AUTORIZNUMERO,
                DataScadenza = x.DataScadenza,
                DataCessazione = x.DataCessazione,
                Attiva = x.FlagAttiva.GetValueOrDefault(0) == 1
            });

            this.visuraAutorizzazioni.DataSource = autorizzazioni;
            this.visuraAutorizzazioni.DataBind();

            var numeroAutorizzazioni = autorizzazioni?.Count() ?? 0;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Autorizzazioni, numeroAutorizzazioni.ToString());

            if (numeroAutorizzazioni == 0 || this.LivelloAccesso.Autorizzazioni == false)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Autorizzazioni);
                this.visuraAutorizzazioni.Visible = false;
            }

            // scadenze
            var codiceUtente = this._authDataResolver.IsAuthenticated ? this._authDataResolver.DatiAutenticazione.DatiUtente.Codicefiscale : null;


            var listaScadenze = this._scadenzeService.GetListaScadenzeByCodiceIstanza(this.DataSource.SOFTWARE, Convert.ToInt32(this.DataSource.CODICEISTANZA), codiceUtente);

            this.dgScadenze.DataSource = listaScadenze;
            this.dgScadenze.DataBind();

            var numeroScadenze = listaScadenze?.Count() ?? 0;

            this.TabsPagina.SetBadgeValue(VisuraTabListNames.Scadenze, numeroScadenze.ToString());

            if (!this.MostraScadenze || numeroScadenze == 0)
            {
                this.TabsPagina.RimuoviTab(VisuraTabListNames.Scadenze);
                this.dgScadenze.Visible = false;
            }

            if (!this.MostraPraticheCollegate)
            {
                // ???
            }

            // Tabs
            var tabs = this.TabsPagina.Tabs.AsEnumerable();

            if (this.LivelloAccesso == LivelloAccessoVisura.AccessoAnonimo)
            {
                tabs = tabs.Where(x => x.VisibileDaArchivio);
            }

            this.rptTabs.DataSource = tabs;
            this.rptTabs.DataBind();
        }

        protected string GetUrlMovimento(object idMovimento)
        {
            var page = this.Page as ReservedBasePage;

            return this.ToAbsoluteUrl(UrlBuilder.Url("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs =>
            {
                qs.Add(new QsAliasComune(page.IdComune));
                qs.Add(new QsSoftware(page.Software));
                qs.Add("IdMovimento", idMovimento.ToString());
            }));

        }
        private string ToAbsoluteUrl(string relative)
        {
            //return relative;
            var pre = "//"
                        + HttpContext.Current.Request.Url.Authority
                        + HttpContext.Current.Request.ApplicationPath;

            return pre + relative.Replace("~", String.Empty);

        }

        public string EsitoMovimento(object val)
        {
            if (val == null || String.IsNullOrEmpty(val.ToString())) return String.Empty;

            if (val.ToString() == "0") return "Negativo";

            return "Positivo";
        }

        public void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
        {
            var scadenza = this.dgScadenze.DataKeys[this.dgScadenze.SelectedIndex].Value.ToString();

            if (ScadenzaSelezionata != null)
                ScadenzaSelezionata(this, scadenza);
        }

    }
}