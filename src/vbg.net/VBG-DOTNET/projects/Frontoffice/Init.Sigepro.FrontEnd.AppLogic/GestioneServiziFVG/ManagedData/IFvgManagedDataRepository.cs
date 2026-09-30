using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData;
using VBG.DatiDinamici.Interfaces;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    public interface IFvgManagedDataRepository
    {
        /// <summary>
        /// Cerca di recuperare dal managed data eventuali valori compilati nella fase iniziale di presentazione della domanda utilizzando il file di mappature 
        /// per recuperare le espressioni xpath
        /// </summary>
        /// <param name="codiceIstanza"></param>
        /// <param name="listaCampiDinamic"></param>
        /// <returns></returns>
        IEnumerable<FvgManagedDataMapper.ManagedDataValue> ReadAllValues(long codiceIstanza, IEnumerable<IDyn2Campo> listaCampiDinamic);

        /// <summary>
        /// Legge i valori dei dati dinamici che sono stati fatti persistere tramite i servizi esposti da FVG. 
        /// Questi non sono i valori del managedData recuperati dai dati di compilazione della domanda FEG ma sono solo i valori dei dati dinamici
        /// appartenenti ai moduli già compilati
        /// </summary>
        /// <param name="codiceIstanza"></param>
        /// <param name="listaCampiDelModulo"></param>
        /// <returns></returns>
        IEnumerable<FvgDatabase.ValoreCampoDinamico> GetValoriDatiDinamici(long codiceIstanza, IEnumerable<string> listaCampiDelModulo);

        /// <summary>
        /// Salva l'allegato che contiene il riepilogo della domanda compilata tramite i servizi di INSIEL
        /// </summary>
        /// <param name="codiceIstanza">codice istanza</param>
        /// <param name="idModulo">id (codice PEOPLE) sel modulo che si sta compilando</param>
        /// <param name="binaryFile">file pdf da allegare alla domanda FEG</param>
        void SalvaFilePdf(long codiceIstanza, string idModulo, BinaryFile binaryFile);
    }
}
