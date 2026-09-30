using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class ParametroRicercaCODICE : IParametroRicerca
    {
        private const string NOME_PARAMETRO = "CODICE";
        private readonly string _valore;

        public ParametroRicercaCODICE(string numero)
        {
            this._valore = numero;
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
