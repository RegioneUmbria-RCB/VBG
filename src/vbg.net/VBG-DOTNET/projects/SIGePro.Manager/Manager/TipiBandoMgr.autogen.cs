using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIBANDO per la classe TipiBando il 01/04/2009 9.21.52
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
    public partial class TipiBandoMgr : BaseManager
    {
        public TipiBandoMgr(DataBase dataBase) : base(dataBase) { }

        public TipiBando GetById(int id, string idcomune)
        {
            var c = new TipiBando();


            c.Id = id;
            c.Idcomune = idcomune;

            return (TipiBando)this.db.GetClass(c);
        }

        public List<TipiBando> GetList(TipiBando filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiBando>();
        }

        public TipiBando Insert(TipiBando cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiBando ChildInsert(TipiBando cls)
        {
            return cls;
        }

        private TipiBando DataIntegrations(TipiBando cls)
        {
            return cls;
        }


        public TipiBando Update(TipiBando cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiBando cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiBando cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiBando cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiBando cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


