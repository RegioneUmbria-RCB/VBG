using System;
using System.Data;
using System.Web.UI;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;
using VBG.DatiDinamici.Statistiche;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public partial class DatiDinamiciSigeproListBox : DatiDinamiciListBox
    {
        [Ninject.Inject]
        public IDyn2QueryDatiDinamiciManager _queryManager { get; set; }

        public static new TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
        {
            return new TipoConfrontoFiltroEnum[] {
                            TipoConfrontoFiltroEnum.Equal,
                            TipoConfrontoFiltroEnum.NotEqual,
                            TipoConfrontoFiltroEnum.Null,
                            TipoConfrontoFiltroEnum.NotNull };
        }

        /// <summary>
        /// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
        /// </summary>
        /// <returns>lista di proprieta valorizzabili tramite la pagina di editing dei campi</returns>
        public static new ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                        new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("CampiSelect", "Campi della select",""),
                        new ProprietaDesigner("TabelleSelect", "Tabelle della select",""),
                        new ProprietaDesigner("CondizioneJoin", "Condizioni di join",""),
                        new ProprietaDesigner("CondizioniWhere", "Condizioni where",""),
                        new ProprietaDesigner("NomeCampoValore", "Nome campo valore",""),
                        new ProprietaDesigner("NomeCampoTesto", "Nome campo testo","")};
        }

        #region gestione della query di selezione
        public string CampiSelect
        {
            get { object o = this.ViewState["CampiSelect"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["CampiSelect"] = value; }
        }

        public string TabelleSelect
        {
            get { object o = this.ViewState["TabelleSelect"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["TabelleSelect"] = value; }
        }

        public string CondizioneJoin
        {
            get { object o = this.ViewState["CondizioneJoin"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CondizioneJoin"] = value; }
        }

        public string CondizioniWhere
        {
            get { object o = this.ViewState["CondizioniWhere"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CondizioniWhere"] = value; }
        }

        public string NomeCampoValore
        {
            get { return this.InnerControl.DataValueField; }
            set { this.InnerControl.DataValueField = value; }
        }

        public string NomeCampoTesto
        {
            get { return this.InnerControl.DataTextField; }
            set { this.InnerControl.DataTextField = value; }
        }
        #endregion

        // Override solo per evitare che compaia nella lista delle proprietà
        public override string ElementiLista
        {
            get
            {
                return base.ElementiLista;
            }
            set
            {
                base.ElementiLista = value;
            }
        }

        public override string Valore
        {/*
			get { object o = this.ViewState["Valore"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Valore"] = value; }*/
            get { return this.InnerControl.SelectedValue; }
            set { this.InnerControl.SelectedValue = value; }
        }


        internal DatiDinamiciSigeproListBox()
        {
            this.InnerControl.SelectedIndexChanged += new EventHandler(this.InnerControl_SelectedIndexChanged);
            this.RichiedeNotificaSuModificaValoreDecodificato = true;
        }

        public DatiDinamiciSigeproListBox(CampoDinamicoBase campo)
            : base(campo)
        {
            this.InnerControl.SelectedIndexChanged += new EventHandler(this.InnerControl_SelectedIndexChanged);
            this.RichiedeNotificaSuModificaValoreDecodificato = true;
        }

        private void InnerControl_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.Valore = this.InnerControl.SelectedValue;
        }

        public override void DataBind()
        {
            if (this._queryManager == null)
            {
                return;
            }

            DataSet ds = this._queryManager.EseguiQuery(this.IdComune, this.CampiSelect, this.TabelleSelect, this.CondizioneJoin, this.CondizioniWhere, this.NomeCampoTesto, this.NomeCampoValore);

            DataRow dr = ds.Tables[0].NewRow();
            dr[this.NomeCampoTesto] = String.Empty;
            dr[this.NomeCampoValore] = String.Empty;

            ds.Tables[0].Rows.InsertAt(dr, 0);

            this.InnerControl.DataSource = ds.Tables[0];
            this.InnerControl.DataBind();
        }

    }
}
