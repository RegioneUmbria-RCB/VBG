using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public class DatiDinamiciService : IDatiDinamiciService
    {
        WsDatiDinamiciServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _softwareResolver;

        public DatiDinamiciService(WsDatiDinamiciServiceCreator serviceCreator, ISoftwareResolver softwareResolver)
        {
            this._serviceCreator = serviceCreator;
            this._softwareResolver = softwareResolver;
        }

        public IEnumerable<DecodificaDto> GetDecodificheAttive(string tabella)
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

        public IEnumerable<IstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza(int idDomanda)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetDyn2DatiByCodiceIstanza(ws.Token, idDomanda);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public int? GetIdCampoDaNome(string nomeCampo)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetIdCampoDaNome(ws.Token, this._softwareResolver.Software, nomeCampo);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public int? GetIdModelloDaCodice(string codiceModello)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.GetIdModelloDaCodice(ws.Token, this._softwareResolver.Software, codiceModello);
                }
                catch (Exception)
                {
                    ws.Service.Abort();
                    throw;
                }
            }
        }

        public bool VerificaEsistenzaModelloDinamico(int idModello)
        {
            using (var ws = this._serviceCreator.CreateClient())
            {
                try
                {
                    return ws.Service.VerificaEsistenzaModelloDinamico(ws.Token, idModello);
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
