// -----------------------------------------------------------------------
// <copyright file="AnagraficheRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche
{
    public class CreazioneAnagraficaResult
    {
        public readonly bool Esito;
        public readonly string CodiceErrore;
        public readonly string DescrizioneErrore;

        public static CreazioneAnagraficaResult Success
        {
            get
            {
                return new CreazioneAnagraficaResult(true);
            }
        }

        public static CreazioneAnagraficaResult Failed(string codiceErrore, string descrizioneErrore)
        {
            return new CreazioneAnagraficaResult(false, codiceErrore, descrizioneErrore);
        }

        protected CreazioneAnagraficaResult(bool esito, string codiceErrore = "", string descrizioneErrore = "")
        {
            this.Esito = esito;
            this.CodiceErrore = codiceErrore;
            this.DescrizioneErrore = descrizioneErrore;
        }
    }
}