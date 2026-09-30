

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_CONFIGURAZIONE per la classe OConfigurazione il 27/06/2008 13.01.35
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
    public partial class OConfigurazioneMgr : BaseManager
    {
        public OConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public OConfigurazione GetById(string idcomune, string software)
        {
            var c = new OConfigurazione();


            c.Idcomune = idcomune;
            c.Software = software;

            return (OConfigurazione)this.db.GetClass(c);
        }

        public List<OConfigurazione> GetList(string idcomune, int fk_tipiaree_codice_zto, int fk_tipiaree_codice_prg, int fk_tum_umid_mq, int fk_tum_umid_mc, string software)
        {
            var c = new OConfigurazione();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.FkTipiareeCodiceZto = fk_tipiaree_codice_zto;
            c.FkTipiareeCodicePrg = fk_tipiaree_codice_prg;
            c.FkTumUmidMq = fk_tum_umid_mq;
            c.FkTumUmidMc = fk_tum_umid_mc;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


            return this.db.GetClassList(c).ToList<OConfigurazione>();
        }

        public List<OConfigurazione> GetList(OConfigurazione filtro)
        {
            return this.db.GetClassList(filtro).ToList<OConfigurazione>();
        }

        public OConfigurazione Insert(OConfigurazione cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OConfigurazione ChildInsert(OConfigurazione cls)
        {
            return cls;
        }

        private OConfigurazione DataIntegrations(OConfigurazione cls)
        {
            return cls;
        }


        public OConfigurazione Update(OConfigurazione cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OConfigurazione cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OConfigurazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OConfigurazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


