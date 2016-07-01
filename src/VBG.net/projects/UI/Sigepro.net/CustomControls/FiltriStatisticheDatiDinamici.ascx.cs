using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;
using Init.SIGePro.Manager;
using SIGePro.Net;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.WebControls.Statistiche;

namespace Sigepro.net.CustomControls
{
	public partial class FiltriStatisticheDatiDinamici : System.Web.UI.UserControl
	{
		const string STATISTICHE_SESSION_KEY = "STATISTICHE_SESSION_KEY";

		#region properties

		public string IdModello
		{
			get { object o = this.ViewState["IdModello"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdModello"] = value; }
		}

		public string Software
		{
			get { return (this.Page as BasePage).Software; }
		}

		public string IdComune
		{
			get { return (this.Page as BasePage).IdComune; }
		}

		public DataBase Database
		{
			get { return (this.Page as BasePage).Database; }
		}

		public string Contesto
		{
			get { object o = this.ViewState["Contesto"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Contesto"] = value; }
		}


		#endregion

		protected void Page_Load(object sender, EventArgs e)
		{
			DropDownList ddl = new DropDownList();

			if (!IsPostBack)
			{
				ClearSession();

				BindComboModelli();

				DataBind();
			}
			else
			{
				ResyncRows();
			}
		}

		private void BindComboModelli()
		{
			
			List<Dyn2ModelliT> listaModelli = new Dyn2ModelliTMgr(Database).GetListaModelliPerStatistiche( IdComune , Software).ToList();
			listaModelli.Insert(0, new Dyn2ModelliT());

			ddlModello.Item.DataSource = listaModelli;
			ddlModello.Item.DataBind();
		}

		

		#region membri pubblici

		public override void DataBind()
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			if (ds == null)
				ds = CreateDataset();

			dgFiltri.DataSource = ds.DtFiltri;
			dgFiltri.DataBind();
		}

		public DsFiltriStatisticheDatiDinamici GetFiltri()
		{
			return (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];
		}

		#endregion
		

		#region membri privati

		private void ClearSession()
		{
			Session[STATISTICHE_SESSION_KEY] = null;
		}

		private DsFiltriStatisticheDatiDinamici CreateDataset()
		{
			Session[STATISTICHE_SESSION_KEY] = new DsFiltriStatisticheDatiDinamici();

			AddEmptyRowToDataSource();

			return (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];
		}

		private void AddEmptyRowToDataSource()
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			DsFiltriStatisticheDatiDinamici.DtFiltriRow row = ds.DtFiltri.NewDtFiltriRow();
			row.IdCampo = -1;
			row.Concatenazione = "And";
			ds.DtFiltri.AddDtFiltriRow(row);
		}


		protected void ListaCampiDataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
		{
			e.InputParameters["idModello"] = IdModello;
		}


		protected void dgFiltri_ItemCreated(object sender, DataGridItemEventArgs e)
		{
			if (e.Item.ItemIndex == 0)
			{
				DropDownList ddlConcatenazione = (DropDownList)e.Item.FindControl("ddlConcatenazione");
				ddlConcatenazione.Visible = false;
			}
		}

		protected void OnControlIdChanged(object sender, EventArgs e)
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			DropDownList ddl = (DropDownList)sender;
			TableCell cell = ddl.Parent as TableCell;
			DataGridItem dgit = cell.Parent as DataGridItem;

			InvalidateRow( dgit );

			DataBind();
		}

		private void InvalidateRow(DataGridItem dgit)
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			DropDownList ddlIdCampo = (DropDownList)dgit.FindControl("ddlIdCampo");

			int rowId = Convert.ToInt32(dgFiltri.DataKeys[dgit.ItemIndex]);
			DsFiltriStatisticheDatiDinamici.DtFiltriRow row = ds.DtFiltri.FindById(rowId);

			if (row != null)
			{
				row.Valore = "";
				row.Criterio = "";
				row.IdCampo = Convert.ToInt32(ddlIdCampo.SelectedValue);

				// Svuoto le vecchie proprieta del controllo
				DsFiltriStatisticheDatiDinamici.DtProprietaControlloRow[] righeProprieta = row.GetDtProprietaControlloRows();

				foreach (DsFiltriStatisticheDatiDinamici.DtProprietaControlloRow rProp in righeProprieta)
				{
					rProp.Delete();
				}

				if (row.IdCampo != -1)
				{
					// Todo: ottenere il tipo del controllo
					Dyn2CampiMgr mgr = new Dyn2CampiMgr(Database);
					Dyn2Campi campo = mgr.GetById(IdComune, row.IdCampo);

					TipoControlloEnum tipoControllo = (TipoControlloEnum)Enum.Parse(typeof(TipoControlloEnum), campo.Tipodato);
					row.Tipo = ControlliDatiDinamiciDictionary.Items[tipoControllo].TipoElemento.AssemblyQualifiedName;

					Dyn2CampiProprietaMgr propMgr = new Dyn2CampiProprietaMgr(Database);
					Dyn2CampiProprieta filtro = new Dyn2CampiProprieta();
					filtro.Idcomune = IdComune;
					filtro.FkD2cId = row.IdCampo;

					List < Dyn2CampiProprieta> props = propMgr.GetList(filtro);
					foreach (Dyn2CampiProprieta prop in props)
						ds.DtProprietaControllo.AddDtProprietaControlloRow(row, prop.Proprieta, prop.Valore);
				}
				else
				{
					row.Tipo = String.Empty;
				}
			}
		}

