using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Tares;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Tares
{
	public partial class RecuperoDatiSchedeDinamiche : IstanzeStepPage
	{
		[Inject]
		protected TaresBariService _taresBariService { get; set; }

		public int IdCampoIdContribuente
		{
			get { object o = this.ViewState["IdCampoIdContribuente"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IdCampoIdContribuente"] = value; }
		}

		public string IdCampoIdUtenza
		{
			get { object o = this.ViewState["IdCampoIdUtenza"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdCampoIdUtenza"] = value; }
		}

		public string IdCampoVia
		{
			get { object o = this.ViewState["IdCampoVia"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoVia"] = value; }
		}

		public string IdCampoCivico
		{
			get { object o = this.ViewState["IdCampoCivico"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoCivico"] = value; }
		}

		public string IdcampoPalazzo
		{
			get { object o = this.ViewState["IdcampoPalazzo"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdcampoPalazzo"] = value; }
		}

		public string IdCampoScala
		{
			get { object o = this.ViewState["IdCampoScala"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoScala"] = value; }
		}

		public string IdCampoPiano
		{
			get { object o = this.ViewState["IdCampoPiano"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoPiano"] = value; }
		}

		public string IdCampoInterno
		{
			get { object o = this.ViewState["IdCampoInterno"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoInterno"] = value; }
		}

		public string IdCampoMq
		{
			get { object o = this.ViewState["IdCampoMq"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoMq"] = value; }
		}

		public string IdCampoSezione
		{
			get { object o = this.ViewState["IdCampoSezione"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoSezione"] = value; }
		}

		public string IdCampoFoglio
		{
			get { object o = this.ViewState["IdCampoFoglio"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoFoglio"] = value; }
		}

		public string IdCampoParticella
		{
			get { object o = this.ViewState["IdCampoParticella"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoParticella"] = value; }
		}

		public string IdCampoSub
		{
			get { object o = this.ViewState["IdCampoSub"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoSub"] = value; }
		}

		public string IdCampoInizioUtenza
		{
			get { object o = this.ViewState["IdCampoInizioUtenza"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoInizioUtenza"] = value;}
		}

		public string IdCampoVariazioneUtenza
		{
			get { object o = this.ViewState["IdCampoVariazioneUtenza"]; return o == null ? String.Empty : o.ToString(); }
			set { this.ViewState["IdCampoVariazioneUtenza"] = value; }
		}





		
		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public override bool CanEnterStep()
		{
			var mappa = new TaresBariService.MappaturaCampiSchede();

			mappa.IdCampoIdContribuente = this.IdCampoIdContribuente;

			mappa.SetIdCampoVia( this.IdCampoVia );
			mappa.SetIdCampoIdUtenza( this.IdCampoIdUtenza);
			mappa.SetIdCampoCivico( this.IdCampoCivico);
			mappa.SetIdcampoPalazzo( this.IdcampoPalazzo);
			mappa.SetIdCampoScala( this.IdCampoScala);
			mappa.SetIdCampoPiano( this.IdCampoPiano);
			mappa.SetIdCampoInterno( this.IdCampoInterno);
			mappa.SetIdCampoMq(this.IdCampoMq);
			mappa.SetIdCampoSezione(this.IdCampoSezione);
			mappa.SetIdCampoFoglio(this.IdCampoFoglio);
			mappa.SetIdCampoParticella(this.IdCampoParticella);
			mappa.SetIdCampoSub(this.IdCampoSub);			
			mappa.SetIdCampoInizioUtenza(this.IdCampoInizioUtenza);
			mappa.SetIdCampoVariazioneUtenza(this.IdCampoVariazioneUtenza);

			this._taresBariService.PopolaCampiSchede(IdDomanda, mappa);

			return false;
		}
	}
}