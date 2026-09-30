

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CDSINVITATI2 per la classe CdsInvitati2 il 30/07/2008 16.22.49
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
    public partial class CdsInvitati2Mgr : BaseManager
    {
        public CdsInvitati2Mgr(DataBase dataBase) : base(dataBase) { }

        public CdsInvitati2 GetById(int codiceinvitato, int codiceistanza, string idcomune, int fkidtestata)
        {
            var c = new CdsInvitati2();


            c.Codiceinvitato = codiceinvitato;
            c.Codiceistanza = codiceistanza;
            c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;

            return (CdsInvitati2)this.db.GetClass(c);
        }

        public List<CdsInvitati2> GetList(int codiceinvitato, int codiceistanza, int codiceatto, int codiceanagrafe, string note, string idcomune, int fkidtestata)
        {
            var c = new CdsInvitati2();
            c.Codiceinvitato = codiceinvitato;
            c.Codiceistanza = codiceistanza;
            c.Codiceatto = codiceatto;
            c.Codiceanagrafe = codiceanagrafe;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;


            return this.db.GetClassList(c).ToList<CdsInvitati2>();
        }

        public List<CdsInvitati2> GetList(CdsInvitati2 filtro)
        {
            return this.db.GetClassList(filtro).ToList<CdsInvitati2>();
        }

        public CdsInvitati2 Insert(CdsInvitati2 cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CdsInvitati2 ChildInsert(CdsInvitati2 cls)
        {
            return cls;
        }

        private CdsInvitati2 DataIntegrations(CdsInvitati2 cls)
        {
            return cls;
        }


        public CdsInvitati2 Update(CdsInvitati2 cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CdsInvitati2 cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CdsInvitati2 cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CdsInvitati2 cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CdsInvitati2 cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


