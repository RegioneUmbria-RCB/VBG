// -----------------------------------------------------------------------
// <copyright file="RigaMultiplaRenderer.cs" company="">
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
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
	using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class RigaMultiplaRenderer : RigaRendererBase
	{
		bool _inSolaLettura;
		bool _compatibilitaOpenOffice;
		Action<int,int> _callbackEliminazioneRiga;
		Action<int> _callbackAggiuntaRiga;
		ICampiNonVisibili _campiNonVisibili;

		public RigaMultiplaRenderer(bool inSolaLettura, bool compatibilitaOpenOffice, ICampiNonVisibili campiNonVisibili, Action<int> callbackAggiuntaRiga, Action<int, int> callbackEliminazioneRiga, Action<string> callbackErroreCreazioneControllo)
			: base(callbackErroreCreazioneControllo)
		{
			this._inSolaLettura				 = inSolaLettura;
			this._compatibilitaOpenOffice	 = compatibilitaOpenOffice;
			this._callbackEliminazioneRiga = callbackEliminazioneRiga;
			this._callbackAggiuntaRiga	 = callbackAggiuntaRiga;

			this._campiNonVisibili = campiNonVisibili;
		}

		internal TabellaDatiMultipli Render(RigaModelloDinamico rigaScheda)
		{
			var tabellaDatiMultipli = new TabellaDatiMultipli(this._compatibilitaOpenOffice, this._inSolaLettura, rigaScheda.IdGruppo);

			if (rigaScheda.NumeroColonne == 0)
				return tabellaDatiMultipli;
			
			// Colcolo da quante righe è composta la tabella
			var numeroRigheMultiple = rigaScheda.CalcolaMolteplicita();
			var mostraBottoneEliminaRiga = numeroRigheMultiple > 1;

			tabellaDatiMultipli.AggiungiIntestazione(EstraiIntestazioniDaRigaScheda(rigaScheda), mostraBottoneEliminaRiga);

			for (int indiceMolteplicita = 0; indiceMolteplicita < numeroRigheMultiple; indiceMolteplicita++)
			{
				var rigaMultipla = new RigaMultipla(indiceMolteplicita);

				for (int indiceColonna = 0; indiceColonna < rigaScheda.NumeroColonne; indiceColonna++)
				{
					var campoScheda = rigaScheda[indiceColonna];
					var cssClassCampo = GetClasseCssCella(campoScheda, indiceMolteplicita);

					if (!this._campiNonVisibili.ValoreVisibile(campoScheda.Id, indiceMolteplicita))
					{
						rigaMultipla.AggiungiCellaVuota(cssClassCampo);
						continue;
					}

					var idCampoScheda = (campoScheda.Id < 0 ? indiceColonna * -1 : campoScheda.Id); // i campi testuali hanno id == -1

					var idControllo = new IdControlloInputRigaMultipla(idCampoScheda, rigaScheda.NumeroRiga, indiceMolteplicita);

					var campo = new CampoDinamicoRenderizzato(idControllo, campoScheda);

					IDatiDinamiciControl dynControl = campo.CreaControllo(this._inSolaLettura, (errMsg) =>
					{
						NotificaErroreCreazioneControllo(errMsg);
					});

					rigaMultipla.AggiungiCampoDinamico(cssClassCampo, dynControl);
				}

				if (!this._inSolaLettura && mostraBottoneEliminaRiga)
				{
					var bottone = new BottoneEliminaRiga(rigaScheda.IdGruppo, indiceMolteplicita, new EventHandler(lnkEliminaBlocco_Click));
					rigaMultipla.AggiungiBottoneElimina(bottone);
				}

				tabellaDatiMultipli.AggiungiRigaMultipla(rigaMultipla);
			}

			// aggiungo un bottone per l'incremento della molteplicita
			if (!this._inSolaLettura)
			{
				var footer = new FooterTabellaMultipla(rigaScheda.IdGruppo, new EventHandler(cmdAggiungiBlocco_Click));

				footer.SetColSpan(rigaScheda.NumeroColonne);

				tabellaDatiMultipli.AggiungiFooter(footer);
			}

			// Restituisco la riga creata
			return tabellaDatiMultipli;
		}
		
		private IEnumerable<string> EstraiIntestazioniDaRigaScheda(RigaModelloDinamico riga)
		{
			for (int i = 0; i < riga.NumeroColonne; i++)
				yield return riga[i].Etichetta;
		}



		void lnkEliminaBlocco_Click(object sender, EventArgs e)
		{
			var lnkEliminaBlocco = (LinkButton)sender;

			var parametri = lnkEliminaBlocco.CommandArgument.Split('$');

			var idGruppo = int.Parse(parametri[0]);
			var indice = int.Parse(parametri[1]);

			this._callbackEliminazioneRiga(idGruppo, indice);
		}

		void cmdAggiungiBlocco_Click(object sender, EventArgs e)
		{
			Button cmdIncrementa = (Button)sender;

			var idGruppo = Int32.Parse(cmdIncrementa.CommandArgument);

			this._callbackAggiuntaRiga(idGruppo);
		}
	}
}
