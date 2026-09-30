using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.QsParameters.AccessoAtti;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.accesso_atti
{
    public partial class accesso_atti_list : ReservedBasePage
    {
        public class BindingItem
        {
            public int Key { get; set; }
            public string Descrizione { get; set; }
            public List<PraticaAccessoAtti> Pratiche { get; set; } = new List<PraticaAccessoAtti>();
        }

        [Inject]
        public IVbgAccessoAttiService _service { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var listaPratiche = this._service.GetListaPratiche(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value);
            var dataSource = new Dictionary<int, BindingItem>();

            foreach (var pratica in listaPratiche)
            {
                if (!dataSource.TryGetValue(pratica.IdAccessoAtti, out BindingItem value))
                {
                    value = new BindingItem
                    {
                        Key = pratica.IdAccessoAtti,
                        Descrizione = $"{pratica.CodiceIstanzaAccessoAtti} del {pratica.DataIstanzaAccessoAtti.Value.ToString("dd/MM/yyyy")}  - {pratica.DescrizioneAccessoAtti}"
                    };

                    dataSource.Add(pratica.IdAccessoAtti, value);
                }

                value.Pratiche.Add(pratica);
            }

            this.rptItems.DataSource = dataSource.Values;
            this.rptItems.DataBind();
        }

        private string UrlAccessoPratica(int idAccessoAtti, string uuidIstanza)
        {
            var url = UrlBuilder.Url("~/reserved/accesso-atti/accesso-atti-dettaglio.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(new QsUuidIstanza(uuidIstanza));
                x.Add(new QsIdAccessoAtti(idAccessoAtti));
            });

            return this.ResolveClientUrl(url);
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            this.Response.Redirect(UrlBuilder.Url("~/reserved/default.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            }));
        }

        protected void rptItems_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptPraticheFascicolo = (Repeater)e.Item.FindControl("rptPraticheFascicolo");
                rptPraticheFascicolo.DataSource = (e.Item.DataItem as BindingItem).Pratiche.Select(x => new
                {
                    x.StringaProtocollo,
                    x.StringaNumeroIstanza,
                    x.Localizzazione,
                    x.Richiedente,
                    x.Oggetto,
                    x.StatoLavorazione,
                    x.SoftwareDescrizione,
                    Link = this.UrlAccessoPratica(x.IdAccessoAtti, x.UUID)
                });
                rptPraticheFascicolo.DataBind();
            }
        }
    }
}