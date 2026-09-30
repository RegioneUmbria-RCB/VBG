using System.Collections;

namespace AreaRiservataCore.Pages.InserimentoIstanza.HelperGestioneLocalizzazioni
{
    public interface ICampoLocalizzazioni //: ICompilazioneVerificabile, IRegexVerificabile, IValoreinRangeVerificabile
    {
        string Etichetta { get; set; }
        bool Visibile { get; set; }
        bool Obbligatorio { get; set; }
        //string Valore { get; }
        //string Descrizione { get; }
        string EspressioneRegolare { get; set; }
        //IDictionary StateBag { set; }
        string ValoreMax { get; set; }
        string ValoreMin { get; set; }


        //void SvuotaCampo();
    }
}
