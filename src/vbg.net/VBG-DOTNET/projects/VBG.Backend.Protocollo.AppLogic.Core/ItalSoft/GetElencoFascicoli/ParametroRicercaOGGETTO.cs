using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class ParametroRicercaOGGETTO : IParametroRicerca
    {
        private const string NOME_PARAMETRO = "OGGETTO";
        private readonly string _valore;

        public ParametroRicercaOGGETTO(string oggetto)
        {
            this._valore = oggetto;
        }

        public parametroRicerca Get()
        {
            return new parametroRicerca
            {
                chiave = NOME_PARAMETRO,
                valore = this._valore
            };
        }
    }
}
