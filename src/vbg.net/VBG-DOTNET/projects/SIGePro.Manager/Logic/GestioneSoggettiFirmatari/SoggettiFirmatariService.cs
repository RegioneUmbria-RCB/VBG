using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    public class SoggettiFirmatariService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public SoggettiFirmatariService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public ConfigurazioneSoggettiFirmatariDto GetSoggettiFirmatariDaIdDocumenti(RichiestaSoggettiFirmatariDaIdDocumenti richiesta)
        {
            return new ConfigurazioneSoggettiFirmatariDto
            {
                SoggettiAllegatiIntervento = this.GetSoggettiFirmatariInterventoDaIdDocumenti(richiesta.IdDocumentiIntervento).ToArray(),
                SoggettiAllegatiEndo = this.GetSoggettiFirmatariEndoDaIdDocumenti(richiesta.IdDocumentiEndo).ToArray(),
            };
        }

        private IEnumerable<SoggettiFirmatariDto> GetSoggettiFirmatariEndoDaIdDocumenti(IEnumerable<int> idDocumenti)
        {
            if (!idDocumenti.Any())
            {
                return Enumerable.Empty<SoggettiFirmatariDto>();
            }

            var soggettiFirmatariManager = new AllegatiDocSoggFirmatariMgr(this._db, this._idComune);
            var tipiSoggettoMgr = new TipiSoggettoMgr(this._db, this._idComune);
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

        private IEnumerable<SoggettiFirmatariDto> GetSoggettiFirmatariInterventoDaIdDocumenti(IEnumerable<int> idDocumenti)
        {
            if (!idDocumenti.Any())
            {
                return Enumerable.Empty<SoggettiFirmatariDto>();
            }

            var soggettiFirmatariManager = new AlberoprocDocSoggFirmatariMgr(this._db, this._idComune);
            var tipiSoggettoMgr = new TipiSoggettoMgr(this._db, this._idComune);
            var rVal = new List<SoggettiFirmatariDto>();

            var soggettiFirmatariPerDocumento = soggettiFirmatariManager.GetListByIdAllegati(idDocumenti);

            foreach (var documento in soggettiFirmatariPerDocumento)
            {
                var doc = documento.Key;
                var soggettiDelDocumento = documento.Value;

                var dto = new SoggettiFirmatariDto
                {
                    CodiceDocumento = doc
                };

                foreach (var soggetto in soggettiDelDocumento)
                {
                    var datiSoggetto = tipiSoggettoMgr.GetById(soggetto.FkTipoSoggetto!.Value);

                    dto.AggiungiTipoSoggetto(datiSoggetto);
                }

                rVal.Add(dto);
            }

            return rVal;
        }

        public VerificaSoggettiFirmatariRiepilogoDomandaDto GetSoggettiFirmatariRiepilogoDomanda(int idDocumento)
        {
            var alberoProcDocMgr = new AlberoProcDocumentiMgr(this._db);

            var verificaFirmaUteLoggato = alberoProcDocMgr.GetById(idDocumento.ToString(), this._idComune).FlagFirmaUteLoggato.GetValueOrDefault(0) == 1;

            var soggettiFirmatari = this.GetSoggettiFirmatariInterventoDaIdDocumenti(new int[] { idDocumento });

            var tipiSoggList = soggettiFirmatari.SelectMany(x => x.TipiSoggetto).Select(x => new TipoSoggettoFirmatarioDto
            {
                Id = x.Id,
                Descrizione = x.Descrizione
            }).ToList();

            return new VerificaSoggettiFirmatariRiepilogoDomandaDto
            {
                SoggettiFirmatari = tipiSoggList,
                VerificaFirmaUtenteLoggato = verificaFirmaUteLoggato
            };
        }
    }
}
