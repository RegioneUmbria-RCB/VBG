

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
    /// File generato automaticamente dalla tabella TIPIRATEIZZAZIONE per la classe TipiRateizzazione il 03/09/2008 11.55.36
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
    public partial class TipiRateizzazioneMgr : BaseManager
    {
        public TipiRateizzazioneMgr(DataBase dataBase) : base(dataBase) { }

        public TipiRateizzazione GetById(int id, string idcomune)
        {
            TipiRateizzazione c = new TipiRateizzazione();


            c.Id = id;
            c.Idcomune = idcomune;

            return (TipiRateizzazione)db.GetClass(c);
        }

        public List<TipiRateizzazione> GetList(TipiRateizzazione filtro)
        {
            return db.GetClassList(filtro).ToList<TipiRateizzazione>();
        }

        public TipiRateizzazione Insert(TipiRateizzazione cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (TipiRateizzazione)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private TipiRateizzazione ChildInsert(TipiRateizzazione cls)
        {
            return cls;
        }

        private TipiRateizzazione DataIntegrations(TipiRateizzazione cls)
        {
            return cls;
        }


        public TipiRateizzazione Update(TipiRateizzazione cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(TipiRateizzazione cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiRateizzazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiRateizzazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiRateizzazione cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


