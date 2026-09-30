using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Manager;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    public class SoggettiFirmatariService
    {
        AuthenticationInfo _authInfo;

        public SoggettiFirmatariService(AuthenticationInfo authInfo)
        {
            this._authInfo = authInfo;
        }

        public ConfigurazioneSoggettiFirmatariDto GetSoggettiFirmatariDaIdDocumenti(RichiestaSoggettiFirmatariDaIdDocumenti richiesta)
        {
            return new ConfigurazioneSoggettiFirmatariDto
            {
                SoggettiAllegatiIntervento = GetSoggettiFirmatariInterventoDaIdDocumenti(richiesta.IdDocumentiIntervento).ToArray(),
                SoggettiAllegatiEndo = GetSoggettiFirmatariEndoDaIdDocumenti(richiesta.IdDocumentiEndo).ToArray(),
            };
        }

        private IEnumerable<SoggettiFirmatariDto> GetSoggettiFirmatariEndoDaIdDocumenti(IEnumerable<int> idDocumenti)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var soggettiFirmatariManager = new AllegatiDocSoggFirmatariMgr(db, this._authInfo.IdComune);
                var tipiSoggettoMgr = new TipiSoggettoMgr(db, this._authInfo.IdComune);
                var rVal = new List<SoggettiFirmatariDto>();

                var soggettiFirmatariPerDocumento = soggettiFirmatariManager.GetListByIdAllegati(idDocumenti);

                foreach (var doc in soggettiFirmatariPerDocumento.Keys)
                {
                    var soggettiDelDocumento = soggettiFirmatariPerDocumento[doc];
                    var dto = new SoggettiFirmatariDto();

                    dto.CodiceDocumento = doc;

                    foreach (var soggetto in soggettiDelDocumento)
                    {
                        var datiSoggetto = tipiSoggettoMgr.GetById(soggetto.FkTipoSoggetto.Value);
                        var id = Convert.ToInt32(datiSoggetto.CODICETIPOSOGGETTO);
                        var descr = datiSoggetto.TIPOSOGGETTO;

                        dto.AggiungiTipoSoggetto(id, descr);
                    }

                    rVal.Add(dto);
                }

                return rVal;
            }
        }

        private IEnumerable<SoggettiFirmatariDto> GetSoggettiFirmatariInterventoDaIdDocumenti(IEnumerable<int> idDocumenti)
        { 
            using (var db = this._authInfo.CreateDatabase())
            {
                var soggettiFirmatariManager = new AlberoprocDocSoggFirmatariMgr(db, this._authInfo.IdComune);
                var tipiSoggettoMgr = new TipiSoggettoMgr(db, this._authInfo.IdComune);
                var rVal = new List<SoggettiFirmatariDto>();

                var soggettiFirmatariPerDocumento = soggettiFirmatariManager.GetListByIdAllegati(idDocumenti);

                foreach(var doc in soggettiFirmatariPerDocumento.Keys)
                {
                    var soggettiDelDocumento = soggettiFirmatariPerDocumento[doc];
                    var dto = new SoggettiFirmatariDto();

                    dto.CodiceDocumento = doc;

                    foreach(var soggetto in soggettiDelDocumento)
                    {
                        var datiSoggetto = tipiSoggettoMgr.GetById(soggetto.FkTipoSoggetto.Value);
                        var id = Convert.ToInt32(datiSoggetto.CODICETIPOSOGGETTO);
                        var descr = datiSoggetto.TIPOSOGGETTO;

                        dto.AggiungiTipoSoggetto(id, descr);
                    }

                    rVal.Add(dto);
                }

                return rVal;
            }        
        }

    }
}
