//using System;
//using System.Web.UI;
//using System.Web.UI.HtmlControls;
//using System.Web.UI.WebControls;
//using System.ComponentModel;

//namespace SIGePro.Net.Controls
//{

//    public enum IntestazionePaginaTipiTab
//    {
//        Ricerca = 0,
//        Risultato,
//        Scheda
//    }

//    /// <summary>
//    /// Descrizione di riepilogo per IntestazionePagina.
//    /// </summary>
//    [DefaultProperty("Text"), 
//        ToolboxData("<{0}:IntestazionePagina runat=server></{0}:IntestazionePagina>")]
//    public class IntestazionePagina : WebControl
//    {
//        protected HtmlTable m_table = new HtmlTable();
//        protected Image m_spacer1 = new Image();
//        protected Image m_spacer2 = new Image();
//        protected Label m_lblTitolo = new Label();
//        protected Image m_imgRicerca = new Image();
//        protected Image m_imgScheda = new Image();
//        protected Image m_imgRisultato = new Image();
	
//        private string[] imgNames = {"{0}tab_ricerca_{1}.gif", "{0}tab_risultato_{1}.gif", "{0}tab_scheda_{1}.gif"};
//        private Image[] m_imgPlaceHolders = new Image[3];

//        public const int TAB_RICERCA = 0;
//        public const int TAB_RISULTATO = 1;
//        public const int TAB_SCHEDA = 2;


//        public string TitoloPagina
//        {
//            get
//            {
//                EnsureChildControls();
//                return m_lblTitolo.Text;
//            }

//            set 
//            {
//                EnsureChildControls();
//                m_lblTitolo.Text = value;
//            }
//        }


//        public string CssClassTitolo
//        {
//            get
//            {
//                EnsureChildControls();
//                return m_lblTitolo.CssClass;
				
//            }

//            set
//            {
//                EnsureChildControls();
//                m_lblTitolo.CssClass = value;
//            }
//        }


//        public IntestazionePaginaTipiTab TabSelezionato
//        {
//            get
//            {
//                object o = this.ViewState["SelectedTab"];
//                return (o == null) ? IntestazionePaginaTipiTab.Ricerca : (IntestazionePaginaTipiTab)o;
//            }

//            set { this.ViewState["SelectedTab"] = value; }
//        }


//        public string PathImmagini
//        {
//            get
//            {
//                object o = this.ViewState["PathImmagini"];
//                return o == null ? "~/Images/" : o.ToString();
//            }
//            set{this.ViewState["PathImmagini"] = value;}
//        }




//        public IntestazionePagina()
//        {
//            this.Load += new EventHandler(this.Page_Load);
//            this.PreRender += new EventHandler(IntestazionePagina_PreRender);

//            m_lblTitolo.CssClass = "intestazionepagina";
//        }



//        protected override void CreateChildControls()
//        {
//            base.CreateChildControls ();

//            m_table.Width = "100%";

//            HtmlTableRow row0 = new HtmlTableRow();
//            HtmlTableRow row1 = new HtmlTableRow();

//            HtmlTableCell row0cell0 = new HtmlTableCell();
//            HtmlTableCell row1cell0 = new HtmlTableCell();
			
//            m_table.Rows.Add( row0 );
//            m_table.Rows.Add( row1 );

//            row0.Cells.Add( row0cell0 );
//            row1.Cells.Add( row1cell0 );

//            Literal l = new Literal();
//            l.Text = "&nbsp;&nbsp;";

//            row0cell0.Controls.Add( l );

//            row0cell0.Controls.Add( m_lblTitolo );


//            l = new Literal();
//            l.Text = "<br />";
            
//            row1cell0.Controls.Add( l );

//            row1cell0.Controls.Add( m_imgRicerca );
//            row1cell0.Controls.Add( m_spacer1 );
//            row1cell0.Controls.Add( m_imgRisultato );
//            row1cell0.Controls.Add( m_spacer2 );
//            row1cell0.Controls.Add( m_imgScheda );

//            this.Controls.Add( m_table );
//        }





//        private void Page_Load(object sender, EventArgs e)
//        {
//            m_imgPlaceHolders[0] = m_imgRicerca;
//            m_imgPlaceHolders[1] = m_imgRisultato;
//            m_imgPlaceHolders[2] = m_imgScheda;


			
//        }

//        private void IntestazionePagina_PreRender(object sender, EventArgs e)
//        {
//            //m_lblTitolo.Text = TitoloPagina;

//            m_spacer1.ImageUrl = m_spacer2.ImageUrl = String.Format( "{0}tab_pipe.gif" , PathImmagini );

//            for (int i = 0; i < m_imgPlaceHolders.Length; i++)
//            {
//                string status = (i == (int)TabSelezionato) ? "on" : "off";
//                string imgPath = String.Format(imgNames[i], PathImmagini, status);

//                m_imgPlaceHolders[i].ImageUrl = imgPath;
//            }
//        }

////		protected override void Render(HtmlTextWriter writer)
////		{
////			for (int i = 0; i < m_imgPlaceHolders.Length; i++)
////			{
////				string status = (i == TabSelezionato) ? "on" : "off";
////				string imgPath = String.Format(imgNames[i], PathImmagini, status);
////
////				writer.WriteLine( imgPath );
////			}
////			base.Render (writer);
////		}


//    }
//}
