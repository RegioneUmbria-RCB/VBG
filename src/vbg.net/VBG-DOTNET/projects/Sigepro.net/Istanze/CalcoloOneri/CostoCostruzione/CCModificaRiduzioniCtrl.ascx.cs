using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.Utils.Web.UI;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class CCModificaRiduzioniCtrl : Ninject.Web.UserControlBase
    {
        [Inject]
        public IAuthenticationInfoResolver _authInfoResolver { get; set; }

        public int TContributoId
        {
            get => (int)this.ViewState["TContributoId"];
            set => this.ViewState["TContributoId"] = value;
        }

        public event EventHandler<Exception> ErroreSalvataggio;
        public event EventHandler SalvataggioRiuscito;
        public event EventHandler Annulla;

        private int CodiceIstanza
        {
            get => (int)this.ViewState["CodiceIstanza"];
            set => this.ViewState["CodiceIstanza"] = value;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Visible = false;
        }

        public override void DataBind()
        {
            var authInfo = this._authInfoResolver.Resolve();
            var idComune = authInfo.IdComune;

            using (var db = authInfo.CreateDatabase())
            {
                var tContributo = new CCICalcoloTContributoMgr(db).GetById(idComune, this.TContributoId);

                var istanza = new IstanzeMgr(db).GetById(idComune, tContributo.Codiceistanza.GetValueOrDefault(int.MinValue));

                this.CodiceIstanza = Convert.ToInt32(istanza.CODICEISTANZA);

                var causali = new CcCausaliRiduzioniTMgr(db).GetListByIdcomuneSoftware(idComune, istanza.SOFTWARE);
                this.rptTipiCausali.DataSource = causali;
                this.rptTipiCausali.DataBind();
            }

            this.Visible = true;
        }

        protected void cmdSalvaNote_Click(object sender, EventArgs e) { }


        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            this.Annulla?.Invoke(this, EventArgs.Empty);

            this.Visible = false;
        }


        protected void rptTipiCausali_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var authInfo = this._authInfoResolver.Resolve();
                var idComune = authInfo.IdComune;

                using (var db = authInfo.CreateDatabase())
                {

                    var dgCausali = (DataGrid)e.Item.FindControl("dgCausali");

                    var tipoCausale = (CcCausaliRiduzioniT)e.Item.DataItem;

                    dgCausali.DataSource = new CcICalcoloTContributoRiduzMgr(db).GetImportiRiduzioni(idComune, tipoCausale.Id.GetValueOrDefault(int.MinValue), this.TContributoId);
                    dgCausali.DataBind();
                }
            }
        }

        protected void bmModificaRiduzioni_OkClicked(object sender, EventArgs e)
        {
            var authInfo = this._authInfoResolver.Resolve();
            var idComune = authInfo.IdComune;

            try
            {
                var listaRiduzioni = new List<CcICalcoloTContributoRiduz>();

                foreach (RepeaterItem rptItm in this.rptTipiCausali.Items)
                {
                    var dgCausali = (DataGrid)rptItm.FindControl("dgCausali");

                    var riduzioni = dgCausali.Items
                                             .Cast<DataGridItem>()
                                             .Select(item => new
                                             {
                                                 ChkSelezionato = (CheckBox)item.FindControl("chkSelezionato"),
                                                 DtbImporto = (DecimalTextBox)item.FindControl("dtbImporto"),
                                                 TxtNoteImporto = (TextBox)item.FindControl("txtNoteImporto"),
                                                 DataKey = Convert.ToInt32(dgCausali.DataKeys[item.ItemIndex])
                                             })
                                             .Where(x => x.ChkSelezionato.Checked)
                                             .Select(x => new CcICalcoloTContributoRiduz
                                             {
                                                 Idcomune = idComune,
                                                 Codiceistanza = this.CodiceIstanza,
                                                 FkCccrrId = x.DataKey,
                                                 FkCcictcId = this.TContributoId,
                                                 Riduzioneperc = x.DtbImporto.ValoreDecimal,
                                                 Note = x.TxtNoteImporto.Text
                                             });

                    listaRiduzioni.AddRange(riduzioni);
                }

                using (var db = authInfo.CreateDatabase())
                {
                    var contribRiduzMgr = new CcICalcoloTContributoRiduzMgr(db);

                    contribRiduzMgr.AggiornaRiduzioniByIdTContributo(idComune, this.TContributoId, listaRiduzioni);

                    this.SalvataggioRiuscito?.Invoke(this, EventArgs.Empty);
                }
            }
            catch (Exception ex)
            {
                this.ErroreSalvataggio?.Invoke(this, ex);
            }

            this.Visible = false;
        }
    }
}