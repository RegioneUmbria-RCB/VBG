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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_D per la classe MercatiPresenzeD il 29/10/2008 10.40.57
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
    public partial class MercatiPresenzeDMgr : BaseManager
    {
        public MercatiPresenzeDMgr(DataBase dataBase) : base(dataBase) { }

        public MercatiPresenzeD GetById(string idcomune, int id)
        {
            MercatiPresenzeD c = new MercatiPresenzeD();


            c.Idcomune = idcomune;
            c.Id = id;

            return (MercatiPresenzeD)db.GetClass(c);
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private MercatiPresenzeD ChildInsert(MercatiPresenzeD cls)
        {
            return cls;
        }

        private MercatiPresenzeD DataIntegrations(MercatiPresenzeD cls)
        {
            return cls;
        }

        public MercatiPresenzeD Update(MercatiPresenzeD cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(MercatiPresenzeD cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(MercatiPresenzeD cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(MercatiPresenzeD cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }

        private void Validate(MercatiPresenzeD cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }

    }
}


