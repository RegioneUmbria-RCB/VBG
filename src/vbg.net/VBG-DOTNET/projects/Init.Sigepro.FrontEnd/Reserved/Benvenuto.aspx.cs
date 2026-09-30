using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class Benvenuto : ReservedBasePage
    {
        public class SubMenuBindingItem
        {
            private string _gliph;

            public string Titolo { get; set; }
            public string DescrizioneEstesa { get; set; }
            public string Url { get; set; }
            public string UrlIcona { get; set; }

            public string GlyphIcon
            {
                set { this._gliph = value; }
                get { return $"<i class=\"{this._gliph}\" aria-hidden=\"true\"></i>"; }
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

            public string Target { get; internal set; }
        }

        public class MenuBindingItem
        {
            public IEnumerable<SubMenuBindingItem> Items { get; set; }
        }

        [Inject]
        public IsUtenteAnonimoSpecification IsUtenteAnonimo { get; set; }

        [Inject]
        public IMenuService _menuService { get; set; }

        public bool MostraDescrizionePagina
        {
            get { object o = this.ViewState["MostraDescrizionePagina"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDescrizionePagina"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (this.IsUtenteAnonimo.IsSatisfiedBy(this.UserAuthenticationResult))
            {
                var loginUrl = UrlBuilder.Url("~/login.aspx", x =>
                {
                    x.Add(new QsAliasComune(this.IdComune));
                    x.Add(new QsSoftware(this.Software));
                });

                var logoutUrl = UrlBuilder.Url("~/endpoints/logout.ahx", x =>
                {
                    x.Add(new QsAliasComune(this.IdComune));
                    x.Add(new QsSoftware(this.Software));
                    x.Add("f", this.Server.UrlEncode(this.ResolveClientUrl(loginUrl)));
                });

                this.Response.Redirect(logoutUrl);

                return;
            }

            this.Master.NascondiTitoloPagina = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            var menu = this._menuService.LoadMenu();

            this.Title = "Scrivania Virtuale";
            this.descrizionePagina.Text = menu.Descrizione;
            this.MostraDescrizionePagina = !String.IsNullOrEmpty(this.descrizionePagina.Text);

            var bindingSource = new List<MenuBindingItem>();
            var items = new List<SubMenuBindingItem>();


            var vociMenuHomePage = menu.VociInHomepage;

            for (int i = 0; i < vociMenuHomePage.Count(); i++)
            {
                var voce = vociMenuHomePage.ElementAt(i);
                var item = new SubMenuBindingItem
                {
                    DescrizioneEstesa = voce.Descrizione,
                    Titolo = voce.Titolo,
                    Url = voce.Url,
                    Target = voce.Target,
                    GlyphIcon = voce.Icona.ToString()
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

            this.rptMenu.DataSource = bindingSource;
            this.rptMenu.DataBind();
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