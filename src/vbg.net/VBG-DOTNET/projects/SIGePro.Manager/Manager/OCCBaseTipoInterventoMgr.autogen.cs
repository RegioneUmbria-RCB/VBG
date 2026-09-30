

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella OCC_BASETIPOINTERVENTO per la classe OCCBaseTipoIntervento il 27/06/2008 13.01.41
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
    public partial class OCCBaseTipoInterventoMgr : BaseManager
    {
        public OCCBaseTipoInterventoMgr(DataBase dataBase) : base(dataBase) { }

        public OCCBaseTipoIntervento GetById(string id)
        {
            var c = new OCCBaseTipoIntervento();


            c.Id = id;

            return (OCCBaseTipoIntervento)this.db.GetClass(c);
        }

        public List<OCCBaseTipoIntervento> GetList(string id, string intervento)
        {
            var c = new OCCBaseTipoIntervento();
            if (!String.IsNullOrEmpty(id)) c.Id = id;
            if (!String.IsNullOrEmpty(intervento)) c.Intervento = intervento;


            return this.db.GetClassList(c).ToList<OCCBaseTipoIntervento>();
        }

        public List<OCCBaseTipoIntervento> GetList(OCCBaseTipoIntervento filtro)
        {
            return this.db.GetClassList(filtro).ToList<OCCBaseTipoIntervento>();
        }

        public OCCBaseTipoIntervento Insert(OCCBaseTipoIntervento cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OCCBaseTipoIntervento ChildInsert(OCCBaseTipoIntervento cls)
        {
            return cls;
        }

        private OCCBaseTipoIntervento DataIntegrations(OCCBaseTipoIntervento cls)
        {
            return cls;
        }


        public OCCBaseTipoIntervento Update(OCCBaseTipoIntervento cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OCCBaseTipoIntervento cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OCCBaseTipoIntervento cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OCCBaseTipoIntervento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OCCBaseTipoIntervento cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


