using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Bari.TARES;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class BenvenutoTares : ReservedBasePage
	{
        public class SubMenuBindingItem
        {
            string _gliph;

            public string Titolo { get; set; }
            public string DescrizioneEstesa { get; set; }
            public string Url { get; set; }
            public string UrlIcona { get; set; }

            public string GlyphIcon
            {
                set { this._gliph = value; }
                get { return String.Format("<i class=\"glyphicon {0}\"></i>", this._gliph); }
            }

            public bool UseGliph
            {
                get
                {
                    return !String.IsNullOrEmpty(this._gliph);
                }
            }

            public bool UseIcona
            {
                get
                {
                    return !this.UseGliph;
                }
            }
        }

        public class MenuBindingItem
        {
            public IEnumerable<SubMenuBindingItem> Items { get; set; }
        }


        [Inject]
        public MenuService _menuService { get; set; }

        public bool MostraDescrizionePagina
        {
            get { object o = this.ViewState["MostraDescrizionePagina"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDescrizionePagina"] = value; }
        }



        [Inject]
		public BariTaresService _servizioTares { get; set; }





		protected void Page_Load(object sender, EventArgs e)
		{
            this.Master.NascondiTitoloPagina = true;

            this.VerificaAppartenenzaACaf();

            if (!IsPostBack)
			{
				DataBind();
			}
		}

		private void VerificaAppartenenzaACaf()
		{
			if (!_servizioTares.OperatoreAppartieneACaf(UserAuthenticationResult.DatiUtente.Codicefiscale))
				ErroreAccessoPagina.Mostra(IdComune, Software, ErroreAccessoPagina.TipoErroreEnum.AccessoNegato);
		}

        public override void DataBind()
        {
            var menu = this._menuService.LoadMenu();

            this.Title = "Scrivania Virtuale";
            this.descrizionePagina.Text = menu.Descrizione;
            this.MostraDescrizionePagina = !String.IsNullOrEmpty(this.descrizionePagina.Text);

            var bindingSource = new List<MenuBindingItem>();
            var items = new List<SubMenuBindingItem>();


            var vociMenuHomePage = menu.GetVociHomePage();

            for (int i = 0; i < vociMenuHomePage.Count(); i++)
            {
                var voce = vociMenuHomePage.ElementAt(i);
                var item = new SubMenuBindingItem
                {
                    DescrizioneEstesa = voce.Descrizione,
                    Titolo = voce.Titolo,
                    Url = BuildClientUrl(voce.Url),
                    GlyphIcon = voce.IconaBootstrap,
                    UrlIcona = voce.UrlIcona
                };

                items.Add(item);

                if (i % 2 == 1)
                {
                    bindingSource.Add(new MenuBindingItem
                    {
                        Items = items
                    });
                    items = new List<SubMenuBindingItem>();
                }
            }

            if (vociMenuHomePage.Count() % 2 == 1)
            {
                bindingSource.Add(new MenuBindingItem
                {
                    Items = items
                });
            }

            rptMenu.DataSource = bindingSource;
            rptMenu.DataBind();
        }

        protected void rptSubMenu_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptSubMenu = (Repeater)e.Item.FindControl("rptSubMenu");
                var dataItem = (MenuBindingItem)e.Item.DataItem;

                rptSubMenu.DataSource = dataItem.Items;
                rptSubMenu.DataBind();
            }
        }
    }
}