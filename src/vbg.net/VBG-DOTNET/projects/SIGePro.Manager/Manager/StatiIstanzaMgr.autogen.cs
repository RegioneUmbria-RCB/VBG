using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella STATIISTANZA per la classe StatiIstanza il 31/10/2008 14.59.11
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
    public partial class StatiIstanzaMgr : BaseManager
    {
        public StatiIstanzaMgr(DataBase dataBase) : base(dataBase) { }

        public StatiIstanza GetById(string idcomune, string software, string codicestato)
        {
            var c = new StatiIstanza();


            c.Idcomune = idcomune;
            c.Software = software;
            c.Codicestato = codicestato;

            return (StatiIstanza)this.db.GetClass(c);
        }

        public List<StatiIstanza> GetList(StatiIstanza filtro)
        {
            return this.db.GetClassList(filtro).ToList<StatiIstanza>();
        }

        public StatiIstanza Insert(StatiIstanza cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private StatiIstanza ChildInsert(StatiIstanza cls)
        {
            return cls;
        }

        private StatiIstanza DataIntegrations(StatiIstanza cls)
        {
            return cls;
        }


        public StatiIstanza Update(StatiIstanza cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(StatiIstanza cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(StatiIstanza cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(StatiIstanza cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(StatiIstanza cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


