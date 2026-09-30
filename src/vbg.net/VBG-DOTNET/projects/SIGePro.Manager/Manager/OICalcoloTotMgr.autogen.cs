

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_ICALCOLOTOT per la classe OICalcoloTot il 27/06/2008 13.01.36
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
    public partial class OICalcoloTotMgr : BaseManager
    {
        public OICalcoloTotMgr(DataBase dataBase) : base(dataBase) { }

        public OICalcoloTot? GetById(string idcomune, int id)
        {
            FormattableString sql = $"select * from O_ICALCOLOTOT where idcomune = {idcomune} and id = {id}";

            return this.db.GetClassList<OICalcoloTot>(sql).FirstOrDefault();
        }

        public List<OICalcoloTot> GetList(OICalcoloTot filtro)
        {
            return this.db.GetClassList(filtro).ToList<OICalcoloTot>();
        }

        public OICalcoloTot Insert(OICalcoloTot cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OICalcoloTot ChildInsert(OICalcoloTot cls)
        {
            return cls;
        }

        private OICalcoloTot DataIntegrations(OICalcoloTot cls)
        {
            return cls;
        }

        public OICalcoloTot Update(OICalcoloTot cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OICalcoloTot cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OICalcoloTot cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(OICalcoloTot cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


