

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
    /// File generato automaticamente dalla tabella CDS per la classe Cds il 30/07/2008 16.17.30
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
    public partial class CdsMgr : BaseManager
    {
        public CdsMgr(DataBase dataBase) : base(dataBase) { }

        public Cds GetById(int codiceistanza, string idcomune, int idtestata)
        {
            Cds c = new Cds();


            c.Codiceistanza = codiceistanza;
            c.Idcomune = idcomune;
            c.Idtestata = idtestata;

            return (Cds)db.GetClass(c);
        }

        public List<Cds> GetList(int codiceistanza, int codiceatto, DateTime dataconvocazione, string oraconvocazione, DateTime dataconvocazione2, string oraconvocazione2, string odg, string note, int invitorichiedente, int flagvia, string idcomune, int idtestata, int codicemovimento)
        {
            Cds c = new Cds();
            c.Codiceistanza = codiceistanza;
            c.Codiceatto = codiceatto;
            c.Dataconvocazione = dataconvocazione;
            if (!String.IsNullOrEmpty(oraconvocazione)) c.Oraconvocazione = oraconvocazione;
            c.Dataconvocazione2 = dataconvocazione2;
            if (!String.IsNullOrEmpty(oraconvocazione2)) c.Oraconvocazione2 = oraconvocazione2;
            if (!String.IsNullOrEmpty(odg)) c.Odg = odg;
            if (!String.IsNullOrEmpty(note)) c.Note = note;
            c.Invitorichiedente = invitorichiedente;
            c.Flagvia = flagvia;
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Idtestata = idtestata;
            c.Codicemovimento = codicemovimento;


            return db.GetClassList(c).ToList<Cds>();
        }

        public List<Cds> GetList(Cds filtro)
        {
            return db.GetClassList(filtro).ToList<Cds>();
        }

        public Cds Insert(Cds cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (Cds)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private Cds ChildInsert(Cds cls)
        {
            return cls;
        }

        private Cds DataIntegrations(Cds cls)
        {
            return cls;
        }


        public Cds Update(Cds cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        private void VerificaRecordCollegati(Cds cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(Cds cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


