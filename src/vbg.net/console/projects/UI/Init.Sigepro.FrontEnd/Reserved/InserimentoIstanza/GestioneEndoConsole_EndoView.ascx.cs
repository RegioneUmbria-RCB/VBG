using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneEndoConsole_EndoView : System.Web.UI.UserControl
    {
        public IEnumerable<FamigliaEndoprocedimentoDto> DataSource { get; set; }

        public bool ForzaSelezioneRichiesti
        {
            get { object o = this.ViewState["ForzaSelezioneRichiesti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ForzaSelezioneRichiesti"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.rptFamiglie.DataSource = this.DataSource;
            this.rptFamiglie.DataBind();
        }

        protected void rptFamiglie_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptTipi = (Repeater)e.Item.Controls.Cast<Control>().Where(x => x is Repeater).FirstOrDefault();

                if (rptTipi != null)
                {
                    rptTipi.DataSource = (e.Item.DataItem as dynamic).TipiEndoprocedimenti;
                    rptTipi.DataBind();
                }
            }
        }

        protected void rptTipi_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptEndo = (Repeater)e.Item.Controls.Cast<Control>().Where(x => x is Repeater).FirstOrDefault();

                if (rptEndo != null)
                {
                    rptEndo.DataSource = (e.Item.DataItem as dynamic).Endoprocedimenti;
                    rptEndo.DataBind();
                }
            }
        }

        protected void rptEndo_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptSubEndo = (Repeater)e.Item.Controls.Cast<Control>().Where(x => x is Repeater).FirstOrDefault();
                var chkSelezionato = (CheckBox)e.Item.FindControl("chkSelezionato");

                var subEndo = ((EndoprocedimentoDto)e.Item.DataItem).SubEndo?.SelectMany(x => x.TipiEndoprocedimenti.SelectMany(y => y.Endoprocedimenti));

                if (this.ForzaSelezioneRichiesti && (e.Item.DataItem as dynamic).Richiesto)
                {
                    chkSelezionato.Checked = true;
                    chkSelezionato.Enabled = false;
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
                var parentCheckbox = (CheckBox)e.Item.Parent.Parent.FindControl("chkSelezionato");
                
                if (parentCheckbox.Checked && (e.Item.DataItem as dynamic).Richiesto)
                {
                    chkSelezionato.Checked = true;
                    chkSelezionato.Enabled = parentCheckbox.Enabled;
                }
            }
        }

        public IEnumerable<int> GetEndoSelezionati()
        {
            return GetSelected(rptFamiglie);            
        }

        private IEnumerable<int> GetSelected(Repeater repeater)
        {
            var itemsFilter = new Func<RepeaterItem, bool>(x => x.ItemType == ListItemType.Item || x.ItemType == ListItemType.AlternatingItem);
            var rVal = new List<int>();

            foreach (var famiglieItem in repeater.Items.Cast<RepeaterItem>().Where(itemsFilter))
            {
                var rptTipi = (Repeater)famiglieItem.FindControl("rptTipi");

                foreach (var tipiItem in rptTipi.Items.Cast<RepeaterItem>().Where(itemsFilter))
                {
                    var rptEndo = (Repeater)tipiItem.FindControl("rptEndo");

                    foreach (var endoItem in rptEndo.Items.Cast<RepeaterItem>().Where(itemsFilter))
                    {
                        var chkSelezionato = (CheckBox)endoItem.FindControl("chkSelezionato");
                        var hidIdEndo = (HiddenField)endoItem.FindControl("hidIdEndo");
                        var rptSubEndo = (Repeater)endoItem.FindControl("rptSubEndo");

                        if (chkSelezionato.Checked)
                        {
                            rVal.Add(Convert.ToInt32(hidIdEndo.Value));
                        }

                        foreach (var subEndo in rptSubEndo.Items.Cast<RepeaterItem>().Where(itemsFilter))
                        {
                            chkSelezionato = (CheckBox)subEndo.FindControl("chkSelezionato");
                            hidIdEndo = (HiddenField)subEndo.FindControl("hidIdEndo");

                            if (chkSelezionato.Checked)
                            {
                                rVal.Add(Convert.ToInt32(hidIdEndo.Value));
                            }
                        }
                        
                    }
                }
            }

            return rVal;
        }
    }
}