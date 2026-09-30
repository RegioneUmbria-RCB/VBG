using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.DebugConfiguration;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using VBG.DatiDinamici.Interfaces;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    public class FvgManagedDataRepository : IFvgManagedDataRepository
    {
        private readonly IFVGWebServiceProxy _webServiceProxy;
        private readonly IFVGDebugConfiguration _debugConfiguration;
        private readonly IPathMapper _pathMapper;

        public FvgManagedDataRepository(IFVGWebServiceProxy webServiceProxy, IFVGDebugConfiguration debugConfiguration, IPathMapper pathMapper)
        {
            this._webServiceProxy = webServiceProxy;
            this._debugConfiguration = debugConfiguration;
            this._pathMapper = pathMapper;
        }

        /// <summary>
        /// Legge i valori dei dati dinamici che sono stati fatti persistere tramite i servizi esposti da FVG. 
        /// Questi non sono i valori del managedData recuperati dai dati di compilazione della domanda FEG ma sono solo i valori dei dati dinamici
        /// appartenenti ai moduli già compilati
        /// </summary>
        /// <param name="codiceIstanza"></param>
        /// <param name="listaCampiDelModulo"></param>
        /// <returns></returns>
        public IEnumerable<FvgDatabase.ValoreCampoDinamico> GetValoriDatiDinamici(long codiceIstanza, IEnumerable<string> listaCampiDelModulo)
        {
            var reader = new FvgDatiDinamiciManagedDataReader(listaCampiDelModulo, this._webServiceProxy);
            return reader.GetValoriDatiDinamici(codiceIstanza);
        }

        /// <summary>
        /// Legge la lista dei valori compilati dall'utente durante la presentazione di una domanda
        /// </summary>
        /// <param name="codiceIstanza">Istanza per cui si stanno leggendo i dati</param>
        /// <param name="listaCampiDinamici">Lista di campi dinamici di cui si vogliono leggere i valori</param>
        /// <returns></returns>
        public IEnumerable<FvgManagedDataMapper.ManagedDataValue> ReadAllValues(long codiceIstanza, IEnumerable<IDyn2Campo> listaCampiDinamici)
        {
            var mapper = this.CreateDataMapper();
            var dataReader = new FvgManagedDataReader(mapper, this._webServiceProxy, listaCampiDinamici);

            return dataReader.ReadAllValues(codiceIstanza).ToList();
        }


        /// <summary>
        /// Salva l'allegato che contiene il riepilogo della domanda compilata tramite i servizi di INSIEL
        /// </summary>
        /// <param name="codiceIstanza">codice istanza</param>
        /// <param name="idModulo">id (codice PEOPLE) sel modulo che si sta compilando</param>
        /// <param name="binaryFile">file pdf da allegare alla domanda FEG</param>
        public void SalvaFilePdf(long codiceIstanza, string idModulo, BinaryFile binaryFile)
        {
            this._webServiceProxy.SalvaFilePdf(codiceIstanza, idModulo, binaryFile.FileContent);
        }

        private FvgManagedDataMapper CreateDataMapper()
        {
            var configurationFile = "~/moduli-fvg/compilazione/managed-data-mappings.xml";
            return FvgManagedDataMapper.LoadFrom(this._pathMapper, configurationFile, this._debugConfiguration);
        }
    }
}
