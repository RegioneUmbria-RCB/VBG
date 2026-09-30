// -----------------------------------------------------------------------
// <copyright file="BloccoMultiploRenderer.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Web.UI.HtmlControls;
	using System.Web.UI.WebControls;
	using System.Web.UI;
	using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
	using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BloccoMultiploRenderer : RigaRendererBase
	{
		public static class Constants
		{
			public const string LabelBottoneAggiungiBlocco = "Aggiungi";
		}

		bool _solaLettura;
		Action<int> _callbackAggiuntaBlocco;
		Action<int, int> _callbackEliminazioneBlocco;
		ICampiNonVisibili _campiNonVisibili;


		public BloccoMultiploRenderer(bool solaLettura, ICampiNonVisibili campiNonVisibili, Action<int> callbackAggiuntaBlocco, Action<int, int> callbackEliminazioneBlocco, Action<string> callbackErroreCreazioneControllo, WebControl contenitoreCampiNascosti)
			: base(callbackErroreCreazioneControllo, contenitoreCampiNascosti)
		{
			this._solaLettura = solaLettura;
			this._callbackAggiuntaBlocco = callbackAggiuntaBlocco;
			this._callbackEliminazioneBlocco = callbackEliminazioneBlocco;

			this._campiNonVisibili = campiNonVisibili;
		}

		private void AggiungiAttributiGruppoAControllo(HtmlControl ctrl, int idGruppo)
		{
			var oldClass = ctrl.Attributes["class"];

            ctrl.Attributes.Add("class", oldClass + " d2groupid_" + idGruppo);
			ctrl.Attributes.Add("data-d2-group", idGruppo.ToString());
		}

		internal BloccoMultiplo Render(IEnumerable<RigaModelloDinamico> righeMultipleSuccessive)
		{
			var bloccoMultiplo = new BloccoMultiplo();

			var numeroBlocchiTotali = righeMultipleSuccessive.Select(r => r.CalcolaMolteplicita()).Max();

			var idGruppo = righeMultipleSuccessive.ElementAt(0).IdGruppo;
			var numeroRigheMultipleSuccessive = righeMultipleSuccessive.Count();
            var isMultiploNonModificabile = righeMultipleSuccessive.First().TipoRiga == TipoRigaEnum.MultiplaNonModificabile;

            for (int indiceBlocco = 0; indiceBlocco < numeroBlocchiTotali; indiceBlocco++)
            {
                var blocco = new Blocco();

				blocco.AssegnaGruppo(idGruppo);

				// Se il modello non è in sola lettura aggiungo il bottone per eliminare il blocco
                if (!this._solaLettura && numeroRigheMultipleSuccessive > 1 && numeroBlocchiTotali > 1 && !isMultiploNonModificabile)
				{
					blocco.Aggiungi(CreaBottoneEliminaBlocco(idGruppo, indiceBlocco));
				}

				for (int indiceRiga = 0; indiceRiga < righeMultipleSuccessive.Count(); indiceRiga++)
				{
					var maschera = this._solaLettura ? (IMascheraSolaLettura)new MascheraSolaLetturaCompleta() : (IMascheraSolaLettura)new MascheraSolaLetturaVuota();

					var rigaRenderer = new RigaSingolaRenderer(maschera, this._campiNonVisibili, base._callbackErroreCreazioneControllo, this.ContenitoreCampiNascosti);

					var rigaInterna = rigaRenderer.Render(righeMultipleSuccessive.ElementAt(indiceRiga), indiceBlocco);// CreaRigaSingola(righeMultipleSuccessive.ElementAt(j), listaControlliCreati, indiceBlocco);

					blocco.Aggiungi(rigaInterna);
				}

				// Aggiusto il colspan di tutte le righe
				blocco.CorreggiColspanCelle();

				bloccoMultiplo.Aggiungi(blocco);
			}

			// Cella del bottone Aggiungi
            if (!this._solaLettura && !isMultiploNonModificabile)
				bloccoMultiplo.Aggiungi(CreaBottoneAggiungiBlocco(idGruppo));

			return bloccoMultiplo;
		}


		/// <summary>
		/// Genera il bottone che consente di eliminare un blocco
		/// </summary>
		/// <param name="idGruppo">id del gruppo</param>
		/// <param name="indiceMolteplicita">indice di molteplicità da eliminare</param>
		/// <returns>Controllo che consente l'eliminazione del blocco </returns>
		private Control CreaBottoneEliminaBlocco(int idGruppo, int indiceMolteplicita)
		{
			var divContenitore = new HtmlGenericControl("div");
			divContenitore.Attributes.Add("class", "divEliminazioneBlocco");


			var lnkEliminaBlocco = new LinkButton
			{
				ID = String.Format("{0}_{1}_elimina", idGruppo, indiceMolteplicita),
				Text = "Elimina",
				OnClientClick = "return confirm('Eliminare il blocco selezionato\\?');",
				CommandArgument = idGruppo + "$" + indiceMolteplicita
			};

			lnkEliminaBlocco.ToolTip = "Elimina blocco";
			lnkEliminaBlocco.Click += new EventHandler(lnkEliminaBlocco_Click);

			divContenitore.Controls.Add(lnkEliminaBlocco);

			return divContenitore;
		}

		void lnkEliminaBlocco_Click(object sender, EventArgs e)
		{
			var lnkEliminaBlocco = (LinkButton)sender;

			var parametri = lnkEliminaBlocco.CommandArgument.Split('$');

			var idGruppo = int.Parse(parametri[0]);
			var indice = int.Parse(parametri[1]);

			this._callbackEliminazioneBlocco(idGruppo, indice);
		}

		void cmdAggiungiBlocco_Click(object sender, EventArgs e)
		{
			var cmdIncrementa = (IButtonControl)sender;

			var idGruppo = Int32.Parse(cmdIncrementa.CommandArgument);

			this._callbackAggiuntaBlocco(idGruppo);

            var script = "if (window.focusElemento){window.focusElemento('.d2groupid_0:last')}";
            (cmdIncrementa as LinkButton).Page.ClientScript.RegisterClientScriptBlock(cmdIncrementa.GetType(), "scroll-ultimo-blocco", script, true);
		}

		HtmlGenericControl CreaBottoneAggiungiBlocco(int idGruppo)
		{
			var divBottone = new HtmlGenericControl("div");

			AggiungiAttributiGruppoAControllo(divBottone, idGruppo);

			var bottoneAggiungi = new LinkButton
			{
				ID = $"btnAggiungi{idGruppo}",
				Text = Constants.LabelBottoneAggiungiBlocco,
				CommandArgument = idGruppo.ToString()
			};

			bottoneAggiungi.Click += new EventHandler(cmdAggiungiBlocco_Click);

			divBottone.Controls.Add(bottoneAggiungi);

			return divBottone;
		}

	}
}
