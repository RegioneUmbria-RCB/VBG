using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneEndoV2GrigliaEndo : System.Web.UI.UserControl
    {
        public bool ModificaProcedimentiProposti
        {
            get { var o = this.ViewState["ModificaProcedimentiProposti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ModificaProcedimentiProposti"] = value; }
        }

        public bool ForzaSelezioneRichiesti
        {
            get { var o = this.ViewState["ForzaSelezioneRichiesti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ForzaSelezioneRichiesti"] = value; }
        }

        public bool MostraFamiglia
        {
            get { var o = this.ViewState["MostraFamiglia"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraFamiglia"] = value; }
        }

        public bool MostraTipoEndo
        {
            get { var o = this.ViewState["MostraTipoEndo"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraTipoEndo"] = value; }
        }

        public bool MostraInformazioniEndo
        {
            get { var o = this.ViewState["MostraInformazioniEndo"]; return o == null || (bool)o; }
            set { this.ViewState["MostraInformazioniEndo"] = value; }
        }

        public FamigliaEndoprocedimentoDto[] DataSource { get; set; }

        public override void DataBind()
        {
            /*
			foreach (var famiglia in DataSource)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{
					tipo.Endoprocedimenti = tipo.Endoprocedimenti.OrderBy(x => x.Ordine).ThenBy(x => x.Descrizione).ToList(); 
				}
			}
			*/

            this.rptFamiglieEndo.DataSource = this.DataSource;
            this.rptFamiglieEndo.DataBind();
        }

        public IEnumerable<SubEndoprocedimentoSelezionato> GetEndoSelezionati()
        {
            return this.GetSelected(this.rptFamiglieEndo);
        }

        private IEnumerable<SubEndoprocedimentoSelezionato> GetSelected(Repeater repeater)
        {
            var itemsFilter = new Func<RepeaterItem, bool>(x => x.ItemType == ListItemType.Item || x.ItemType == ListItemType.AlternatingItem);
            var rVal = new List<SubEndoprocedimentoSelezionato>();

            foreach (var famiglieItem in repeater.Items.Cast<RepeaterItem>().Where(itemsFilter))
            {
                var rptTipi = (Repeater)famiglieItem.FindControl("rptTipiEndo");

                foreach (var tipiItem in rptTipi.Items.Cast<RepeaterItem>().Where(itemsFilter))
                {
                    var rptEndo = (Repeater)tipiItem.FindControl("rptEndo");

                    foreach (var endoItem in rptEndo.Items.Cast<RepeaterItem>().Where(itemsFilter))
                    {
                        var chkSelezionato = (CheckBox)endoItem.FindControl("chkEndo");
                        var hidIdEndo = (HiddenField)endoItem.FindControl("hidEndo");
                        var rptSubEndo = (Repeater)endoItem.FindControl("rptSubEndo");
                        var idPadre = Convert.ToInt32(hidIdEndo.Value);

                        if (chkSelezionato.Checked)
                        {
                            rVal.Add(new SubEndoprocedimentoSelezionato(idPadre));

                            foreach (var subEndo in rptSubEndo.Items.Cast<RepeaterItem>().Where(itemsFilter))
                            {
                                var chkSubEndo = (CheckBox)subEndo.FindControl("chkSelezionato");
                                var hidIdSubEndo = (HiddenField)subEndo.FindControl("hidIdEndo");
                                var idSubEndo = Convert.ToInt32(hidIdSubEndo.Value);

                                if (chkSubEndo.Checked)
                                {
                                    rVal.Add(new SubEndoprocedimentoSelezionato(idSubEndo, idPadre));
                                }
                            }
                        }
                    }
                }
            }

            return rVal;
        }

        protected void rptEndo_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptSubEndo = (Repeater)e.Item.Controls.Cast<Control>().Where(x => x is Repeater).FirstOrDefault();
                var chkSelezionato = (CheckBox)e.Item.FindControl("chkEndo");
                var endo = (EndoprocedimentoDto)e.Item.DataItem;

                var subEndo = endo.SubEndo?.SelectMany(x => x.ListaEndo);

                if (endo.Principale || (this.ForzaSelezioneRichiesti && endo.Richiesto))
                {
                    chkSelezionato.Checked = true;
                    chkSelezionato.Enabled = this.ModificaProcedimentiProposti;
                }

                if (subEndo != null && subEndo.Any())
                {
                    rptSubEndo.DataSource = subEndo;
                    rptSubEndo.DataBind();
                }
            }
        }

        protected void rptSubEndo_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var chkSelezionato = (CheckBox)e.Item.FindControl("chkSelezionato");

                // SCHIFEZZA! Ritrova il checkbox dell'endo padre per verificare che sia richiesto
                var parentCheckbox = (CheckBox)e.Item.Parent.Parent.FindControl("chkEndo");

                if (parentCheckbox.Checked && (e.Item.DataItem as dynamic).Richiesto)
                {
                    chkSelezionato.Checked = true;
                    chkSelezionato.Enabled = parentCheckbox.Enabled;
                }
            }
        }
    }
}