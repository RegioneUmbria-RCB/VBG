using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class ParametroRicercaTITOLARIO : IParametroRicerca
    {
        private const string NOME_PARAMETRO = "TITOLARIO";
        private readonly string _valore;

        public ParametroRicercaTITOLARIO(string titolario)
        {
            this._valore = titolario;
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
