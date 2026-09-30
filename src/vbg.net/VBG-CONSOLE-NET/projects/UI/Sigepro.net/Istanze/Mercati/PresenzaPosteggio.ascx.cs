using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using Init.SIGePro.Data;
using System.ComponentModel;
using System.Collections.Generic;
using Init.SIGePro.Manager;
using PersonalLib2.Sql;

namespace Sigepro.net.Istanze.Mercati
{
    public partial class PresenzaPosteggio : System.Web.UI.UserControl
    {
        protected BasePage BasePage
        {
            get { return (BasePage)this.Page; }
        }

        int m_iddettaglio = int.MinValue;
        public int IdDettaglio
        {
            get { return m_iddettaglio; }
            set { m_iddettaglio = value; }
        }

        Mercati_D m_posteggio = null;
        public Mercati_D Posteggio
        {
            get { return m_posteggio; }
            set { m_posteggio = value; }
        }

        public int CodiceAnagrafe
        {
            get { return this.txtCodiceAnagrafe.ValoreInt.GetValueOrDefault(int.MinValue); }
        }

        public int IdPosteggio
        {
            get { return this.txIdPosteggio.ValoreInt.GetValueOrDefault(int.MinValue); }
        }

        public int NumeroPresenze
        {
            get { return this.txPresenze.ValoreInt.GetValueOrDefault(int.MinValue); }
        }



        public override void DataBind()
        {
            base.DataBind();
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (Posteggio != null)
            {
                this.txIdPosteggio.ValoreInt = Posteggio.IDPOSTEGGIO;
                this.lblPosteggio.Text = Posteggio.CODICEPOSTEGGIO;
                this.imNote.AlternateText = Posteggio.Note;
                this.imNote.Visible = (!String.IsNullOrEmpty(Posteggio.Note));
                BindMerceologie(Posteggio);
                
            }

            pnlOccupante.Visible = (IdDettaglio != int.MinValue);
            if (IdDettaglio == int.MinValue) return;
            
            MercatiPresenzeD mpd = new MercatiPresenzeD();
            mpd.Idcomune = BasePage.IdComune;
            mpd.Id = IdDettaglio;
            mpd.UseForeign = useForeignEnum.Yes;

            mpd = new MercatiPresenzeDMgr(BasePage.Database).GetByClass(mpd);

            this.txtCodiceAnagrafe.ValoreInt = Convert.ToInt32(mpd.Anagrafe.CODICEANAGRAFE);
            this.lblOccupante.Text = mpd.Anagrafe.ToString();

            this.txPresenze.ValoreInt  = mpd.Numeropresenze;
            
            base.OnPreRender(e);
        }

        private void BindMerceologie(Mercati_D cls)
        {
            Mercati_DAttivitaIstat m = new Mercati_DAttivitaIstat();
            m.FKCODICEMERCATO = cls.FKCODICEMERCATO;
            m.FKIDPOSTEGGIO = cls.IDPOSTEGGIO;
            m.IDCOMUNE = cls.IDCOMUNE;

            List<Mercati_DAttivitaIstat> merceologie = new Mercati_DAttivitaIstatMgr(BasePage.Database).GetList(m);

            foreach (Mercati_DAttivitaIstat att in merceologie)
            {
                this.imMerceologie.AlternateText += att.Attivita.CodiceIstat + " - " + att.Attivita.ISTAT;
                this.imMerceologie.AlternateText += (att.Flag_Consentito == "0") ? " non consentito" : " consentito";
                this.imMerceologie.AlternateText += "\n";
            }

            this.imMerceologie.Visible = (!String.IsNullOrEmpty(this.imMerceologie.AlternateText));
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }
    }
}