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
    internal class TabellaDatiDinamici
    {
        
        int _maxColCount = 0;
        List<IRigaRenderizzata> _righe = new List<IRigaRenderizzata>();

        internal void Aggiungi(IRigaRenderizzata riga)
        {
            if (riga.NumeroControlli > 0)
            {
                this._maxColCount = Math.Max(this._maxColCount, riga.NumeroCelle);

                this._righe.Add(riga);
            }
        }

        internal HtmlTable GetTabellaHtml()
        {
            var table = new HtmlTable();
            table.Attributes.Add("class", "tabellaDatiDinamici");

            this._righe.ForEach(x =>
            {
                var row = x.AsHtmlRow();
                var numeroCelle = row.Cells.Count;

                if (numeroCelle < this._maxColCount && numeroCelle > 0)
                {
                    row.Cells[numeroCelle - 1].ColSpan = this._maxColCount - (numeroCelle - 1);
                }

                table.Rows.Add(row);
            });
           
            return table;
        }
    }

	internal class ModelloDinamicoTableRenderer
	{

		public event ErroreCreazioneControlloDelegate ErroreCreazioneControllo;

		private readonly ModelloDinamicoBase _modello;
		private readonly IMascheraSolaLettura _campiInSolaLettura;
		private readonly Action<int> _callbackAggiuntaBlocco;
		private readonly Action<int, int> _callbackEliminazioneBlocco;
		private readonly Action<string> _callbackErroreCreazioneControllo;
        private Panel _contenitoreCampiNascosti;

		private ICampiNonVisibili _campiNonVisibili;

		internal ModelloDinamicoTableRenderer(ModelloDinamicoBase modello, IMascheraSolaLettura campiInSolaLettura, Action<int> callbackAggiuntaBlocco, Action<int, int> callbackEliminazioneBlocco)
		{
			this._modello = modello;
			this._campiInSolaLettura = campiInSolaLettura;
			this._callbackAggiuntaBlocco = callbackAggiuntaBlocco;
			this._callbackEliminazioneBlocco = callbackEliminazioneBlocco;
			this._callbackErroreCreazioneControllo = (msg) => { if (ErroreCreazioneControllo != null)ErroreCreazioneControllo(msg); };
			this._campiNonVisibili = CampiNonVisibili.TuttiICampiVisibili;
		}

		internal void SetCampiNonVisibili(ICampiNonVisibili campiNonVisibili)
		{
			this._campiNonVisibili = campiNonVisibili;
		}

		internal void RenderTo(Panel renderingPanel)
		{
            renderingPanel.Controls.Clear();            

            _contenitoreCampiNascosti = new Panel { ID = "contenitoreCampiNascosti", CssClass="contenitore-campi-nascosti"};
            _contenitoreCampiNascosti.Style.Add(HtmlTextWriterStyle.Display, "none");

            var tabellaCorrente = new TabellaDatiDinamici();

			for (int indiceRiga = 0; indiceRiga < this._modello.Righe.Count(); indiceRiga++)
			{
				IRigaRenderizzata row;
				var rigaModello = this._modello.Righe.ElementAt(indiceRiga);

				if (rigaModello.NumeroColonne == 0)
					continue;

				if (rigaModello.TipoRiga != TipoRigaEnum.Singola)
				{
					// Se la riga è multipla e le righe successive (escluse quelle che hanno 0 colonne) 
					// sono multiple allora creo un blocco
					var righeMultipleConsecutive = GetRigheMultipleConsecutive(indiceRiga);

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

                tabellaCorrente.Aggiungi(row);

                if (rigaModello.InterrompiTabellaDopo)
                {
                    renderingPanel.Controls.Add(tabellaCorrente.GetTabellaHtml());

                    tabellaCorrente = new TabellaDatiDinamici();
                }
			}

            renderingPanel.Controls.Add(this._contenitoreCampiNascosti);

            var tabellaCampiDinamici = tabellaCorrente.GetTabellaHtml();

            renderingPanel.Controls.Add(tabellaCampiDinamici);


            //if (accumulatoreNote != null)
            //{
            //    accumulatoreNote.AnalizzaControllo(tabellaCampiDinamici);
            //}
		}
        
        private IEnumerable<RigaModelloDinamico> GetRigheMultipleConsecutive(int indiceDiPartenza)
		{
            // TODO: Risolvere con un espressione linq
            var righeModello = this._modello.Righe;

            for (var i = indiceDiPartenza; i < righeModello.Count(); i++)
			{
				var riga = righeModello.ElementAt(i);

				if (riga.NumeroColonne == 0)
					continue;

				if (riga.TipoRiga != TipoRigaEnum.Singola)
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
			var renderer = new RigaSingolaRenderer(this._campiInSolaLettura, this._campiNonVisibili, this._callbackErroreCreazioneControllo, this._contenitoreCampiNascosti);
			return renderer.Render(rigaScheda, indiceMolteplicitaValore);
		}

		private TabellaDatiMultipli CreaRigaMultipla(RigaModelloDinamico rigaScheda)
		{
			bool solaLettura = this._campiInSolaLettura.ContieneAlmenoUnCampo(rigaScheda.Campi.Where(x => x.Id > 0));

			var renderer = new RigaMultiplaRenderer( solaLettura,

													 this._campiNonVisibili,
													 this._callbackAggiuntaBlocco,
													 this._callbackEliminazioneBlocco, 
													 this._callbackErroreCreazioneControllo,
                                                     this._contenitoreCampiNascosti);

			return renderer.Render(rigaScheda);
		}

		private IRigaRenderizzata CreaBloccoMultiplo(IEnumerable<RigaModelloDinamico> righeMultipleSuccessive)
		{
			var solaLettura = false;

			foreach (var riga in righeMultipleSuccessive)
			{
				if (this._campiInSolaLettura.ContieneAlmenoUnCampo(riga.Campi.Where(x => x != null && x.Id > 0)))
				{
					solaLettura = true;
					break;
				}
			}

			var renderer = new BloccoMultiploRenderer( solaLettura,
														this._campiNonVisibili,
														this._callbackAggiuntaBlocco,
														this._callbackEliminazioneBlocco,
														this._callbackErroreCreazioneControllo,
                                                        this._contenitoreCampiNascosti);

			return renderer.Render(righeMultipleSuccessive);
		}

		private void OnErroreCreazioneControllo(string errMessage)
		{
			if (ErroreCreazioneControllo != null)
				ErroreCreazioneControllo(errMessage);
		}
	}
}
