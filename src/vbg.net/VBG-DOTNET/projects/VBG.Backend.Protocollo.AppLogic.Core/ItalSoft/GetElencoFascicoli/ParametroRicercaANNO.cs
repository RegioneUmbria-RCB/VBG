using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class ParametroRicercaANNO : IParametroRicerca
    {
        private const string NOME_PARAMETRO = "ANNO";
        private readonly string _valore;

        public ParametroRicercaANNO(int anno)
        {
            this._valore = anno.ToString();
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
