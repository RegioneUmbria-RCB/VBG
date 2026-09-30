using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella STP_ENDO_TIPO1 per la classe StpEndoTipo1 il 06/12/2010 10.11.09
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
    public partial class StpEndoTipo1Mgr : BaseManager
    {
        public StpEndoTipo1Mgr(DataBase dataBase) : base(dataBase) { }

        public StpEndoTipo1 GetById(string idcomune, int? id)
        {
            var c = new StpEndoTipo1();


            c.Idcomune = idcomune;
            c.Id = id;

            return (StpEndoTipo1)this.db.GetClass(c);
        }

        public List<StpEndoTipo1> GetList(StpEndoTipo1 filtro)
        {
            return this.db.GetClassList(filtro).ToList<StpEndoTipo1>();
        }

        public StpEndoTipo1 Insert(StpEndoTipo1 cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private StpEndoTipo1 ChildInsert(StpEndoTipo1 cls)
        {
            return cls;
        }

        private StpEndoTipo1 DataIntegrations(StpEndoTipo1 cls)
        {
            return cls;
        }


        public StpEndoTipo1 Update(StpEndoTipo1 cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(StpEndoTipo1 cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(StpEndoTipo1 cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(StpEndoTipo1 cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(StpEndoTipo1 cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


