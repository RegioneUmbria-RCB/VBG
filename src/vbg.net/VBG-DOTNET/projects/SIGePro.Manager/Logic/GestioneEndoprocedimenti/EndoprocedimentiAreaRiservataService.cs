using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.Logic.GestioneAlberoInterventi;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
    public class EndoprocedimentiAreaRiservataService : IEndoprocedimentiService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public EndoprocedimentiAreaRiservataService(DataBase db, string idComune)
        {
            _db = db ?? throw new ArgumentNullException(nameof(db));
            _idComune = idComune ?? throw new ArgumentNullException(nameof(idComune));
        }

        public EndoprocedimentiAreaRiservataDto GetListaEndoDaIdInterventoECodiceComune(string codiceComune, int idIntervento, bool utenteTester)
        {
            var alberoInterventiService = new AlberoInterventiService(this._db, this._idComune);

            // TODO: Inizializzare con un oggetto concreto
            var repository = new EndoAreaRiservataDaIdInterventoRepository(this._db, this._idComune, codiceComune, alberoInterventiService, utenteTester);

            return new EndoprocedimentiAreaRiservataDto
            {
                Principali = repository.GetPrincipali(idIntervento).ToList(),
                Richiesti = repository.GetRichiesti(idIntervento).ToList(),
                Ricorrenti = repository.GetRicorrenti(idIntervento).ToList(),
                Altri = repository.GetAltri(idIntervento).ToList()
            };
        }
    }
}
