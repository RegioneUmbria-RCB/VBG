using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData.MappingDaManagedData;
using VBG.DatiDinamici.Interfaces;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    /// <summary>
    /// Oggetto che legge i dati della domanda FEG compilata dall'utente in base alle mappature specificate nell'oggetto <see cref="FvgManagedDataMapper"/>
    /// </summary>
    public class FvgManagedDataReader
    {
        private readonly FvgManagedDataMapper _mapper;
        private readonly IFVGWebServiceProxy _serviceProxy;
        private readonly Dictionary<string, int> _nomeCampoToId = new Dictionary<string, int>();

        public FvgManagedDataReader(FvgManagedDataMapper mapper, IFVGWebServiceProxy serviceProxy, IEnumerable<IDyn2Campo> listaCampiDinamici)
        {
            this._mapper = mapper;
            this._serviceProxy = serviceProxy;
#if DEBUG
            listaCampiDinamici.ToList().ForEach(x => System.Diagnostics.Debug.WriteLine(x.Nomecampo));
#endif
            this._nomeCampoToId = listaCampiDinamici.Select(x => new
            {
                NomeCampo = x.Nomecampo,
                Id = x.Id.Value
            })
            .GroupBy(x => x.Id)
            .Select(y => y.First())
            .ToDictionary(x => x.NomeCampo, x => x.Id);
        }

        /// <summary>
        /// Legge tutte le mappature del managed data che sono applicabili nel modulo che si sta compilando
        /// </summary>
        /// <param name="codiceIstanza"></param>
        /// <returns></returns>
        public IEnumerable<FvgManagedDataMapper.ManagedDataValue> ReadAllValues(long codiceIstanza)
        {
            // TODO: La lettura dei valori del managed data dovrebbe recuperare anche quei valori che 
            // sono stringa vuota

            var managedData = this._serviceProxy.GetManagedDataDaCodiceIstanza(codiceIstanza);

            return this._mapper.Map.SelectMany(x => x.ApplyTo(managedData, this._nomeCampoToId));
        }
    }
}
