

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
    /// File generato automaticamente dalla tabella RI_TIPIPROCEDIMENTO per la classe RiTipoProcedimento il 01/09/2014 16.21.03
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
    public partial class RiTipoProcedimentoMgr : BaseManager
    {
        public RiTipoProcedimentoMgr(DataBase dataBase) : base(dataBase) { }

        public RiTipoProcedimento GetById(string codice)
        {
            RiTipoProcedimento c = new RiTipoProcedimento();


            c.Codice = codice;

            return (RiTipoProcedimento)db.GetClass(c);
        }

        public List<RiTipoProcedimento> GetList(RiTipoProcedimento filtro)
        {
            return db.GetClassList(filtro).ToList<RiTipoProcedimento>();
        }

        public RiTipoProcedimento Insert(RiTipoProcedimento cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (RiTipoProcedimento)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private RiTipoProcedimento ChildInsert(RiTipoProcedimento cls)
        {
            return cls;
        }

        private RiTipoProcedimento DataIntegrations(RiTipoProcedimento cls)
        {
            return cls;
        }


        public RiTipoProcedimento Update(RiTipoProcedimento cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(RiTipoProcedimento cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(RiTipoProcedimento cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(RiTipoProcedimento cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(RiTipoProcedimento cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


