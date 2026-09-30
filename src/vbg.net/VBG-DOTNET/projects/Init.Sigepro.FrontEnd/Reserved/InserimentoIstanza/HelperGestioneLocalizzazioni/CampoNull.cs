using System.Collections;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public class CampoNull : ICampoLocalizzazioni
    {
        private IDictionary _statebag;

        public string Etichetta { get => ""; set { var a = value; } }
        public bool Visibile { get => false; set { var a = value; } }
        public bool Obbligatorio { get => false; set { var a = value; } }

        public string Valore => "";

        public string Descrizione => "";

        public string EspressioneRegolare { set { var a = value; } }
        public IDictionary StateBag { set { _statebag = value; } }
        public string ValoreMax { get => ""; set { var a = value; } }
        public string ValoreMin { get => ""; set { var a = value; } }

        public bool RegexVerificata()
        {
            return true;
        }

        public void SvuotaCampo()
        {
            //...
        }

        public bool VerificaCompilazione()
        {
            return true;
        }

        public bool VerificaValoreInRange()
        {
            return true;
        }
    }
}