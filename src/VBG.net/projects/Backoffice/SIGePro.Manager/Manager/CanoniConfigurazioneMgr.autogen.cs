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
    /// File generato automaticamente dalla tabella CANONI_CONFIGURAZIONE per la classe CanoniConfigurazione il 11/11/2008 9.22.48
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
    public partial class CanoniConfigurazioneMgr : BaseManager
    {
        public CanoniConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public List<CanoniConfigurazione> GetList(CanoniConfigurazione filtro)
        {
            return db.GetClassList(filtro).ToList<CanoniConfigurazione>();
        }

        public CanoniConfigurazione Insert(CanoniConfigurazione cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (CanoniConfigurazione)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CanoniConfigurazione ChildInsert(CanoniConfigurazione cls)
        {
            return cls;
        }

        private CanoniConfigurazione DataIntegrations(CanoniConfigurazione cls)
        {
            return cls;
        }


        public CanoniConfigurazione Update(CanoniConfigurazione cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(CanoniConfigurazione cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void Validate(CanoniConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


