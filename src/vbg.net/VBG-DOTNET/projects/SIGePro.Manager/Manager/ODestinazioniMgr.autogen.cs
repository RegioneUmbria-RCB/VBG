

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_DESTINAZIONI per la classe ODestinazioni il 27/06/2008 13.01.35
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
    public partial class ODestinazioniMgr : BaseManager
    {
        public ODestinazioniMgr(DataBase dataBase) : base(dataBase) { }

        public ODestinazioni GetById(string idcomune, int id)
        {
            var c = new ODestinazioni();


            c.Idcomune = idcomune;
            c.Id = id;

            return (ODestinazioni)this.db.GetClass(c);
        }

        public List<ODestinazioni> GetList(string idcomune, int id, string fk_occbde_id, string destinazione, int fk_tum_umid, string software)
        {
            var c = new ODestinazioni();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(fk_occbde_id)) c.FkOccbdeId = fk_occbde_id;
            if (!String.IsNullOrEmpty(destinazione)) c.Destinazione = destinazione;
            c.FkTumUmid = fk_tum_umid;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<ODestinazioni>();
        }

        public List<ODestinazioni> GetList(ODestinazioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<ODestinazioni>();
        }

        public ODestinazioni Insert(ODestinazioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private ODestinazioni ChildInsert(ODestinazioni cls)
        {
            return cls;
        }

        private ODestinazioni DataIntegrations(ODestinazioni cls)
        {
            return cls;
        }


        public ODestinazioni Update(ODestinazioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(ODestinazioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }



        private void EffettuaCancellazioneACascata(ODestinazioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(ODestinazioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


