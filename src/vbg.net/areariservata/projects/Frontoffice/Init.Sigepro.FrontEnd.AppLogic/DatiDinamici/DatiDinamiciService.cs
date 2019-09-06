using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public class DatiDinamiciService : IDatiDinamiciService
    {
        AreaRiservataServiceCreator _serviceCreator;

        internal DatiDinamiciService(AreaRiservataServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public IEnumerable<IIstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza( int idDomanda)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDyn2DatiByCodiceIstanza(ws.Token, idDomanda)
                        .Select( x => new IstanzeDyn2Dati
                        {
                            Codiceistanza = idDomanda,
                            FkD2cId = x.FkD2cId,
                            Idcomune = x.Idcomune,
                            Indice = x.Indice,
                            IndiceMolteplicita = x.IndiceMolteplicita,
                            Valore = x.Valore,
                            Valoredecodificato = x.Valoredecodificato
                        });
                }
                catch(Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public IEnumerable<DecodificaDTO> GetDecodificheAttive(string tabella)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDecodificheAttive(ws.Token, tabella);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    ws.Service.RecuperaDocumentiIstanzaCollegata(ws.Token, codiceIstanzaOrigine, idDomandaDestinazione);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }
    }
}
