using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Anagrafe;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;

namespace Sigepro.net.Istanze.DatiDinamici.Storico
{
    public partial class AnagrafeDyn2Storico : BasePage
    {
        /// <summary>
        /// Id dell'anagrafica corrente
        /// </summary>
        protected int CodiceAnagrafe { get { return Convert.ToInt32(this.Request.QueryString["CodiceAnagrafe"]); } }

        /// <summary>
        /// Id modello 
        /// </summary>
        protected int IdModello { get { return Convert.ToInt32(this.Request.QueryString["IdModello"]); } }

        public override string Software
        {
            get
            {
                return "TT";
            }
        }

        public int IndiceScheda
        {
            get { object o = this.ViewState["IndiceScheda"]; return o == null ? 0 : (int)o; }
            set { this.ViewState["IndiceScheda"] = value; }
        }

        public int IndiceVersione
        {
            get { object o = this.ViewState["IndiceVersione"]; return o == null ? 0 : (int)o; }
            set { this.ViewState["IndiceVersione"] = value; }
        }




        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            this.gvLista.DataSource = new AnagrafeDyn2ModelliTStoricoMgr(this.Database).GetList(this.IdComune, this.CodiceAnagrafe, this.IdModello);
            this.gvLista.DataBind();

            Anagrafe anagrafe = new AnagrafeMgr(this.Database).GetById(this.IdComune, this.CodiceAnagrafe);
            this.ltrAnagrafica.Text = anagrafe.NOMINATIVO + " " + anagrafe.NOME;

            Dyn2ModelliT modt = new Dyn2ModelliTMgr(this.Database).GetById(this.IdComune, this.IdModello);
            this.ltrNomeModello.Text = modt.Descrizione;
        }

        private void BindDettaglio(AnagrafeDyn2ModelliTStorico cls)
        {
            this.IndiceVersione = cls.Idversione.Value;

            this.multiView.ActiveViewIndex = 1;

            // bindo la lista degli indici
            List<int> indiciVersione = new AnagrafeDyn2DatiStoricoMgr(this.Database).LeggiIndiciVersione(this.IdComune, this.CodiceAnagrafe, this.IdModello, this.IndiceVersione);

            this.IndiceScheda = indiciVersione[0];

            this.BindListaIndici();

            // Bindo iil modello
            this.BindModello();

        }

        private void BindListaIndici()
        {
            List<int> indiciVersione = new AnagrafeDyn2DatiStoricoMgr(this.Database).LeggiIndiciVersione(this.IdComune, this.CodiceAnagrafe, this.IdModello, this.IndiceVersione);

            this.rptMolteplicita.DataSource = indiciVersione;
            this.rptMolteplicita.DataBind();

            if (indiciVersione.Count < 2)
                this.rptMolteplicita.Visible = false;
        }

        private void BindModello()
        {
            var dap = new AnagrafeDyn2DataAccessFactory(this.Database, this.IdComune, this.CodiceAnagrafe);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            var modello = new BackendModelliFactory().CreaModelloAnagrafe(loader, this.IdModello, this.IndiceScheda, true, this.IndiceVersione);

            this.ModelloDinamicoRenderer1.DataSource = modello;
            this.ModelloDinamicoRenderer1.DataBind();
        }



        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (this.multiView.ActiveViewIndex)
            {
                case (1):
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                default:
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
            }
        }


        #region Scheda lista

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

            AnagrafeDyn2ModelliTStorico cls = new AnagrafeDyn2ModelliTStoricoMgr(this.Database).GetById(this.IdComune, id, this.CodiceAnagrafe, this.IdModello);

            this.BindDettaglio(cls);
        }

        protected void cmdElimina_Click(object sender, EventArgs e)
        {
            List<int> listaId = new List<int>();

            try
            {
                foreach (GridViewRow gvr in this.gvLista.Rows)
                {
                    CheckBox chkSelezionato = (CheckBox)gvr.FindControl("chkSelezionato");

                    if (!chkSelezionato.Checked)
                        continue;

                    int id = Convert.ToInt32(this.gvLista.DataKeys[gvr.RowIndex].Value);

                    listaId.Add(id);
                }

                this.Database.BeginTransaction();

                AnagrafeDyn2ModelliTStoricoMgr mgr = new AnagrafeDyn2ModelliTStoricoMgr(this.Database);

                for (int i = 0; i < listaId.Count; i++)
                {
                    AnagrafeDyn2ModelliTStorico cls = mgr.GetById(this.IdComune, listaId[i], this.CodiceAnagrafe, this.IdModello);

                    mgr.Delete(cls);
                }

                this.Database.CommitTransaction();

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Database.RollbackTransaction();

                this.MostraErrore(ex);
            }
        }
        #endregion


        #region Scheda dettaglio
        protected void CambiaIndice(object sender, EventArgs e)
        {
            LinkButton lb = (LinkButton)sender;

            this.IndiceScheda = Convert.ToInt32(lb.CommandArgument);

            this.BindListaIndici();

            this.BindModello();
        }

        protected bool IndiceCorrente(object indice)
        {
            return (int)indice == this.IndiceScheda;
        }

        protected string TestoIndice(object indice)
        {
            //return "[" + (Convert.ToInt32(indice) + 1).ToString() + "]&nbsp;";
            return "[" + (Convert.ToInt32(indice)).ToString() + "]&nbsp;";
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }
        #endregion

    }
}