		private void ResyncRow(DataGridItem dgit)
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			DropDownList ddlConcatenazione = (DropDownList)dgit.FindControl("ddlConcatenazione");
			DropDownList ddlParentesiIn = (DropDownList)dgit.FindControl("ddlParentesiIn");
			DropDownList ddlIdCampo = (DropDownList)dgit.FindControl("ddlIdCampo");
			DropDownList ddlParentesiOut = (DropDownList)dgit.FindControl("ddlParentesiOut");
			DropDownList ddlTipoConfronto = (DropDownList)dgit.FindControl(StatisticheDatiDinamiciTipoConfrontoGridColumn.IdControlloTipoConfronto);
			StatisticheDatiDinamiciGridColumnControl controlloValore = (StatisticheDatiDinamiciGridColumnControl)dgit.FindControl(StatisticheDatiDinamiciGridColumn.ID_CONTROLLO_VALORE);

			int rowId = Convert.ToInt32(dgFiltri.DataKeys[ dgit.ItemIndex ]);
			DsFiltriStatisticheDatiDinamici.DtFiltriRow row = ds.DtFiltri.FindById(rowId);

			if (row != null)
			{
				row.Concatenazione = ddlConcatenazione.SelectedValue;
				row.ParentesiIn = Convert.ToBoolean(ddlParentesiIn.SelectedValue);
				row.IdCampo = Convert.ToInt32(ddlIdCampo.SelectedValue);
				row.ParentesiOut = Convert.ToBoolean(ddlParentesiOut.SelectedValue);
				row.Criterio = ddlTipoConfronto.SelectedValue;

				row.Valore = controlloValore.Value;
			}

		}

		protected void cmdAggiungiFiltro_Click(object sender, EventArgs e)
		{
			AddEmptyRowToDataSource();

			DataBind();
		}

		private void ResyncRows()
		{
			for (int i = 0; i < dgFiltri.Items.Count; i++)
			{
				ResyncRow(dgFiltri.Items[i]);
			}
		}

		public void OnControlPropertiesRequired(object sender, Dictionary<string, string> propList)
		{
			DsFiltriStatisticheDatiDinamici ds = (DsFiltriStatisticheDatiDinamici)Session[STATISTICHE_SESSION_KEY];

			Control senderCtrl = (Control)sender;
			DataGridItem dgit = (DataGridItem)senderCtrl.NamingContainer;

			int rowId = Convert.ToInt32(dgFiltri.DataKeys[dgit.ItemIndex]);
			DsFiltriStatisticheDatiDinamici.DtFiltriRow row = ds.DtFiltri.FindById(rowId);

			if (row != null)
			{
				DsFiltriStatisticheDatiDinamici.DtProprietaControlloRow[] righeProprieta = row.GetDtProprietaControlloRows();

				foreach (DsFiltriStatisticheDatiDinamici.DtProprietaControlloRow rProp in righeProprieta)
				{
					propList.Add(rProp.Proprieta, rProp.Valore);
				}
			}
		}

		#endregion

		protected void ddlModello_ValueChanged(object sender, EventArgs e)
		{
			this.IdModello = ddlModello.Value;

			ClearSession();

			DataBind();
		}
	}
}