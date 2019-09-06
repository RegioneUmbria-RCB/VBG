using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Classificazione
{
    public class ClassificheServiceWrapper
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public ClassificheServiceWrapper(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public ProtocolloClassifiche GetClassificaByCodice(string codice)
        {
            var classificaMgr = new ProtocolloClassificheMgr(this._db);
            var list = classificaMgr.GetList(new ProtocolloClassifiche { Codice = codice, Idcomune = this._idComune });

            if (list == null || list.Count == 0)
            {
                throw new Exception($"CLASSIFICA CON CODICE: {codice} NON TROVATA NELLA TABELLA PROTOCOLLO_CLASSIFICHE");
            }

            if (list.Count > 1)
            {
                throw new Exception($"CLASSIFICA CON CODICE: {codice} TROVATA PIU' VOLTE NELLA TABELLA PROTOCOLLO_CLASSIFICHE");
            }

            return list[0];
        }

        public ProtocolloClassifiche GetClassificaById(int id)
        {
            var classificaMgr = new ProtocolloClassificheMgr(this._db);
            return classificaMgr.GetById(this._idComune, id);
        }
    }
}
