

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
    /// File generato automaticamente dalla tabella O_TIPIONERI per la classe OTipiOneri il 27/06/2008 13.01.37
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
    public partial class OTipiOneriMgr : BaseManager
    {
        public OTipiOneriMgr(DataBase dataBase) : base(dataBase) { }

        public OTipiOneri GetById(string idcomune, int id)
        {
            OTipiOneri c = new OTipiOneri();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OTipiOneri)db.GetClass(c);
        }

        public List<OTipiOneri> GetList(string idcomune, int id, string descrizione, string descrizionelunga, string fk_bto_id, string software)
        {
            OTipiOneri c = new OTipiOneri();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            if (!String.IsNullOrEmpty(descrizionelunga)) c.Descrizionelunga = descrizionelunga;
            if (!String.IsNullOrEmpty(fk_bto_id)) c.FkBtoId = fk_bto_id;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < OTipiOneri>();
        }

        public List<OTipiOneri> GetList(OTipiOneri filtro)
        {
			return db.GetClassList(filtro).ToList < OTipiOneri>();
        }

        public OTipiOneri Insert(OTipiOneri cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (OTipiOneri)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private OTipiOneri ChildInsert(OTipiOneri cls)
        {
            return cls;
        }

        private OTipiOneri DataIntegrations(OTipiOneri cls)
        {
            return cls;
        }


        public OTipiOneri Update(OTipiOneri cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(OTipiOneri cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(OTipiOneri cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OTipiOneri cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


