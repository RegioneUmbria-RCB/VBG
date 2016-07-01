

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
            RiIntervento c = new RiIntervento();


            c.Codice = codice;

            return (RiIntervento)db.GetClass(c);
        }

        public List<RiIntervento> GetList(RiIntervento filtro)
        {
            return db.GetClassList(filtro).ToList<RiIntervento>();
        }

        public RiIntervento Insert(RiIntervento cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (RiIntervento)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
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
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(RiIntervento cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
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
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


