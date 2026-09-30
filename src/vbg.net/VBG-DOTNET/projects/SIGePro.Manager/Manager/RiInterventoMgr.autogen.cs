using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella RI_TIPIINTERVENTO per la classe RiIntervento il 01/09/2014 17.02.07
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
    public partial class RiInterventoMgr : BaseManager
    {
        public RiInterventoMgr(DataBase dataBase) : base(dataBase) { }

        public RiIntervento GetById(string codice)
        {
            var c = new RiIntervento();


            c.Codice = codice;

            return (RiIntervento)this.db.GetClass(c);
        }

        public List<RiIntervento> GetList(RiIntervento filtro)
        {
            return this.db.GetClassList(filtro).ToList<RiIntervento>();
        }

        public RiIntervento Insert(RiIntervento cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private RiIntervento ChildInsert(RiIntervento cls)
        {
            return cls;
        }

        private RiIntervento DataIntegrations(RiIntervento cls)
        {
            return cls;
        }


        public RiIntervento Update(RiIntervento cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(RiIntervento cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(RiIntervento cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(RiIntervento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(RiIntervento cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


