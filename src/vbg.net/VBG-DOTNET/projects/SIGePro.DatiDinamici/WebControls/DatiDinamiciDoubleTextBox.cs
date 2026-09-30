using Init.Utils.Web.UI;
using System;
using System.Web.UI;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Statistiche;
using VBG.DatiDinamici.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls
{
    [ControlValueProperty("Valore")]
    public partial class DatiDinamiciDoubleTextBox : DatiDinamiciBaseControl<DoubleTextBox>
    {
        private static class Constants
        {
            public const string ValoreDefaultAttribute = "data-valore-default";
        }

        public static TipoConfrontoFiltroEnum[] GetTipiConfrontoSupportati()
        {
            return new TipoConfrontoFiltroEnum[] {
                                    TipoConfrontoFiltroEnum.Equal,
                                    TipoConfrontoFiltroEnum.NotEqual,
                                    TipoConfrontoFiltroEnum.LessThan,
                                    TipoConfrontoFiltroEnum.LessThanOrEqual,
                                    TipoConfrontoFiltroEnum.GreaterThan,
                                    TipoConfrontoFiltroEnum.GreaterThanOrEqual,
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
                            new ProprietaDesigner("ReadOnly", "Sola lettura", TipoControlloEditEnum.ListBox, "No=false,Si=true","false"),
                            new ProprietaDesigner("MaxLength", "Lunghezza massima","99999"),
                            new ProprietaDesigner("Columns", "Larghezza visualizzata","10"),
                            new ProprietaDesigner("ValidationMinValue", "Valore minimo","0"),
                            new ProprietaDesigner("ValidationMaxValue", "Valore massimo","99999"),
                            new ProprietaDesigner("ValoreDefault", "Valore di default",String.Empty),
                            new ProprietaDesigner("TipoNumerico", "Tipo numerico","true",false) };
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
        public string ValoreDefault
        {
            get { return this.InnerControl.Attributes[Constants.ValoreDefaultAttribute]; }
            set { this.InnerControl.Attributes[Constants.ValoreDefaultAttribute] = value; }
        }

        public override string Valore
        {
            get
            {
                return this.InnerControl.Text.ToString();
            }
            set
            {
                this.InnerControl.Text = value;
            }
        }

        public bool ReadOnly
        {
            get { return this.InnerControl.ReadOnly; }
            set { this.InnerControl.ReadOnly = value; }
        }

        internal DatiDinamiciDoubleTextBox()
        {
            this.InnerControl.CausesValidation = false;
        }

        public DatiDinamiciDoubleTextBox(CampoDinamicoBase campo)
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
            return "d2DoubleTextBox";
        }

    }
}
