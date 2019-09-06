

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
    /// File generato automaticamente dalla tabella MERCATIPRESENZE_T per la classe MercatiPresenzeT il 29/10/2008 10.40.25
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
    public partial class MercatiPresenzeTMgr : BaseManager
    {
        public MercatiPresenzeTMgr(DataBase dataBase) : base(dataBase) { }

        public List<MercatiPresenzeT> GetList(MercatiPresenzeT filtro)
        {
            return db.GetClassList(filtro).ToList<MercatiPresenzeT>();
        }

        public MercatiPresenzeT Insert(MercatiPresenzeT cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (MercatiPresenzeT)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private MercatiPresenzeT ChildInsert(MercatiPresenzeT cls)
        {
            return cls;
        }

        private MercatiPresenzeT DataIntegrations(MercatiPresenzeT cls)
        {
            return cls;
        }


        public MercatiPresenzeT Update(MercatiPresenzeT cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(MercatiPresenzeT cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(MercatiPresenzeT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(MercatiPresenzeT cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


