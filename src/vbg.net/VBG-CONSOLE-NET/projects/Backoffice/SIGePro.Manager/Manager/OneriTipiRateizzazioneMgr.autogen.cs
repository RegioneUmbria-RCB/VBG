



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
    /// File generato automaticamente dalla tabella ONERITIPIRATEIZZAZIONE per la classe OneriTipiRateizzazione il 20/07/2009 12.40.07
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
    public partial class OneriTipiRateizzazioneMgr : BaseManager
    {
        public OneriTipiRateizzazioneMgr(DataBase dataBase) : base(dataBase) { }

        public OneriTipiRateizzazione GetById(string idcomune, int tiporateizzazione)
        {
            OneriTipiRateizzazione c = new OneriTipiRateizzazione();


            c.Idcomune = idcomune;
            c.Tiporateizzazione = tiporateizzazione;

            return (OneriTipiRateizzazione)db.GetClass(c);
        }

        public List<OneriTipiRateizzazione> GetList(OneriTipiRateizzazione filtro)
        {
            return db.GetClassList(filtro).ToList<OneriTipiRateizzazione>();
        }

        public OneriTipiRateizzazione Insert(OneriTipiRateizzazione cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (OneriTipiRateizzazione)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private OneriTipiRateizzazione ChildInsert(OneriTipiRateizzazione cls)
        {
            return cls;
        }

        private OneriTipiRateizzazione DataIntegrations(OneriTipiRateizzazione cls)
        {
            return cls;
        }


        public OneriTipiRateizzazione Update(OneriTipiRateizzazione cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(OneriTipiRateizzazione cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }


        private void EffettuaCancellazioneACascata(OneriTipiRateizzazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OneriTipiRateizzazione cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}
			
			
			
			
			
			