using System.Collections;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public interface ICampoLocalizzazioni : ICompilazioneVerificabile, IRegexVerificabile, IValoreinRangeVerificabile
    {
        string Etichetta { get; set; }
        bool Visibile { get; set; }
        bool Obbligatorio { get; set; }
        string Valore { get; }
        string Descrizione { get; }
        string EspressioneRegolare { set; }
        IDictionary StateBag { set; }
        string ValoreMax { get; set; }
        string ValoreMin { get; set; }


        void SvuotaCampo();
    }
}