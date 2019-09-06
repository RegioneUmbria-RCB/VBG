using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Scadenzario;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using static Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_documenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_localizzazioni;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_soggetti;

namespace Init.Sigepro.FrontEnd.Reserved.enti_terzi
{
    public partial class et_dettaglio_pratica : ReservedBasePage
    {
        [Inject]
        public IVisuraService _visuraService { get; set; }
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }
        [Inject]
        public IScrivaniaEntiTerziService _etService { get; set; }

        protected QsUuidIstanza Uuid => new QsUuidIstanza(Request.QueryString);

        public int CodiceIstanza
        {
            get { object o = this.ViewState["CodiceIstanza"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceIstanza"] = value; }
        }



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
        }

        public TabsList TabsPagina = new TabsList(new List<TabsListItem> {
            new TabsListItem{ Descrizione= "Dati generali", Id="dati-generali", VisibileDaArchivio=true, IsActive=true },
            new TabsListItem{ Descrizione= "Localizzazioni", Id="localizzazioni", VisibileDaArchivio=true },
            new TabsListItem{ Descrizione= "Schede", Id="schede", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Documenti", Id="documenti", VisibileDaArchivio=false },
            new TabsListItem{ Descrizione= "Scadenze", Id="scadenze", VisibileDaArchivio=false, HasBadge = true, ValoreBadge = "0" }
        });

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var istanza = _visuraService.GetByUuid(Uuid.Value);

            this.CodiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);

            datiGenerali.DaArchivio = false;
            datiGenerali.DataSource = new VisuraDatiGeneraliDataSource
            {
                DataPratica = istanza.DATA,
                NumeroPratica = istanza.NUMEROISTANZA,
                DataPratocollo = istanza.DATAPROTOCOLLO,
                NumeroProtocollo = istanza.NUMEROPROTOCOLLO,
                Intervento = istanza.Intervento.SC_DESCRIZIONE,
                Oggetto = istanza.LAVORI,
                Istruttore = istanza.Istruttore?.RESPONSABILE,
                Operatore = istanza.Operatore?.RESPONSABILE,
                ResponsabileProcedimento = istanza.ResponsabileProc?.RESPONSABILE,
                Stato = istanza.Stato.Stato
            };
            datiGenerali.DataBind();

            // Soggetti dell'istanza
            var soggetti = new List<VisuraSoggettiListItem>();

            soggetti.Add(new VisuraSoggettiListItem(istanza.Richiedente, istanza.TipoSoggetto, istanza.AziendaRichiedente));

            if (istanza.Professionista != null)
            {
                soggetti.Add(new VisuraSoggettiListItem(istanza.Professionista, new TipiSoggetto { TIPOSOGGETTO = "Intermediario" }));
            }

            if (istanza.Richiedenti != null)
            {
                var richiedenti = istanza.Richiedenti.Select(x => new VisuraSoggettiListItem(x.Richiedente, x.TipoSoggetto, x.AnagrafeCollegata, x.Procuratore));

                soggetti.AddRange(richiedenti);
            }

            visuraSoggetti.DaArchivio = false;
            visuraSoggetti.DataSource = soggetti;
            visuraSoggetti.DataBind();

            // Localizzazioni
            visuraLocalizzazioni.MostraDatiCatastaliEstesi = false;
            visuraLocalizzazioni.DataSource = new VisuraLocalizzazioniDataSource
            {
                Stradario = istanza.Stradario,
                Mappali = istanza.Mappali
            };
            visuraLocalizzazioni.DataBind();

            // Schede dinamiche
            schedeDinamicheReadonly.DaArchivio = false;
            schedeDinamicheReadonly.CodiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);
            schedeDinamicheReadonly.DataBind();

            // Documenti
            var documenti = istanza.DocumentiIstanza
                                        .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
                                        .Select(x => new VisuraDocumentiListItem
                                        {
                                            CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                                            Data = x.DATA,
                                            Descrizione = x.DOCUMENTO,
                                            Md5 = x.Oggetto.Md5,
                                            NomeFile = x.Oggetto.NOMEFILE
                                        });
            visuraDocumenti.DaArchivio = false;
            visuraDocumenti.DataSource = documenti;
            visuraDocumenti.DataBind();

            // Scadenze
            var codiceAnagrafe = new ETCodiceAnagrafe(UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            var listaScadenze = Enumerable.Empty<Scadenza>();

            if (this._etService.PuoEffettuareMovimenti(codiceAnagrafe))
            {
                var amministrazione = this._etService.GetDatiAmministrazioneCollegata(codiceAnagrafe);
                listaScadenze = _scadenzeService.GetListaScadenzeEntiTerziByNumeroIstanza(istanza.SOFTWARE, istanza.NUMEROISTANZA, amministrazione.PartitaIva);
            }

            dgScadenze.DataSource = listaScadenze;
            dgScadenze.DataBind();

            var numeroScadenze = listaScadenze == null ? 0 : listaScadenze.Count();

            TabsPagina.SetBadgeValue("scadenze", numeroScadenze.ToString());



            // Tabs
            var tabs = TabsPagina.Tabs.AsEnumerable();

            rptTabs.DataSource = tabs;
            rptTabs.DataBind();

            // pratica elaborata
            var elaborata = this._etService.PraticaElaborata(new ETCodiceIstanza(Convert.ToInt32(istanza.CODICEISTANZA)), new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            this.cmdMarcaComeElaborata.Visible = !elaborata;
            this.cmdMarcaComeNonElaborata.Visible = elaborata;
            this.pnlPraticaElaborata.Visible = elaborata;
            


        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/enti-terzi/et-lista-pratiche.aspx", pb =>
            {
                pb.Add(new QsAliasComune(IdComune));
                pb.Add(new QsSoftware(Software));
                pb.Add("restore", "1");

            });

            Response.Redirect(url);
        }

        protected string GetUrlMovimento(object idMovimento)
        {
            var page = Page as ReservedBasePage;

            return ResolveClientUrl(UrlBuilder.Url("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs =>
           {
               qs.Add(new QsAliasComune(page.IdComune));
               qs.Add(new QsSoftware(page.Software));
               qs.Add("IdMovimento", idMovimento.ToString());
           }));

        }

        protected void cmdMarcaComeElaborata_Click(object sender, EventArgs e)
        {
            var codiceIstanza = new ETCodiceIstanza(this.CodiceIstanza);
            var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            this._etService.MarcaPraticaComeElaborata(codiceIstanza, codiceAnagrafe);

            this.DataBind();
        }

        protected void cmdMarcaComeNonElaborata_Click(object sender, EventArgs e)
        {
            var codiceIstanza = new ETCodiceIstanza(this.CodiceIstanza);
            var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            this._etService.MarcaPraticaComeNonElaborata(codiceIstanza, codiceAnagrafe);

            this.DataBind();
        }
    }
}