using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	internal class ModelloDinamicoTableRenderer
	{

		public event ErroreCreazioneControlloDelegate ErroreCreazioneControllo;

		private readonly ModelloDinamicoBase _modello;
		private readonly IMascheraSolaLettura _campiInSolaLettura;
		private readonly bool _compatibilitaOpenOffice;
		private readonly Action<int> _callbackAggiuntaBlocco;
		private readonly Action<int, int> _callbackEliminazioneBlocco;
		private readonly Action<string> _callbackErroreCreazioneControllo;

		private ICampiNonVisibili _campiNonVisibili;

		internal ModelloDinamicoTableRenderer(ModelloDinamicoBase modello, IMascheraSolaLettura campiInSolaLettura, bool compatibilitaOpenOffice, Action<int> callbackAggiuntaBlocco, Action<int, int> callbackEliminazioneBlocco)
		{
			this._modello = modello;
			this._campiInSolaLettura = campiInSolaLettura;
			this._compatibilitaOpenOffice = compatibilitaOpenOffice;
			this._callbackAggiuntaBlocco = callbackAggiuntaBlocco;
			this._callbackEliminazioneBlocco = callbackEliminazioneBlocco;
			this._callbackErroreCreazioneControllo = (msg) => { if (ErroreCreazioneControllo != null)ErroreCreazioneControllo(msg); };
			this._campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;
		}

		internal void SetCampiNonVisibili(ICampiNonVisibili campiNonVisibili)
		{
			this._campiNonVisibili = campiNonVisibili;
		}

		internal void RenderTo(HtmlTable tabella/*, OnValueChangedDelegate valueChangedDelegate*/)
		{
			tabella.Controls.Clear();
			tabella.Attributes.Add("class", "tabellaDatiDinamici");

			if (_compatibilitaOpenOffice)
				tabella.Attributes.Add("width", "100%");

			int maxColCount = 0;

			for (int indiceRiga = 0; indiceRiga < this._modello.Righe.Count(); indiceRiga++)
			{
				IRigaRenderizzata row;
				var rigaModello = this._modello.Righe.ElementAt(indiceRiga);

				if (rigaModello.NumeroColonne == 0)
					continue;

				if (rigaModello.RigaMultipla)
				{
					// Se la riga è multipla e le righe successive (escluse quelle che hanno 0 colonne) 
					// sono multiple allora creo un blocco
					var righeMultipleConsecutive = GetRigheMultipleConsecutive(this._modello.Righe, indiceRiga);

					if (righeMultipleConsecutive.Count() == 1)
					{
						row = CreaRigaMultipla(rigaModello);
					}
					else
					{
						row = CreaBloccoMultiplo(righeMultipleConsecutive);
						indiceRiga = righeMultipleConsecutive.Last().NumeroRiga;
					}
				}
				else
				{
					row = CreaRigaSingola(rigaModello);
				}

				maxColCount = Math.Max( maxColCount , row.NumeroCelle );

				if (row.NumeroControlli > 0)
					tabella.Rows.Add(row.AsHtmlRow());
			}


			// Aggiusto il valore del colspan delle celle appartenenti alle righe con un numero di celle < di maxColCount
			for (int rowIdx = 0; rowIdx < tabella.Rows.Count; rowIdx++)
			{
				int celleInRiga = tabella.Rows[rowIdx].Cells.Count;

				if (celleInRiga < maxColCount && celleInRiga > 0)
					tabella.Rows[rowIdx].Cells[celleInRiga - 1].ColSpan = maxColCount - (celleInRiga - 1);
			}
		}
		
		private IEnumerable<RigaModelloDinamico> GetRigheMultipleConsecutive(IEnumerable<RigaModelloDinamico> righeModello, int indiceDiPartenza)
		{
			for (var i = indiceDiPartenza; i < righeModello.Count(); i++)
			{
				var riga = righeModello.ElementAt(i);

				if (riga.NumeroColonne == 0)
					continue;

				if (riga.RigaMultipla)
					yield return riga;
				else
					yield break;
			}
		}

		private IRigaRenderizzata CreaRigaSingola(RigaModelloDinamico rigaScheda)
		{
			return CreaRigaSingola(rigaScheda, 0);
		}

		private IRigaRenderizzata CreaRigaSingola(RigaModelloDinamico rigaScheda,  int indiceMolteplicitaValore)
		{
			var renderer = new RigaSingolaRenderer(this._campiInSolaLettura, this._campiNonVisibili, this._callbackErroreCreazioneControllo);
			return renderer.Render(rigaScheda, indiceMolteplicitaValore);
		}

		private TabellaDatiMultipli CreaRigaMultipla(RigaModelloDinamico rigaScheda)
		{
			bool solaLettura = this._campiInSolaLettura.ContieneAlmenoUnCampo(rigaScheda.Campi.Where(x => x.Id > 0));

			var renderer = new RigaMultiplaRenderer( solaLettura,
													 this._compatibilitaOpenOffice,
													 this._campiNonVisibili,
													 this._callbackAggiuntaBlocco,
													 this._callbackEliminazioneBlocco, 
													 this._callbackErroreCreazioneControllo );

			return renderer.Render(rigaScheda);
		}

		private IRigaRenderizzata CreaBloccoMultiplo(IEnumerable<RigaModelloDinamico> righeMultipleSuccessive)
		{
			var solaLettura = false;

			foreach (var riga in righeMultipleSuccessive)
			{
				if (this._campiInSolaLettura.ContieneAlmenoUnCampo(riga.Campi.Where(x => x.Id > 0)))
				{
					solaLettura = true;
					break;
				}
			}

			var renderer = new BloccoMultiploRenderer( solaLettura,
														this._compatibilitaOpenOffice,
														this._campiNonVisibili,
														this._callbackAggiuntaBlocco,
														this._callbackEliminazioneBlocco,
														this._callbackErroreCreazioneControllo);

			return renderer.Render(righeMultipleSuccessive);
		}

		private void OnErroreCreazioneControllo(string errMessage)
		{
			if (ErroreCreazioneControllo != null)
				ErroreCreazioneControllo(errMessage);
		}
	}
}
