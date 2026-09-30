

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLI_DETTAGLIOR per la classe CCICalcoliDettaglioR il 27/06/2008 13.01.38
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
    public partial class CCICalcoliDettaglioRMgr : BaseManager
    {
        public CCICalcoliDettaglioRMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoliDettaglioR GetById(string idcomune, int id)
        {
            var c = new CCICalcoliDettaglioR();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCICalcoliDettaglioR)this.db.GetClass(c);
        }

        //public List<CCICalcoliDettaglioR> GetList(string idcomune, int id, int codiceistanza, int qta, decimal lung, decimal larg, int fk_ccicdt_id, decimal su)
        //{
        //    var c = new CCICalcoliDettaglioR();
        //    if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //    c.Id = id;
        //    c.Codiceistanza = codiceistanza;
        //    c.Qta = qta;
        //    c.Lung = lung;
        //    c.Larg = larg;
        //    c.FkCcicdtId = fk_ccicdt_id;
        //    c.Su = su;


        //    return this.db.GetClassList(c).ToList<CCICalcoliDettaglioR>();
        //}

        public List<CCICalcoliDettaglioR> GetList(CCICalcoliDettaglioR filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoliDettaglioR>();
        }

        public CCICalcoliDettaglioR Insert(CCICalcoliDettaglioR cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }





        private CCICalcoliDettaglioR DataIntegrations(CCICalcoliDettaglioR cls)
        {
            return cls;
        }



        private void VerificaRecordCollegati(CCICalcoliDettaglioR cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(CCICalcoliDettaglioR cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(CCICalcoliDettaglioR cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


