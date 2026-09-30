using log4net;
using System.Globalization;

namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class CampoLabeled : ICampoLocalizzazioni
    {
        //public IBootstrapFormControl ControlloEdit { get; set; }
        //public DataControlField Colonna { get; set; }

        //IDictionary _stateBag;
        ILog _log = LogManager.GetLogger(typeof(CampoLabeled));


        public bool Visibile { get; set; } = true;

        public string Etichetta { get; set; } = String.Empty;

        public bool Obbligatorio { get; set; } = false;

        public string EspressioneRegolare { get; set; } = String.Empty;


        public string ValoreMax { get; set; } = String.Empty;

        public string ValoreMin { get; set; } = String.Empty;

        //public IDictionary StateBag
        //{
        //    set { this._stateBag = value; }
        //}

        //public virtual string Descrizione
        //{
        //    get { return this.Valore; }
        //}

        //private string StateBagIdEtichetta
        //{
        //    get { return this.ControlloEdit.ClientID + "_Etichetta"; }
        //}

        //private string StateBagIdObbligatorio
        //{
        //    get { return this.ControlloEdit.ClientID + "_Obbligatorio"; }
        //}

        //private string StateBagVisibile
        //{
        //    get { return this.ControlloEdit.ClientID + "_Visibile"; }
        //}

        //private string StateBagIdRegex
        //{
        //    get { return this.ControlloEdit.ClientID + "_Regex"; }
        //}

        //private string StateBagIdValoreMin
        //{
        //    get { return this.ControlloEdit.ClientID + "_valoremin"; }
        //}

        //private string StateBagIdValoreMax
        //{
        //    get { return this.ControlloEdit.ClientID + "_valoremax"; }
        //}

        //private void AggiornaEtichetta()
        //{
        //    this.ControlloEdit.Label = this.Etichetta;

        //    if (this.Obbligatorio)
        //        this.ControlloEdit.Required = true;

        //    if (this.Colonna != null)
        //        this.Colonna.HeaderText = this.Etichetta;
        //}

        //public bool RegexVerificata()
        //{
        //    if (!this.Obbligatorio && String.IsNullOrEmpty(this.ControlloEdit.Value.Trim()))
        //    {
        //        return true;
        //    }

        //    if (String.IsNullOrEmpty(this.EspressioneRegolare))
        //    {
        //        return true;
        //    }

        //    return System.Text.RegularExpressions.Regex.IsMatch(this.ControlloEdit.Value, this.EspressioneRegolare);
        //}

        //public bool VerificaValoreInRange()
        //{
        //    var min = double.MinValue;
        //    var max = double.MaxValue;
        //    var val = 0.0d;

        //    var formatProvider = CultureInfo.InvariantCulture.NumberFormat;
        //    var numberStyles = NumberStyles.AllowLeadingWhite | NumberStyles.AllowTrailingWhite | NumberStyles.AllowLeadingSign | NumberStyles.AllowDecimalPoint;

        //    if (!String.IsNullOrEmpty(this.ValoreMin))
        //    {
        //        if (!double.TryParse(this.ValoreMin, numberStyles, formatProvider, out min))
        //        {
        //            _log.DebugFormat("Il valore min del campo {0} non è un numero valido (valore={1}), verrà usato double.MinValue come valore min", this.Etichetta, this.ValoreMin);
        //            min = double.MinValue;
        //        }
        //    }

        //    if (!String.IsNullOrEmpty(this.ValoreMax))
        //    {
        //        if (!double.TryParse(this.ValoreMax, numberStyles, formatProvider, out max))
        //        {
        //            _log.DebugFormat("Il valore max del campo {0} non è un numero valido (valore={1}), verrà usato double.MaxValue come valore max", this.Etichetta, this.ValoreMin);
        //            max = double.MaxValue;
        //        }
        //    }

        //    if (String.IsNullOrEmpty(this.ControlloEdit.Value))
        //    {
        //        return true;
        //    }

        //    this.ControlloEdit.Value = this.ControlloEdit.Value.Replace(",", ".");

        //    if (!double.TryParse(this.ControlloEdit.Value, numberStyles, formatProvider, out val))
        //    {
        //        _log.DebugFormat("Il valore del campo {0} non è un numero valido (valore={1}), non verrà effettuata la verifica di appartenenza ad un range di valori", this.Etichetta, this.ControlloEdit.Value);
        //        return true;
        //    }

        //    return min <= val && val <= max;
        //}

        //public bool VerificaCompilazione()
        //{
        //    if (this.Visibile && this.Obbligatorio && String.IsNullOrEmpty(this.ControlloEdit.Value))
        //        return false;

        //    return true;
        //}

        //public virtual void SvuotaCampo()
        //{
        //    ControlloEdit.Value = String.Empty;
        //}
    }
}
