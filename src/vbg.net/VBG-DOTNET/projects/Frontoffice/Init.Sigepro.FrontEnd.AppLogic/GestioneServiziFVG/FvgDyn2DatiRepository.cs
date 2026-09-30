using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces;
using VBG.DatiDinamici.Utils;
using VBG.DatiDinamici.VisibilitaCampi;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FvgDyn2DatiRepository : IDyn2DatiRepository //IIstanzeDyn2DatiManager
    {
        FvgDatabase _database;
        //IEndoprocedimentiService _endoService;


        public FvgDyn2DatiRepository(FvgDatabase database)
        {
            _database = database;

            //_endoService = FoKernelContainer.GetService<IEndoprocedimentiService>();
        }

        public void EliminaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<DatiIdentificativiCampo> campiDaEliminare)
        {
            throw new NotImplementedException();
        }

        public SerializableDictionary<int, IEnumerable<IValoreCampo>> GetValoriCampiDaIdModello(int idModello, int indiceModello)
        {
            var campi = _database.GetListaValori()
                        .GroupBy(x => x.FkD2cId.Value)
                        .ToDictionary(x => x.Key, x => x.ToList().Select(y => (IValoreCampo)y));

            return new SerializableDictionary<int, IEnumerable<IValoreCampo>>(campi);
        }

        public void SalvaCampiNonVisibili(DatiIdentificativiModello idModello, IEnumerable<IdValoreCampo> listaIdCampiNonVisibili)
        {
            _database.EliminaStatoVisibilitaCampiDaIdModello(idModello.IdModello);
            _database.SalvaStatoVisibilitaCampi(idModello.IdModello, listaIdCampiNonVisibili);
            _database.Salva();
        }

        public void SalvaValoriCampi(DatiIdentificativiModello idModello, IEnumerable<CampoDaSalvare> campiDaSalvare)
        {
            // todo: far persistere i valori dei campi
            foreach (var campo in campiDaSalvare)
            {
                _database.EliminaValoriCampo(campo.Id);

                for (var i = 0; i < campo.ListaValori.Count; i++)
                {
                    var valore = campo.ListaValori[i];

                    if (String.IsNullOrEmpty(valore.Valore))
                    {
                        continue;
                    }

                    _database.ImpostaValoreCampo(campo.Id, campo.NomeCampo, i, valore.Valore, valore.ValoreDecodificato);
                }
            }

            _database.SetSchedaCompilata(idModello.IdModello);
            _database.Salva();
        }

        
    }
}
