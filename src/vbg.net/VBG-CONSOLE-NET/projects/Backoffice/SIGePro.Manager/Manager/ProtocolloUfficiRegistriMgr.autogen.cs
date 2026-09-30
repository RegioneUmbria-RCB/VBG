
			
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;

using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{

	///
	/// File generato automaticamente dalla tabella PROTOCOLLO_UFFICIREGISTRI per la classe ProtocolloUfficiRegistri il 26/11/2012 17.29.18
	///
	///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
	///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
	/// -
	/// -
	/// -
	/// - 
	///
	///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
	///
	public partial class ProtocolloUfficiRegistriMgr : BaseProtocolloManager
	{
		public ProtocolloUfficiRegistriMgr(DataBase dataBase) : base(dataBase) { }

		public ProtocolloUfficiRegistri GetById(string codiceregistro, string idcomune, string software = "TT", string codiceComune = "")
		{
            var c = new ProtocolloUfficiRegistri { Idcomune = idcomune, Codiceregistro = codiceregistro };
            return this.GetByIdProtocollo<ProtocolloUfficiRegistri>(c, software, codiceComune);
		}

        public IEnumerable<ProtocolloUfficiRegistri> GetBySoftwareCodiceComune(string idcomune, string software = "TT", string codiceComune = "")
        {
            var entity = new ProtocolloUfficiRegistri { Idcomune = idcomune };
            return this.GetByClassProtocollo<ProtocolloUfficiRegistri>(entity, software, codiceComune);
        }
	}
}
			
			
			