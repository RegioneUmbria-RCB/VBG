using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
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
    public partial class VisuraExCtrl : System.Web.UI.UserControl
    {
        [Inject]
        public IVisuraService _visuraService { get; set; }
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }

        public delegate void ScadenzaSelezionataDelegate(object sender, string idScadenza);
        public event ScadenzaSelezionataDelegate ScadenzaSelezionata;

        ILog _log = LogManager.GetLogger(typeof(VisuraExCtrl));


        public bool DaArchivio
        {
            get { object o = ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
            set { ViewState["DaArchivio"] = value; }
        }

        public bool ScadenzePresenti
        {
            get { object o = ViewState["ScadenzePresenti"]; return o == null ? true : (bool)o; }
            set { ViewState["ScadenzePresenti"] = value; }
        }

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

        public bool MostraDocumentiNonValidi
        {
            get { object o = this.ViewState["MostraDocumentiNonValidi"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDocumentiNonValidi"] = value; this.visuraEndoprocedimenti.MostraDocumentiNonValidi = value; }
        }

        public bool MostraScadenze
        {
            get { object o = this.ViewState["MostraScadenze"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraScadenze"] = value; }
        }

        public bool MostraPraticheCollegate
        {
            get { return this.visuraMovimenti.MostraPraticheCollegate; }
            set { this.visuraMovimenti.MostraPraticheCollegate = value; }
        }




        public Istanze DataSource { get; set; }

        public class TabsListItem
        {
            public bool IsActive { get; set; }
            public string Descrizione { get; set; }
            public string Id { get; set; }
            public bool VisibileDaArchivio { get; set; }
            public UserControl Control { get; set; }
            public bool HasBadge { get; set; }
            public string ValoreBadge { get; set; }
        }

        public class TabsList
        {
            public List<TabsListItem> Tabs { get; private set; }

            public TabsList(List<TabsListItem> tabs)
            {
                Tabs = tabs;
            }

            public void SetBadgeValue(string tabName, string value)
            {
                var tab = Tabs.Where(x => x.Id == tabName).FirstOrDefault();

                if (tab != null)
                {
                    tab.ValoreBadge = value;
                }
            }

            internal void RimuoviTab(string idDaRimuovere)
            {
                Tabs.RemoveAll(x => x.Id == idDaRimuovere);
            }
        }

        public TabsList TabsPagina = new TabsList(new List<TabsListItem> {
            new TabsListItem{ Descrizione= "Dati generali", Id="dati-generali", VisibileDaArchivio=true, IsActive=true },
            new TabsListItem{ Descrizione= "Localizzazioni", Id="localizzazioni", VisibileDaArchivio=true },
            new TabsListItem{ Descrizione= "Schede", Id="schede", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Documenti", Id="documenti", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Endoprocedimenti", Id="endoprocedimenti", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Oneri", Id="oneri", VisibileDaArchivio=false },
            //new TabsListItem{ Descrizione= "Movimenti", Id="movimenti", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Autorizzazioni", Id="autorizzazioni", VisibileDaArchivio=true },
            new TabsListItem{ Descrizione= "Scadenze", Id="scadenze", VisibileDaArchivio=false, HasBadge = true, ValoreBadge = "0" }
        });




        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public void EffettuaVisuraIstanza(string idComune, string software, string codiceIstanza)
        {
            var istanza = _visuraService.GetByUuid(codiceIstanza);

            DataSource = istanza;
            DataBind();

            try
            {
                if (!IsPostBack)
                    ltrIntestazioneDettaglio.Text = _configurazione.Parametri.MessaggioIntestazioneVisura;
            }
            catch (Exception ex1)
            {
                _log.Error(ex1.ToString());
            }
        }

        public override void DataBind()
        {
            //try
            //{
            if (DataSource == null)
                return;

            var praticheCollegate = DataSource.Movimenti.Where(x => !String.IsNullOrEmpty(x.UuidPraticaCollegata));

            if (this.MostraPraticheCollegate  && this._configurazione.Parametri.VerticalizzazioneArpaCalabriaAttiva && praticheCollegate.Any())
            {
                var url = UrlBuilder.Url(Request.Url.ToString().Split('?')[0], qb =>
               {

                   foreach (var key in Request.QueryString.Keys)
                   {
                       var val = Request.QueryString[key.ToString()];

                       if (key.ToString() == QsUuidIstanza.QuerystringParameterName)
                       {
                           val = praticheCollegate.First().UuidPraticaCollegata;
                       }

                       qb.Add(key.ToString(), val);
                   }
                });

                Response.Redirect(url);
            }

            datiGenerali.DaArchivio = DaArchivio;
            datiGenerali.DataSource = new VisuraDatiGeneraliDataSource
            {
                DataPratica = DataSource.DATA,
                NumeroPratica = DataSource.NUMEROISTANZA,
                DataPratocollo = DataSource.DATAPROTOCOLLO,
                NumeroProtocollo = DataSource.NUMEROPROTOCOLLO,
                Intervento = DataSource.Intervento.SC_DESCRIZIONE,
                Oggetto = DataSource.LAVORI,
                Istruttore = DataSource.Istruttore?.RESPONSABILE,
                Operatore = DataSource.Operatore?.RESPONSABILE,
                ResponsabileProcedimento = DataSource.ResponsabileProc?.RESPONSABILE,
                Stato = DataSource.Stato.Stato
            };
            datiGenerali.DataBind();


            // Soggetti dell'istanza
            var soggetti = new List<VisuraSoggettiListItem>();

            soggetti.Add(new VisuraSoggettiListItem(DataSource.Richiedente, DataSource.TipoSoggetto, DataSource.AziendaRichiedente));

            if (DataSource.Professionista != null)
            {
                TipiSoggetto ts = new TipiSoggetto();
                ts.TIPOSOGGETTO = "Intermediario";
                soggetti.Add(new VisuraSoggettiListItem(DataSource.Professionista, ts));
            }

            if (DataSource.Richiedenti != null)
            {
                var richiedenti = DataSource.Richiedenti.Select(x => new VisuraSoggettiListItem(x.Richiedente, x.TipoSoggetto, x.AnagrafeCollegata, x.Procuratore));

                soggetti.AddRange(richiedenti);
            }

            visuraSoggetti.DaArchivio = DaArchivio;
            visuraSoggetti.DataSource = soggetti;
            visuraSoggetti.DataBind();

            // Localizzazioni
            visuraLocalizzazioni.MostraDatiCatastaliEstesi = MostraDatiCatastaliEstesi;
            visuraLocalizzazioni.DataSource = new VisuraLocalizzazioniDataSource
            {
                Stradario = DataSource.Stradario,
                Mappali = DataSource.Mappali
            };
            visuraLocalizzazioni.DataBind();

            if (visuraLocalizzazioni.DataSource.Stradario.Count() == 0)
            {
                TabsPagina.RimuoviTab("localizzazioni");
            }

            // Schede dinamiche
            schedeDinamicheReadonly.DaArchivio = DaArchivio;
            schedeDinamicheReadonly.CodiceIstanza = Convert.ToInt32(DataSource.CODICEISTANZA);
            schedeDinamicheReadonly.DataBind();


            // Procedimenti
            var endo = DataSource.EndoProcedimenti.Select(x => new VisuraEndoListItem
            {
                Id = Convert.ToInt32(x.CODICEINVENTARIO),
                CodiceIstanza = Convert.ToInt32(DataSource.CODICEISTANZA),
                Endoprocedimento = x.Endoprocedimento.Procedimento,
                NumeroAllegati = x.IstanzeAllegati?.Where(y => 
                    !String.IsNullOrEmpty(y.CODICEOGGETTO) && (y.CONTROLLOOK == "1" || this.MostraDocumentiNonValidi )).Count()  ?? 0
            });

            visuraEndoprocedimenti.DaArchivio = DaArchivio;
            visuraEndoprocedimenti.DataSource = endo;
            visuraEndoprocedimenti.DataBind();

            // Documenti
            var documenti = DataSource.DocumentiIstanza
                                        .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO));

            if (!this.MostraDocumentiNonValidi)
            {
                documenti = documenti.Where(x => x.ControlloOk.GetValueOrDefault(0) == 1);
            }

            var docRes = documenti.Select(x => new VisuraDocumentiListItem
                                        {
                                            CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                                            Data = x.DATA,
                                            Descrizione = x.DOCUMENTO,
                                            Md5 = x.Oggetto.Md5,
                                            NomeFile = x.Oggetto.NOMEFILE
                                        });

            visuraDocumenti.DaArchivio = DaArchivio;
            visuraDocumenti.DataSource = docRes;
            visuraDocumenti.DataBind();

            // Oneri
            var oneri = DataSource.Oneri
                                    .Where(x => x.DATAPAGAMENTO.HasValue && x.ImportoPagato.GetValueOrDefault(0) > 0)
                                    .Select(x => new VisuraOneriListItem
                                    {
                                        Causale = x.CausaleOnere.CoDescrizione,
                                        Importo = (float)x.ImportoPagato.GetValueOrDefault(0),
                                        DataPagamento = x.DATAPAGAMENTO,
                                        DataScadenza = x.DATASCADENZA
                                    });
            visuraOneri.DaArchivio = DaArchivio;
            visuraOneri.DataSource = oneri;
            visuraOneri.DataBind();

            if (visuraOneri.DataSource.Count() == 0)
            {
                TabsPagina.RimuoviTab("oneri");
            }

            // Response.Write("13<br />");

            // movimenti
            var movimenti = DataSource.Movimenti
                                      .Where(x => x.PUBBLICA == "1" && x.DATA.HasValue)
                                      .Select(x => new VisuraMovimentiListItem
                                      {
                                          Id = Convert.ToInt32(x.CODICEMOVIMENTO),
                                          CodiceIstanza = Convert.ToInt32(DataSource.CODICEISTANZA),
                                          Descrizione = x.MOVIMENTO,
                                          Data = x.DATA,
                                          Parere = x.PUBBLICAPARERE == "1" ? x.PARERE : String.Empty,
                                          NumeroProtocollo = x.NUMEROPROTOCOLLO,
                                          DataProtocollo = x.DATAPROTOCOLLO,
                                          UuidPraticaCollegata = x.UuidPraticaCollegata,
                                          NumeroAllegati = x.MovimentiAllegati?.Where(y => y.FlagPubblica.GetValueOrDefault(0) == 1 &&
                                                                                          !String.IsNullOrEmpty(y.CODICEOGGETTO)).Count() ?? 0
                                      });

            visuraMovimenti.DaArchivio = DaArchivio;
            visuraMovimenti.DataSource = movimenti;
            visuraMovimenti.DataBind();

            // Autorizzazioni
            var autorizzazioni = DataSource.Autorizzazioni.Select(x => new VisuraAutorizzazioniListItem
            {
                Data = x.AUTORIZDATA,
                Descrizione = x.Registro.TR_DESCRIZIONE,
                Note = x.AUTORIZRESPONSABILE,
                Numero = x.AUTORIZNUMERO
            });

            visuraAutorizzazioni.DaArchivio = DaArchivio;
            visuraAutorizzazioni.DataSource = autorizzazioni;
            visuraAutorizzazioni.DataBind();

            if (visuraAutorizzazioni.DataSource.Count() == 0)
            {
                TabsPagina.RimuoviTab("autorizzazioni");
            }


            var listaScadenze = _scadenzeService.GetListaScadenzeByNumeroIstanza(DataSource.SOFTWARE, DataSource.NUMEROISTANZA);

            dgScadenze.Visible = !DaArchivio;
            dgScadenze.DataSource = listaScadenze;
            dgScadenze.DataBind();

            if (listaScadenze.Count() == 0)
            {
                TabsPagina.RimuoviTab("scadenze");
            }

            var numeroScadenze = listaScadenze == null ? 0 : listaScadenze.Count();
            ScadenzePresenti = numeroScadenze > 0;

            TabsPagina.SetBadgeValue("scadenze", numeroScadenze.ToString());


            if (!this.MostraScadenze)
            {
                TabsPagina.RimuoviTab("scadenze");
            }

            if (!this.MostraPraticheCollegate)
            {

            }

            // Tabs
            var tabs = TabsPagina.Tabs.AsEnumerable();

            if (DaArchivio)
            {
                tabs = tabs.Where(x => x.VisibileDaArchivio);
            }

            rptTabs.DataSource = tabs;
            rptTabs.DataBind();
        }

        protected string GetUrlMovimento(object idMovimento)
        {
            var page = Page as ReservedBasePage;

            return ToAbsoluteUrl(UrlBuilder.Url("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs =>
            {
                qs.Add(new QsAliasComune(page.IdComune));
                qs.Add(new QsSoftware(page.Software));
                qs.Add("IdMovimento", idMovimento.ToString());
            }));

        }
        private string ToAbsoluteUrl(string relative)
        {
            var pre = HttpContext.Current.Request.Url.Scheme
                        + "://"
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
            string scadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

            if (ScadenzaSelezionata != null)
                ScadenzaSelezionata(this, scadenza);
        }

    }
}