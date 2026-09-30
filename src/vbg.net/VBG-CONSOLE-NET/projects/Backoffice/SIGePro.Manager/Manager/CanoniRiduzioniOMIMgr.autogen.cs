

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
    /// File generato automaticamente dalla tabella CANONI_RIDUZIONIOMI per la classe CanoniRiduzioniOMI il 13/11/2008 14.38.32
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
    public partial class CanoniRiduzioniOMIMgr : BaseManager
    {
        public CanoniRiduzioniOMIMgr(DataBase dataBase) : base(dataBase) { }

        public CanoniRiduzioniOMI GetById(string idcomune, int id)
        {
            CanoniRiduzioniOMI c = new CanoniRiduzioniOMI();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CanoniRiduzioniOMI)db.GetClass(c);
        }

        public List<CanoniRiduzioniOMI> GetList(CanoniRiduzioniOMI filtro)
        {
            return db.GetClassList(filtro).ToList<CanoniRiduzioniOMI>();
        }

        public CanoniRiduzioniOMI Insert(CanoniRiduzioniOMI cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CanoniRiduzioniOMI)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CanoniRiduzioniOMI ChildInsert(CanoniRiduzioniOMI cls)
        {
            return cls;
        }

        private CanoniRiduzioniOMI DataIntegrations(CanoniRiduzioniOMI cls)
        {
            return cls;
        }


        public CanoniRiduzioniOMI Update(CanoniRiduzioniOMI cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CanoniRiduzioniOMI cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(CanoniRiduzioniOMI cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
    }
}


