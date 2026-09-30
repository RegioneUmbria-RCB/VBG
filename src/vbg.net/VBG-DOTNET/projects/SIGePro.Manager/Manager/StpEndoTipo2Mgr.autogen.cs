using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella STP_ENDO_TIPO2 per la classe StpEndoTipo2 il 06/12/2010 10.11.44
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
    public partial class StpEndoTipo2Mgr : BaseManager
    {
        public StpEndoTipo2Mgr(DataBase dataBase) : base(dataBase) { }

        public StpEndoTipo2 GetById(string idcomune, int? id)
        {
            var c = new StpEndoTipo2();


            c.Idcomune = idcomune;
            c.Id = id;

            return (StpEndoTipo2)this.db.GetClass(c);
        }

        public List<StpEndoTipo2> GetList(StpEndoTipo2 filtro)
        {
            return this.db.GetClassList(filtro).ToList<StpEndoTipo2>();
        }

        public StpEndoTipo2 Insert(StpEndoTipo2 cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private StpEndoTipo2 ChildInsert(StpEndoTipo2 cls)
        {
            return cls;
        }

        private StpEndoTipo2 DataIntegrations(StpEndoTipo2 cls)
        {
            return cls;
        }


        public StpEndoTipo2 Update(StpEndoTipo2 cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(StpEndoTipo2 cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(StpEndoTipo2 cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(StpEndoTipo2 cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(StpEndoTipo2 cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


