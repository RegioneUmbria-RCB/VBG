using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ImuBari
{
	public partial class RecuperoDatiSchedeDinamiche : IstanzeStepPage
	{
		[Inject]
		protected ImuBariService _tasiBariService { get; set; }
		

		public string IdContribuente
		{
			get
			{
				return this.ViewState["IdContribuente"].ToString();
			}
			set
			{
				this.ViewState["IdContribuente"] = value;
			}
		}

		public string CodContribuente
		{
			get
			{
				return this.ViewState["CodContribuente"].ToString();
			}
			set
			{
				this.ViewState["CodContribuente"] = value;
			}
		}

		public string IdImmobile
		{
			get
			{
				return this.ViewState["IdImmobile"].ToString();
			}
			set
			{
				this.ViewState["IdImmobile"] = value;
			}
		}

		public string Via
		{
			get
			{
				return this.ViewState["Via"].ToString();
			}
			set
			{
				this.ViewState["Via"] = value;
			}
		}

		public string IdVia
		{
			get
			{
				return this.ViewState["IdVia"].ToString();
			}
			set
			{
				this.ViewState["IdVia"] = value;
			}
		}

		public string Civico
		{
			get
			{
				return this.ViewState["Civico"].ToString();
			}
			set
			{
				this.ViewState["Civico"] = value;
			}
		}

		public string Esponente
		{
			get
			{
				return this.ViewState["Esponente"].ToString();
			}
			set
			{
				this.ViewState["Esponente"] = value;
			}
		}

		public string Palazzo
		{
			get
			{
				return this.ViewState["Palazzo"].ToString();
			}
			set
			{
				this.ViewState["Palazzo"] = value;
			}
		}

		public string Scala
		{
			get
			{
				return this.ViewState["Scala"].ToString();
			}
			set
			{
				this.ViewState["Scala"] = value;
			}
		}

		public string Piano
		{
			get
			{
				return this.ViewState["Piano"].ToString();
			}
			set
			{
				this.ViewState["Piano"] = value;
			}
		}

		public string Interno
		{
			get
			{
				return this.ViewState["Interno"].ToString();
			}
			set
			{
				this.ViewState["Interno"] = value;
			}
		}

		public string Sezione
		{
			get
			{
				return this.ViewState["Sezione"].ToString();
			}
			set
			{
				this.ViewState["Sezione"] = value;
			}
		}

		public string Foglio
		{
			get
			{
				return this.ViewState["Foglio"].ToString();
			}
			set
			{
				this.ViewState["Foglio"] = value;
			}
		}

		public string Particella
		{
			get
			{
				return this.ViewState["Particella"].ToString();
			}
			set
			{
				this.ViewState["Particella"] = value;
			}
		}

		public string Sub
		{
			get
			{
				return this.ViewState["Sub"].ToString();
			}
			set
			{
				this.ViewState["Sub"] = value;
			}
		}

		public string Categoria
		{
			get
			{
				return this.ViewState["Categoria"].ToString();
			}
			set
			{
				this.ViewState["Categoria"] = value;
			}
		}

		public string Percentuale
		{
			get
			{
				return this.ViewState["Percentuale"].ToString();
			}
			set
			{
				this.ViewState["Percentuale"] = value;
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public override bool CanEnterStep()
		{
			var mappa = new MappaturaCampiSchedeImu();

			mappa.SetIdContribuente(this.IdContribuente);
			mappa.SetIdImmobile(this.IdImmobile);
			mappa.SetVia(this.Via);
			mappa.SetIdVia(this.IdVia);
			mappa.SetCivico(this.Civico);
			mappa.SetEsponente(this.Esponente);
			mappa.SetPalazzo(this.Palazzo);
			mappa.SetScala(this.Scala);
			mappa.SetPiano(this.Piano);
			mappa.SetInterno(this.Interno);
			mappa.SetSezione(this.Sezione);
			mappa.SetFoglio(this.Foglio);
			mappa.SetParticella(this.Particella);
			mappa.SetSub(this.Sub);
			mappa.SetCategoria(this.Categoria);
			mappa.SetPercentuale(this.Percentuale);

			this._tasiBariService.PopolaCampiSchede(IdDomanda, mappa);

			return false;
		}
	}
}