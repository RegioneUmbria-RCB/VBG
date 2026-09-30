using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Statistiche;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public partial class DatiDinamiciMultiListBox : DatiDinamiciBaseControl<ListBox>
    {

        public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
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
        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                        new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("ElementiLista", "Elementi della lista (separati da \";\")","") ,
                        new ProprietaDesigner("IgnoraErroriBinding", "Ignora errori di binding" , TipoControlloEditEnum.ListBox , "No=false,Si=true","false"),
                        new ProprietaDesigner("Multiselezione", "Abilita la multiselezione" , TipoControlloEditEnum.ListBox , "No=false,Si=true","false"),
                        new ProprietaDesigner("RigheVisualizzate", "Numero righe visualizzate","4"),
                        new ProprietaDesigner("SeparatoreMultivalori", "Separatore valori multipli",", ")
            };
        }

        private string m_bindValue = null;

        public override string Valore
        {
            get
            {
                string valore = "";
                bool isFirst = true;

                foreach (ListItem li in this.InnerControl.Items)
                {
                    if (!li.Selected) continue;

                    if (!isFirst)
                        valore += ";";

                    valore += li.Value;

                    isFirst = false;
                }

                return valore;
            }
            set
            {
                bool elFound = false;


                string[] valori = value.Split(';');

                for (int i = 0; i < valori.Length; i++)
                {
                    foreach (ListItem li in this.InnerControl.Items)
                    {
                        if (li.Value == valori[i])
                        {
                            elFound = true;
                            li.Selected = true;
                            break;
                        }
                    }
                }

                if (!elFound)
                {
                    if (this.IgnoraErroriBinding)
                    {
                        this.m_bindValue = value;
                        return;
                    }

                    string errMsg = "Si è tentato di impostare un valore non valido al controllo \"{0}\". Valori possibili: \"{1}\". Valore impostato:\"{2}\"";
                    this.InnerControl.SelectedIndex = 0;
                    throw new ArgumentException(String.Format(errMsg, this.Descrizione, this.ElementiLista, value));
                }
            }
        }

        public virtual string ElementiLista
        {
            get
            {
                if (this.InnerControl.Items.Count == 0) return String.Empty;

                string[] elementi = new string[this.InnerControl.Items.Count];

                for (int i = 0; i < this.InnerControl.Items.Count; i++)
                {
                    if (String.IsNullOrEmpty(this.InnerControl.Items[i].Value.ToString()))
                        elementi[i] = this.InnerControl.Items[i].Value.ToString();
                }

                return String.Join(";", elementi);
            }
            set
            {
                string[] elementi = value.Split(';');

                this.InnerControl.Items.Clear();

                for (int i = 0; i < elementi.Length; i++)
                    this.InnerControl.Items.Add(new ListItem(elementi[i].Trim()));

                this.InnerControl.Items.Insert(0, new ListItem(""));
            }
        }


        internal DatiDinamiciMultiListBox()
        {
        }


        public DatiDinamiciMultiListBox(CampoDinamicoBase campo)
            : base(campo)
        {
        }

        public override void DataBind()
        {
            base.DataBind();
        }

        public bool Multiselezione
        {
            get { return this.InnerControl.SelectionMode == ListSelectionMode.Multiple; }
            set { this.InnerControl.SelectionMode = value ? ListSelectionMode.Multiple : ListSelectionMode.Single; }
        }

        public int RigheVisualizzate
        {
            get { return this.InnerControl.Rows; }
            set { this.InnerControl.Rows = value; }
        }

        public bool IgnoraErroriBinding
        {
            get { object o = this.ViewState["IgnoraErroriBinding"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IgnoraErroriBinding"] = value; }
        }

        public string SeparatoreMultivalori
        {
            get { object o = this.ViewState["SeparatoreMultivalori"]; return o == null ? ", " : o.ToString(); }
            set { this.ViewState["SeparatoreMultivalori"] = value; }
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.InnerControl.Attributes.Add("data-separatore-multi-valori", this.SeparatoreMultivalori);
            if (this.IgnoraErroriBinding && !String.IsNullOrEmpty(this.m_bindValue))
            {
                string script = "var {0} = document.getElementById('{0}');{0}.bindValue = '{1}';";
                script = string.Format(script, this.InnerControl.ClientID, this.m_bindValue);
                this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID + "_safeBinding", script, true);
            }

            base.OnPreRender(e);
        }

        protected override string GetNomeEventoModifica()
        {
            return "click";
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2MultiListBox";
        }
    }
}
