

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_DESTINAZIONI per la classe CCDestinazioni il 01/07/2008 16.53.39
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
    public partial class CCDestinazioniMgr : BaseManager
    {
        public CCDestinazioniMgr(DataBase dataBase) : base(dataBase) { }

        public CCDestinazioni? GetById(string idcomune, int id)
        {
            FormattableString sql = $"SELECT * FROM CC_DESTINAZIONI WHERE IDCOMUNE = {idcomune} AND ID = {id}";

            return this.db.GetClassList<CCDestinazioni>(sql).FirstOrDefault();
        }

        //public List<CCDestinazioni> GetList(string idcomune, int id, string destinazione, string software, string fk_occbde_id)
        //{
        //    var c = new CCDestinazioni();
        //    if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //    c.Id = id;
        //    if (!String.IsNullOrEmpty(destinazione)) c.Destinazione = destinazione;
        //    if (!String.IsNullOrEmpty(software)) c.Software = software;
        //    if (!String.IsNullOrEmpty(fk_occbde_id)) c.FkOccbdeId = fk_occbde_id;


        //    return this.db.GetClassList(c).ToList<CCDestinazioni>();
        //}

        public List<CCDestinazioni> GetList(CCDestinazioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCDestinazioni>();
        }

        public List<CCDestinazioni> GetList(string idComune, string software)
        {
            FormattableString sql = $@"SELECT * FROM CC_DESTINAZIONI where idcomune = {idComune} and software = {software} order by destinazione";
            return this.db.GetClassList<CCDestinazioni>(sql);
        }

        public CCDestinazioni Insert(CCDestinazioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCDestinazioni ChildInsert(CCDestinazioni cls)
        {
            return cls;
        }

        private CCDestinazioni DataIntegrations(CCDestinazioni cls)
        {
            return cls;
        }


        public CCDestinazioni Update(CCDestinazioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCDestinazioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }


        private void EffettuaCancellazioneACascata(CCDestinazioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCDestinazioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


