using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData
{
    public class FvgManagedDataReader
    {
        FvgManagedDataMapper _mapper;
        IFVGWebServiceProxy _serviceProxy;
        Dictionary<string, int> _nomeCampoToId = new Dictionary<string, int>();

        public FvgManagedDataReader(FvgManagedDataMapper mapper, IFVGWebServiceProxy serviceProxy, SerializableDictionary<int, IDyn2Campo> listaCampiDinamici)
        {
            this._mapper = mapper;
            this._serviceProxy = serviceProxy;

            this._nomeCampoToId = listaCampiDinamici.Select(x => new
            {
                NomeCampo = x.Value.Nomecampo,
                Id = x.Value.Id.Value
            })
            .ToDictionary(x => x.NomeCampo, x => x.Id);
        }

        public IEnumerable<FvgManagedDataMapper.ManagedDataValue> ReadAllValues(long codiceIstanza)
        {
            var managedData = this._serviceProxy.GetManagedDataDaCodiceIstanza(codiceIstanza);

            return this._mapper.Map.SelectMany(x => x.ApplyTo(managedData, this._nomeCampoToId));
        }
    }
}
