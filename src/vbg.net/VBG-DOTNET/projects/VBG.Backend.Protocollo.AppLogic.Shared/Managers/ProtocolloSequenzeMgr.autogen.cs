using Init.SIGePro.Manager;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    ///
	/// File generato automaticamente dalla tabella PROTOCOLLO_SEQUENZE per la classe ProtocolloSequenze il 25/02/2014 17.00.35
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
    public partial class ProtocolloSequenzeMgr : BaseProtocolloManager
    {
        public ProtocolloSequenzeMgr(DataBase dataBase) : base(dataBase) { }

        public ProtocolloSequenze GetById(string idcomune, string flusso, string software = "TT", string codiceComune = "")
        {
            var c = new ProtocolloSequenze { Idcomune = idcomune, Flusso = flusso };
            return this.GetByIdProtocollo<ProtocolloSequenze>(c, software, codiceComune);
        }

        public List<ProtocolloSequenze> GetList(ProtocolloSequenze filtro)
        {
            return db.GetClassList(filtro).ToList<ProtocolloSequenze>();
        }
    }
}
