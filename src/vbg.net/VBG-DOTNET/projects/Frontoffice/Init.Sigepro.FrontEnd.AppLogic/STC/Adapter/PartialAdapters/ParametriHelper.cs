using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters
{
    internal class ParametriHelper
    {
        internal ParametroType CreaParametroType(string nome, string valore, bool copiaCodiceSuDescrizione = true)
        {
            return this.CreaParametroType(nome, valore, copiaCodiceSuDescrizione ? valore : null);
        }

        internal ParametroType CreaParametroType(string nome, string codice, string? descrizione)
        {
            return new ParametroType
            {
                nome = nome,
                valore = new ValoreParametroType[]{
                    new ValoreParametroType
                    {
                        codice = codice,
                        descrizione = descrizione
                    }
                }
            };
        }

    }
}
