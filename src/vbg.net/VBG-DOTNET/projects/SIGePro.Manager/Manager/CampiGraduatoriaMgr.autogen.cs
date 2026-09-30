using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CAMPIGRADUATORIA per la classe CampiGraduatoria il 01/04/2009 9.49.12
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
    public partial class CampiGraduatoriaMgr : BaseManager
    {
        public CampiGraduatoriaMgr(DataBase dataBase) : base(dataBase) { }

        public CampiGraduatoria GetById(int id, string idcomune)
        {
            var c = new CampiGraduatoria();


            c.Id = id;
            c.Idcomune = idcomune;

            return (CampiGraduatoria)this.db.GetClass(c);
        }

        public List<CampiGraduatoria> GetList(CampiGraduatoria filtro)
        {
            return this.db.GetClassList(filtro).ToList<CampiGraduatoria>();
        }

        public CampiGraduatoria Insert(CampiGraduatoria cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CampiGraduatoria ChildInsert(CampiGraduatoria cls)
        {
            return cls;
        }

        private CampiGraduatoria DataIntegrations(CampiGraduatoria cls)
        {
            return cls;
        }


        public CampiGraduatoria Update(CampiGraduatoria cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CampiGraduatoria cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CampiGraduatoria cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CampiGraduatoria cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CampiGraduatoria cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


