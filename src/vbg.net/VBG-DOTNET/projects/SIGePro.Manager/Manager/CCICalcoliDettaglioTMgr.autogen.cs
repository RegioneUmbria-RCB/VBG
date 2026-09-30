

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOT per la classe CCICalcoliDettaglioT il 30/06/2008 11.07.01
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
    public partial class CCICalcoliDettaglioTMgr : BaseManager
    {
        public CCICalcoliDettaglioTMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoliDettaglioT GetById(string idcomune, int id)
        {
            var c = new CCICalcoliDettaglioT();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCICalcoliDettaglioT)this.db.GetClass(c);
        }

        public List<CCICalcoliDettaglioT> GetList(string idcomune, int id, int codiceistanza, int ordine, int fk_ccts_id, int fk_ccds_id, string descrizione, decimal su, int fk_ccic_id)
        {
            var c = new CCICalcoliDettaglioT();
            if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
            c.Id = id;
            c.Codiceistanza = codiceistanza;
            c.Ordine = ordine;
            c.FkCctsId = fk_ccts_id;
            c.FkCcdsId = fk_ccds_id;
            if (!String.IsNullOrEmpty(descrizione)) c.Descrizione = descrizione;
            c.Su = su;
            c.FkCcicId = fk_ccic_id;


            return this.db.GetClassList(c).ToList<CCICalcoliDettaglioT>();
        }

        public List<CCICalcoliDettaglioT> GetList(CCICalcoliDettaglioT filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoliDettaglioT>();
        }

        public CCICalcoliDettaglioT Insert(CCICalcoliDettaglioT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }

        private CCICalcoliDettaglioT ChildInsert(CCICalcoliDettaglioT cls)
        {
            return cls;
        }

        public CCICalcoliDettaglioT Update(CCICalcoliDettaglioT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CCICalcoliDettaglioT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(CCICalcoliDettaglioT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(CCICalcoliDettaglioT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


