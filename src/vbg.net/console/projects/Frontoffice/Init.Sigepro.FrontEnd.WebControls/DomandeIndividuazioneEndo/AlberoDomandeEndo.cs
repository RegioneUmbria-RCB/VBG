using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.DomandeIndividuazioneEndo
{
	public class AlberoDomandeEndo : WebControl
	{
		public AreaIndividuazioneEndoDto[] DataSource{get;set;}

		private Dictionary<int, CheckBox> m_listaCheckboxes = new Dictionary<int, CheckBox>();

		private List<int> m_listaIdSelezionati = new List<int>();
		private bool m_dataBound = false;

		public override void DataBind()
		{
			RigeneraListaCheckBoxes(DataSource);

			SelezionaCheckboxes();
		}

		private void SelezionaCheckboxes()
		{
			foreach (var key in m_listaCheckboxes.Keys)
				m_listaCheckboxes[key].Checked = m_listaIdSelezionati.Contains(key);
		}

		private void CreaListaCheckBoxesRicorsivo(AreaIndividuazioneEndoDto[] aree)
        {
			if (aree == null || aree.Length == 0)
				return;

			foreach(var area in aree )
			{
				CreaListaCheckBoxesRicorsivo(area.SottoAree);

				if (area.Domande == null || area.Domande.Length == 0)
					continue;

				foreach (var domanda in area.Domande)
				{
					var idDomanda = domanda.Codice;

					var chkBox = new CheckBox
					{
						ID = "Domanda" + idDomanda,
						Text = domanda.Descrizione
					};

					m_listaCheckboxes.Add(idDomanda, chkBox);
					this.Controls.Add( chkBox );
				}
			}
        }

		private void RigeneraListaCheckBoxes(AreaIndividuazioneEndoDto[] aree)
		{
			m_listaCheckboxes.Clear();
			this.Controls.Clear();

			CreaListaCheckBoxesRicorsivo(aree);

			m_dataBound = true;
		}


		public override void RenderControl(System.Web.UI.HtmlTextWriter writer)
		{
			RenderArea(DataSource, writer);			
		}

		protected void RenderArea(AreaIndividuazioneEndoDto[] aree, HtmlTextWriter writer)
		{
			if (aree == null || aree.Length == 0)
				return;

			writer.RenderBeginTag( HtmlTextWriterTag.Ul );

			for (int i = 0; i < aree.Length; i++)
			{
				var area = aree[i];

				writer.AddAttribute("class", "area");
				writer.RenderBeginTag(HtmlTextWriterTag.Li);

					writer.WriteLine(area.Descrizione);

					RenderArea(area.SottoAree, writer );

					RenderDomande(area.Domande, writer);

				writer.RenderEndTag();
			}

			writer.RenderEndTag();

		}

		protected void RenderDomande(DomandaIndividuazioneEndoDto[] domande, HtmlTextWriter writer)
		{
			if (domande == null || domande.Length == 0)
				return;

			writer.AddAttribute("class", "domandeEndo");
			writer.RenderBeginTag(HtmlTextWriterTag.Ul);

				foreach (var domanda in domande)
				{
					writer.RenderBeginTag(HtmlTextWriterTag.Li);

					var idDomanda = domanda.Codice;

					var chkBox = m_listaCheckboxes[idDomanda];

					chkBox.RenderControl(writer);

					writer.RenderEndTag();
				}
			writer.RenderEndTag();
		}

		protected override object SaveViewState()
		{
			object[] vsData = new object[2];

			vsData[0] = base.SaveViewState();
			vsData[1] = DataSource;

			return vsData;
		}

		protected override void LoadViewState(object savedState)
		{
			var vsData = (object[])savedState;

			base.LoadViewState(vsData[0]);

			DataSource = (AreaIndividuazioneEndoDto[])vsData[1];

			RigeneraListaCheckBoxes(DataSource);
		}

		public List<int> IdSelezionati
		{
			get
			{
				m_listaIdSelezionati = new List<int>();

				foreach (var key in m_listaCheckboxes.Keys)
				{
					if (m_listaCheckboxes[key].Checked)
						m_listaIdSelezionati.Add(key);
				}

				return m_listaIdSelezionati;
			}

			set
			{
				m_listaIdSelezionati = value;

				if (m_dataBound)
					SelezionaCheckboxes();
			}
		}
	}
}
