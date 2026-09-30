using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

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
            return this.db.GetClassList(filtro).ToList<CanoniConfigurazione>();
        }

        public CanoniConfigurazione Insert(CanoniConfigurazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CanoniConfigurazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void Validate(CanoniConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


