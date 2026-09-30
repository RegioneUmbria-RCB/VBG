using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net.Istanze.SitFirenze
{
	public partial class geoin : BasePage
	{
		public class ModelClass
		{
			public string ID { get; set; }

			public string Foglio { get; set; }
			public string Particella { get; set; }
			public string TipoCatasto { get; set; }

			public string NomeVia { get; set; }
			public string Civico { get; set; }
			public string IdPuntoSit { get; set; }

			public ModelClass(IstanzeStradario istanzeStradario, IstanzeMappali mappalePrimario)
			{
				this.TipoCatasto = "F";

				if (mappalePrimario != null)
				{
					this.Foglio = mappalePrimario.Foglio;
					this.Particella = mappalePrimario.Particella;
					this.TipoCatasto = mappalePrimario.Codicecatasto;
				}

				this.NomeVia = SafeString(istanzeStradario.Stradario.PREFISSO) + " " +
								SafeString(istanzeStradario.Stradario.DESCRIZIONE);

				this.Civico = SafeString(istanzeStradario.CIVICO) +
							  SafeString(String.IsNullOrEmpty(istanzeStradario.ESPONENTE) ? String.Empty : "/" + istanzeStradario.ESPONENTE) +
							  SafeString(String.IsNullOrEmpty(istanzeStradario.COLORE) ? String.Empty : " " + istanzeStradario.COLORE);

				this.IdPuntoSit = istanzeStradario.IdPuntoSit;
				this.ID = istanzeStradario.ID;

			}

			private string SafeString(string str)
			{
				if (String.IsNullOrEmpty(str))
					return String.Empty;

				return str.Replace('"', '\'');
			}

			
		}

		protected string IdElemento { get { return Request.QueryString["IdElemento"]; } }

		protected int IdStradario { get { return Convert.ToInt32(Request.QueryString["IdStradario"]); } }

		protected ModelClass Model { get; private set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Model = GetIndirizzo();

			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			ddlTipoCatasto.Items.Add(new ListItem("Terreni", "T"));
			ddlTipoCatasto.Items.Add(new ListItem("Fabbricati", "F"));
			ddlTipoCatasto.SelectedValue = this.Model.TipoCatasto;
			

			base.DataBind();
		}

		public string GetScriptIniziale()
		{
			/*
			var indirizzo = GetIndirizzo();

			if (!string.IsNullOrEmpty(indirizzo.IdPuntoSit))
			{
				return @"_plugin.impostaPuntoCorrente('" + indirizzo.IdPuntoSit + "');";
			}
			*/
			return String.Empty;
		}

		private ModelClass GetIndirizzo()
		{
			using(var db = AuthenticationInfo.CreateDatabase())
			{
				var istanzeStradarioMgr = new IstanzeStradarioMgr(db);
				var istanzeMappaliMgr = new IstanzeMappaliMgr(db);


				var stradario = istanzeStradarioMgr.GetById(IdComune, IdStradario);
				var mappale = istanzeMappaliMgr.GetPrimarioByIdStradario(IdComune, IdStradario);

				return new ModelClass(stradario, mappale);
			}
		}
	}
}