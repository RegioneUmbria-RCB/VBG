using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Linq;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class VisuraIstanzeDyn2DatiManager : IIstanzeDyn2DatiManager
    {
        AreaRiservataServiceCreator _serviceCreator;

        internal VisuraIstanzeDyn2DatiManager(AreaRiservataServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public void EliminaValoriCampi(ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaEliminare)
        {
            throw new NotImplementedException();
        }

        private IEnumerable<IIstanzeDyn2Dati> GetDyn2DatiByIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo = 0)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDyn2DatiByIdModello(ws.Token, codiceIstanza, idModello, indiceCampo)
                             .Select(x => new IstanzeDyn2Dati
                             {
                                 Codiceistanza = codiceIstanza,
                                 FkD2cId = x.FkD2cId,
                                 Idcomune = x.Idcomune,
                                 Indice = x.Indice,
                                 IndiceMolteplicita = x.IndiceMolteplicita,
                                 Valore = x.Valore, 
                                 Valoredecodificato = x.Valoredecodificato
                             });
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public SerializableDictionary<int, List<IIstanzeDyn2Dati>> GetValoriCampoDaIdModello(string idComune, int codiceIstanza, int idModello, int indiceCampo)
        {
            var dyn2Dati = GetDyn2DatiByIdModello(idComune, codiceIstanza, idModello, indiceCampo);
            var dict = dyn2Dati
                .GroupBy(x => x.FkD2cId.Value)
                .ToDictionary(x => x.Key, y => y.Cast<IIstanzeDyn2Dati>().ToList());

            return new SerializableDictionary<int, List<IIstanzeDyn2Dati>>(dict);
        }

        public void SalvaValoriCampi(bool salvaStorico, ModelloDinamicoIstanza modello, IEnumerable<CampoDinamico> campiDaSalvare)
        {
            throw new NotImplementedException();
        }
    }
}