using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using static Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_documenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_localizzazioni;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_movimenti;
using static Init.Sigepro.FrontEnd.Reserved.Visura.visura_soggetti;

namespace Init.Sigepro.FrontEnd.Reserved.enti_terzi
{
    public partial class et_dettaglio_pratica : ReservedBasePage
    {
        private static class Constants
        {
            public const string ReturnToSessionKey = "et_dettaglio_pratica.returnTo";
        }


        [Inject]
        public IVisuraService _visuraService { get; set; }
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }
        [Inject]
        public IScrivaniaEntiTerziService _etService { get; set; }
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        protected QsUuidIstanza Uuid => new QsUuidIstanza(this.Request.QueryString);

        protected QsReturnTo ReturnTo => new QsReturnTo(this.Request.QueryString);

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
            new TabsListItem{ Descrizione= "Scadenze", Id="scadenze", VisibileDaArchivio=false, HasBadge = true, ValoreBadge = "0" }
        });

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                if (this.ReturnTo.HasValue)
                {
                    this.Session[Constants.ReturnToSessionKey] = this.ReturnTo.Value;
                }

                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var istanza = this._visuraService.GetByUuid(this.Uuid.Value, false);

            this.CodiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);

            this.datiGenerali.MostraDatiProtocollo = true;
            this.datiGenerali.DataSource = new VisuraDatiGeneraliDataSource
            {
                ComunePratica = istanza.ComuneIstanza.COMUNE,
                DataPratica = istanza.DATA,
                DataProtocollo = istanza.DATAPROTOCOLLO,
                Intervento = istanza.Intervento.SC_DESCRIZIONE,
                Istruttore = istanza.Istruttore?.RESPONSABILE,
                NumeroPratica = istanza.NUMEROISTANZA,
                NumeroProtocollo = istanza.NUMEROPROTOCOLLO,
                Oggetto = istanza.LAVORI,
                Operatore = istanza.Operatore?.RESPONSABILE,
                PosizioneArchivio = istanza.POSIZIONEARCHIVIO,
                ResponsabileProcedimento = istanza.ResponsabileProc?.RESPONSABILE,
                Stato = istanza.Stato.Stato,

            };
            this.datiGenerali.DataBind();

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

            this.visuraSoggetti.DataSource = soggetti;
            this.visuraSoggetti.DataBind();

            // Movimenti
            this.visuraMovimenti.DataSource = istanza.Movimenti.Where(x => x.PUBBLICA == "1" && x.DATA.HasValue &&
                                                                    (x.Tipo.FkFoSoggettiesterni.GetValueOrDefault(0) == 1 ||
                                                                    x.Tipo.FkFoSoggettiesterni.GetValueOrDefault(0) == 2))
                                                              .Select(x => new VisuraMovimentiListItem
                                                              {
                                                                  Id = Convert.ToInt32(x.CODICEMOVIMENTO),
                                                                  CodiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA),
                                                                  Descrizione = x.MOVIMENTO,
                                                                  Data = x.DATA,
                                                                  Parere = x.PUBBLICAPARERE == "1" ? x.PARERE : String.Empty,
                                                                  NumeroProtocollo = x.NUMEROPROTOCOLLO,
                                                                  DataProtocollo = x.DATAPROTOCOLLO,
                                                                  UuidPraticaCollegata = String.Empty,//x.UuidPraticaCollegata,
                                                                  Allegati = x.MovimentiAllegati?.Where(y => y.FlagPubblica.GetValueOrDefault(0) == 1 &&
                                                                                                                  !String.IsNullOrEmpty(y.CODICEOGGETTO))
                                                              });
            this.visuraMovimenti.DataBind();

            // Localizzazioni
            this.visuraLocalizzazioni.MostraDatiCatastaliEstesi = false;
            this.visuraLocalizzazioni.DataSource = new VisuraLocalizzazioniDataSource
            {
                Stradario = istanza.Stradario,
                Mappali = istanza.Mappali
            };
            this.visuraLocalizzazioni.DataBind();

            // Schede dinamiche
            this.schedeDinamicheReadonly.Istanza = istanza;
            this.schedeDinamicheReadonly.DataBind();

            // Documenti
            var documenti = istanza.DocumentiIstanza
                                        .Where(x => !String.IsNullOrEmpty(x.CODICEOGGETTO))
                                        .Select(x => new VisuraDocumentiListItem
                                        {
                                            CodiceOggetto = Convert.ToInt32(x.CODICEOGGETTO),
                                            Data = x.DATA,
                                            Descrizione = x.DOCUMENTO,
                                            Md5 = x.Oggetto.Md5,
                                            NomeFile = x.Oggetto.NOMEFILE,
                                            UrlDownload = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(x.CODICEOGGETTO))
                                        });

            this.visuraDocumenti.DataSource = documenti;
            this.visuraDocumenti.DataBind();

            // Scadenze
            var codiceAnagrafe = new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

            var listaScadenze = Enumerable.Empty<ElementoListaScadenzeDto>();

            if (this._etService.PuoEffettuareMovimenti(codiceAnagrafe))
            {
                var amministrazione = this._etService.GetDatiAmministrazioneCollegata(codiceAnagrafe);
                listaScadenze = this._scadenzeService.GetListaScadenzeEntiTerziByNumeroIstanza(istanza.SOFTWARE, istanza.NUMEROISTANZA, amministrazione.PartitaIva);
            }

            this.dgScadenze.DataSource = listaScadenze;
            this.dgScadenze.DataBind();

            var numeroScadenze = listaScadenze == null ? 0 : listaScadenze.Count();

            this.TabsPagina.SetBadgeValue("scadenze", numeroScadenze.ToString());



            // Tabs
            var tabs = this.TabsPagina.Tabs.AsEnumerable();

            this.rptTabs.DataSource = tabs;
            this.rptTabs.DataBind();

            // pratica elaborata
            var elaborata = this._etService.PraticaElaborata(new ETCodiceIstanza(Convert.ToInt32(istanza.CODICEISTANZA)), new ETCodiceAnagrafe(this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value));

            this.cmdMarcaComeElaborata.Visible = !elaborata;
            this.cmdMarcaComeNonElaborata.Visible = elaborata;
            this.pnlPraticaElaborata.Visible = elaborata;



        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var returnTo = this.Session[Constants.ReturnToSessionKey]?.ToString();

            if (returnTo != null && !String.IsNullOrEmpty(returnTo.ToString()))
            {
                this.Session.Remove(Constants.ReturnToSessionKey);

                this.Response.Redirect(returnTo);

                return;
            }

            var url = UrlBuilder.Url("~/reserved/enti-terzi/et-lista-pratiche.aspx", pb =>
            {
                pb.Add(new QsAliasComune(this.IdComune));
                pb.Add(new QsSoftware(this.Software));
                pb.Add("restore", "1");

            });

            this.Response.Redirect(url);
        }

        protected string GetUrlMovimento(object idMovimento)
        {
            var page = this.Page as ReservedBasePage;

            var returnTo = UrlBuilder.Url("~/reserved/enti-terzi/et-dettaglio-pratica.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add(this.Uuid);
            });

            return this.ResolveClientUrl(UrlBuilder.Url("~/Reserved/Gestionemovimenti/EffettuaMovimento.aspx", qs =>
           {
               qs.Add(new QsAliasComune(page.IdComune));
               qs.Add(new QsSoftware(page.Software));
               qs.Add("IdMovimento", idMovimento.ToString());
               qs.Add(new QsReturnTo(returnTo));
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