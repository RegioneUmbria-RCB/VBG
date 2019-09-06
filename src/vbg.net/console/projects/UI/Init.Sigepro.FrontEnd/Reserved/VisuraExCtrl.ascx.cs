using System;
using System.Linq;
using System.Collections.Generic;
using System.Configuration;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using static Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_soggetti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_localizzazioni;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_documenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_endoprocedimenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_oneri;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_movimenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_autorizzazioni;
using System.Web.UI;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System.Web;

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
            get { object o = this.ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["DaArchivio"] = value; }
        }

        public bool ScadenzePresenti
        {
            get { object o = this.ViewState["ScadenzePresenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ScadenzePresenti"] = value; }
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
                this.Tabs = tabs;
            }
        
            public void SetBadgeValue(string tabName, string value)
            {
                var tab = this.Tabs.Where(x => x.Id == tabName).FirstOrDefault();

                if (tab != null)
                {
                    tab.ValoreBadge = value;
                }
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
            var istanza = this._visuraService.GetByUuid(codiceIstanza);

            this.DataSource = istanza;
            this.DataBind();

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

            this.datiGenerali.DaArchivio = this.DaArchivio;
            this.datiGenerali.DataSource = new VisuraDatiGeneraliDataSource
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
            this.datiGenerali.DataBind();


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

            this.visuraSoggetti.DaArchivio = this.DaArchivio;
            this.visuraSoggetti.DataSource = soggetti;
            this.visuraSoggetti.DataBind();

            // Localizzazioni
            visuraLocalizzazioni.MostraDatiCatastaliEstesi = this.MostraDatiCatastaliEstesi;
            visuraLocalizzazioni.DataSource = new VisuraLocalizzazioniDataSource
            {
                Stradario = DataSource.Stradario,
                Mappali = DataSource.Mappali
            };
            visuraLocalizzazioni.DataBind();

            // Schede dinamiche
            this.schedeDinamicheReadonly.DaArchivio = this.DaArchivio;
            this.schedeDinamicheReadonly.CodiceIstanza = Convert.ToInt32(this.DataSource.CODICEISTANZA);
            this.schedeDinamicheReadonly.DataBind();


            // Procedimenti
            var endo = DataSource.EndoProcedimenti.Select(x => new VisuraEndoListItem
            {
                Id = Convert.ToInt32(x.CODICEINVENTARIO),
                CodiceIstanza = Convert.ToInt32(DataSource.CODICEISTANZA),
                Endoprocedimento = x.Endoprocedimento.Procedimento,
                NumeroAllegati = x.IstanzeAllegati?.Where(y => !String.IsNullOrEmpty(y.CODICEOGGETTO)).Count() ?? 0
            });

            visuraEndoprocedimenti.DaArchivio = this.DaArchivio;
            visuraEndoprocedimenti.DataSource = endo;
            visuraEndoprocedimenti.DataBind();

            // Documenti
            var documenti = DataSource.DocumentiIstanza
                                        .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
                                        .Select(x => new VisuraDocumentiListItem
                                        {
                                            CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                                            Data = x.DATA,
                                            Descrizione = x.DOCUMENTO,
                                            Md5 = x.Oggetto.Md5,
                                            NomeFile = x.Oggetto.NOMEFILE
                                        });
            visuraDocumenti.DaArchivio = DaArchivio;
            visuraDocumenti.DataSource = documenti;
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
            visuraOneri.DaArchivio = this.DaArchivio;
            visuraOneri.DataSource = oneri;
            visuraOneri.DataBind();

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

            visuraMovimenti.DaArchivio = this.DaArchivio;
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

            visuraAutorizzazioni.DaArchivio = this.DaArchivio;
            visuraAutorizzazioni.DataSource = autorizzazioni;
            visuraAutorizzazioni.DataBind();

            var listaScadenze = _scadenzeService.GetListaScadenzeByNumeroIstanza(this.DataSource.SOFTWARE, this.DataSource.NUMEROISTANZA);

            dgScadenze.Visible = !this.DaArchivio;
            dgScadenze.DataSource = listaScadenze;
            dgScadenze.DataBind();

            var numeroScadenze = listaScadenze == null ? 0 : listaScadenze.Count();
            this.ScadenzePresenti = numeroScadenze > 0;

            this.TabsPagina.SetBadgeValue("scadenze", numeroScadenze.ToString());

            // Tabs
            var tabs = this.TabsPagina.Tabs.AsEnumerable();

            if (this.DaArchivio)
            {
                tabs = tabs.Where(x => x.VisibileDaArchivio);
            }

            this.rptTabs.DataSource = tabs;
            this.rptTabs.DataBind();
        }

        protected string GetUrlMovimento(object idMovimento)
        {
            var page = this.Page as ReservedBasePage;

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