using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;


namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	public partial class ImmagineFrontoffice : Image
	{
		//Image m_image = new Image();
		/// <summary>
		/// Id della risorsa da mostrare
		/// </summary>
		public string IdRisorsa
		{
			get { object o = this.ViewState["IdRisorsa"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdRisorsa"] = value; }
		}

		/// <summary>
		/// Se impostato rappresenta l'url della pagina che mostra gli oggetti del frontoffice
		/// </summary>
		public string RedirPage
		{
			get { object o = this.ViewState["RedirPage"]; return o == null ? "~/MostraRisorsa.ashx" : (string)o; }
			set { this.ViewState["RedirPage"] = value; }
		}


		public ImmagineFrontoffice()
		{
			//m_image.ID = "ImmagineFrontoffice";

			//this.Controls.Add(m_image);
		}

		protected override void OnPreRender(EventArgs e)
		{
			if (!(this.Page is IIdComunePage))
				throw new Exception("il controllo web ImmagineFrontoffice può essere inserito solo in un oggetto del tipo IIdComunePage. il tipo contenitore è " + this.Page.ToString());

			string idComune = ((IIdComunePage)this.Page).IdComune;
			StringBuilder sb = new StringBuilder();
			sb.Append(RedirPage );

			sb.Append(RedirPage.IndexOf("?") == -1 ? "?" : "&");
			sb.Append("IdComune=").Append(idComune).Append("&");
			sb.Append("IdRisorsa=").Append(IdRisorsa);

			this.ImageUrl = sb.ToString();


			base.OnPreRender(e);
		}
	}
}
