

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
    /// File generato automaticamente dalla tabella CC_CONFIGURAZIONE per la classe CCConfigurazione il 27/06/2008 13.01.37
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
    public partial class CCConfigurazioneMgr : BaseManager
    {
        public CCConfigurazioneMgr(DataBase dataBase) : base(dataBase) { }

        public CCConfigurazione GetById(string idcomune, string software)
        {
            CCConfigurazione c = new CCConfigurazione();


            c.Idcomune = idcomune;
            c.Software = software;

            return (CCConfigurazione)db.GetClass(c);
        }

        public List<CCConfigurazione> GetList(string idcomune, int tab1_fk_ts_id, int tab2_fk_ts_id, int art9su_fk_ts_id, int art9sa_fk_ts_id, int fk_co_id, int fk_tipiaree_codice, string software)
        {
            CCConfigurazione c = new CCConfigurazione();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Tab1FkTsId = tab1_fk_ts_id;
            c.Tab2FkTsId = tab2_fk_ts_id;
            c.Art9suFkTsId = art9su_fk_ts_id;
            c.Art9saFkTsId = art9sa_fk_ts_id;
            c.FkCoId = fk_co_id;
            c.FkTipiareeCodice = fk_tipiaree_codice;
            if (!String.IsNullOrEmpty(software)) c.Software = software;


			return db.GetClassList(c).ToList < CCConfigurazione>();
        }

        public List<CCConfigurazione> GetList(CCConfigurazione filtro)
        {
			return db.GetClassList(filtro).ToList < CCConfigurazione>();
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private CCConfigurazione ChildInsert(CCConfigurazione cls)
        {
            return cls;
        }

        private CCConfigurazione DataIntegrations(CCConfigurazione cls)
        {
            return cls;
        }

        private void VerificaRecordCollegati(CCConfigurazione cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CCConfigurazione cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCConfigurazione cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


