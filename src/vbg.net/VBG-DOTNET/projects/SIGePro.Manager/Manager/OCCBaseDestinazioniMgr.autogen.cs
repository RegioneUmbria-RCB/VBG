

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella OCC_BASEDESTINAZIONI per la classe OCCBaseDestinazioni il 27/06/2008 13.01.41
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
    public partial class OCCBaseDestinazioniMgr : BaseManager
    {
        public OCCBaseDestinazioniMgr(DataBase dataBase) : base(dataBase) { }

        public OCCBaseDestinazioni GetById(string id)
        {
            var c = new OCCBaseDestinazioni();


            c.Id = id;

            return (OCCBaseDestinazioni)this.db.GetClass(c);
        }

        public List<OCCBaseDestinazioni> GetList(string id, string destinazione)
        {
            var c = new OCCBaseDestinazioni();
            if (!String.IsNullOrEmpty(id)) c.Id = id;
            if (!String.IsNullOrEmpty(destinazione)) c.Destinazione = destinazione;

            c.OrderBy = "Destinazione asc";

            return this.db.GetClassList(c).ToList<OCCBaseDestinazioni>();
        }

        public List<OCCBaseDestinazioni> GetList(OCCBaseDestinazioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<OCCBaseDestinazioni>();
        }

        public OCCBaseDestinazioni Insert(OCCBaseDestinazioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OCCBaseDestinazioni ChildInsert(OCCBaseDestinazioni cls)
        {
            return cls;
        }

        private OCCBaseDestinazioni DataIntegrations(OCCBaseDestinazioni cls)
        {
            return cls;
        }


        public OCCBaseDestinazioni Update(OCCBaseDestinazioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OCCBaseDestinazioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OCCBaseDestinazioni cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OCCBaseDestinazioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OCCBaseDestinazioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


