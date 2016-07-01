

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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_STORICO per la classe MercatiPresenzeStorico il 30/03/2009 12.43.49
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
    public partial class MercatiPresenzeStoricoMgr : BaseManager
    {
        public MercatiPresenzeStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public MercatiPresenzeStorico GetById(string idcomune, int id)
        {
            MercatiPresenzeStorico c = new MercatiPresenzeStorico();


            c.Idcomune = idcomune;
            c.Id = id;

            return (MercatiPresenzeStorico)db.GetClass(c);
        }

        public List<MercatiPresenzeStorico> GetList(MercatiPresenzeStorico filtro)
        {
            return db.GetClassList(filtro).ToList<MercatiPresenzeStorico>();
        }

        public MercatiPresenzeStorico Insert(MercatiPresenzeStorico cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (MercatiPresenzeStorico)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private MercatiPresenzeStorico ChildInsert(MercatiPresenzeStorico cls)
        {
            return cls;
        }


        public MercatiPresenzeStorico Update(MercatiPresenzeStorico cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(MercatiPresenzeStorico cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(MercatiPresenzeStorico cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(MercatiPresenzeStorico cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }
    }
}


