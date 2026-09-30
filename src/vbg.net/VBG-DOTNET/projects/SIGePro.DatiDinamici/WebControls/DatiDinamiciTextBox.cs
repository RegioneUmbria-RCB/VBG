using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Statistiche;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{

    [ControlValueProperty("Valore")]
    public class DatiDinamiciTextBox : DatiDinamiciBaseControl<TextBox>
    {
        public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
        {
            return new TipoConfrontoFiltroEnum[] {
                            TipoConfrontoFiltroEnum.Equal,
                            TipoConfrontoFiltroEnum.NotEqual,
                            TipoConfrontoFiltroEnum.Null,
                            TipoConfrontoFiltroEnum.NotNull,
                            TipoConfrontoFiltroEnum.Like};
        }


        /// <summary>
        /// Ritorna la lista di proprieta valorizzabili tramite la pagina di editing dei campi
        /// </summary>
        /// <returns>lista di propriea valorizzabili tramite la pagina di editing dei campi</returns>
        public static ProprietaDesigner[] GetProprietaDesigner()
        {
            return new ProprietaDesigner[]{
                        new ProprietaDesigner("Obbligatorio","Obbligatorio",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("IgnoraObbligatorietaSuAttivita","Ignora obbligatorietà su schede attività",TipoControlloEditEnum.ListBox,"No=false,Si=true","false"),
                        new ProprietaDesigner("ReadOnly", "Sola lettura", TipoControlloEditEnum.ListBox, "No=false,Si=true","false"),
                        new ProprietaDesigner("MaxLength", "Lunghezza massima","99999"),
                        new ProprietaDesigner("Columns", "Larghezza visualizzata","40"),
                        new ProprietaDesigner("MultiLine", "Multiriga", TipoControlloEditEnum.ListBox, "No=false,Si=true","false"),
                        new ProprietaDesigner("Rows", "N. righe (se multiriga)","1"),
                        new ProprietaDesigner("EspressioneRegolare", "Espressione regolare di validazione","")};
        }

        public int Columns
        {
            get { return this.InnerControl.Columns; }
            set { this.InnerControl.Columns = value; }
        }
        public int MaxLength
        {
            get { return this.InnerControl.MaxLength; }
            set { this.InnerControl.MaxLength = value; }
        }

        public bool ReadOnly
        {
            get { return this.InnerControl.ReadOnly; }
            set { this.InnerControl.ReadOnly = value; }
        }

        public bool MultiLine
        {
            get { return this.InnerControl.TextMode == TextBoxMode.MultiLine; }
            set { this.InnerControl.TextMode = value ? TextBoxMode.MultiLine : TextBoxMode.SingleLine; }
        }



        public int Rows
        {
            get { return this.InnerControl.Rows; }
            set { this.InnerControl.Rows = value; }
        }

        public override string Valore
        {
            get { return this.InnerControl.Text; }
            set { this.InnerControl.Text = value; }
        }

        internal DatiDinamiciTextBox()
        {
            this.InnerControl.CausesValidation = false;
        }

        public DatiDinamiciTextBox(CampoDinamicoBase campo)
            : base(campo)
        {
            //this.InnerControl.AutoPostBack = true;
            this.InnerControl.CausesValidation = false;

            //this.InnerControl.TextChanged += delegate(object sender, EventArgs e)
            //{
            //    NotifyValueChanged(this.InnerControl.Text);
            //};
        }

        protected override string GetNomeTipoControllo()
        {
            return "d2TextBox";
        }
    }
}
