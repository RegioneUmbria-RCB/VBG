

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
    /// File generato automaticamente dalla tabella RI_FORMEGIURIDICHE per la classe RiFormeGiuridiche il 01/09/2014 11.48.22
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
    public partial class RiFormeGiuridicheMgr : BaseManager
    {
        public RiFormeGiuridicheMgr(DataBase dataBase) : base(dataBase) { }

        public RiFormeGiuridiche GetById(string codice)
        {
            var c = new RiFormeGiuridiche();


            c.Codice = codice;

            return (RiFormeGiuridiche)db.GetClass(c);
        }

        public List<RiFormeGiuridiche> GetList(RiFormeGiuridiche filtro)
        {
            return db.GetClassList(filtro).ToList<RiFormeGiuridiche>();
        }

        public RiFormeGiuridiche Insert(RiFormeGiuridiche cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (RiFormeGiuridiche)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private RiFormeGiuridiche ChildInsert(RiFormeGiuridiche cls)
        {
            return cls;
        }

        private RiFormeGiuridiche DataIntegrations(RiFormeGiuridiche cls)
        {
            return cls;
        }


        public RiFormeGiuridiche Update(RiFormeGiuridiche cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(RiFormeGiuridiche cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(RiFormeGiuridiche cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(RiFormeGiuridiche cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(RiFormeGiuridiche cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


