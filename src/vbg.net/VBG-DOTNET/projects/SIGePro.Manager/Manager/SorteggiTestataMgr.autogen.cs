using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella SORTEGGITESTATA per la classe SorteggiTestata il 26/01/2009 16.36.22
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
    public partial class SorteggiTestataMgr : BaseManager
    {
        public SorteggiTestataMgr(DataBase dataBase) : base(dataBase) { }

        public SorteggiTestata GetById(int st_id, string idcomune)
        {
            var c = new SorteggiTestata();


            c.StId = st_id;
            c.Idcomune = idcomune;

            return (SorteggiTestata)this.db.GetClass(c);
        }

        public List<SorteggiTestata> GetList(SorteggiTestata filtro)
        {
            return this.db.GetClassList(filtro).ToList<SorteggiTestata>();
        }

        public SorteggiTestata Insert(SorteggiTestata cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private SorteggiTestata ChildInsert(SorteggiTestata cls)
        {
            return cls;
        }

        private SorteggiTestata DataIntegrations(SorteggiTestata cls)
        {
            return cls;
        }


        public SorteggiTestata Update(SorteggiTestata cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(SorteggiTestata cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(SorteggiTestata cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(SorteggiTestata cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(SorteggiTestata cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


