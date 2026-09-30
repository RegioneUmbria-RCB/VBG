

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CDSINVITATI per la classe CdsInvitati il 30/07/2008 16.12.53
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
    public partial class CdsInvitatiMgr : BaseManager
    {
        public CdsInvitatiMgr(DataBase dataBase) : base(dataBase) { }

        public CdsInvitati GetById(int codiceinvitato, int codiceistanza, string idcomune, int fkidtestata)
        {
            var c = new CdsInvitati();


            c.Codiceinvitato = codiceinvitato;
            c.Codiceistanza = codiceistanza;
            c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;

            return (CdsInvitati)this.db.GetClass(c);
        }

        public List<CdsInvitati> GetList(int codiceinvitato, int codiceistanza, int codiceatto, int codiceamministrazione, string note, string idcomune, int fkidtestata)
        {
            var c = new CdsInvitati();
            c.Codiceinvitato = codiceinvitato;
            c.Codiceistanza = codiceistanza;
            c.Codiceatto = codiceatto;
            c.Codiceamministrazione = codiceamministrazione;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Fkidtestata = fkidtestata;


            return this.db.GetClassList(c).ToList<CdsInvitati>();
        }

        public List<CdsInvitati> GetList(CdsInvitati filtro)
        {
            return this.db.GetClassList(filtro).ToList<CdsInvitati>();
        }

        public CdsInvitati Insert(CdsInvitati cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CdsInvitati ChildInsert(CdsInvitati cls)
        {
            return cls;
        }

        private CdsInvitati DataIntegrations(CdsInvitati cls)
        {
            return cls;
        }


        public CdsInvitati Update(CdsInvitati cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CdsInvitati cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CdsInvitati cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CdsInvitati cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CdsInvitati cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


