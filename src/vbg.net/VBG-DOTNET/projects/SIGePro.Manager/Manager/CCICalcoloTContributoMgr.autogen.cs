

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_ICALCOLO_TCONTRIBUTO per la classe CCICalcoloTContributo il 27/06/2008 13.01.38
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
    public partial class CCICalcoloTContributoMgr : BaseManager
    {
        public CCICalcoloTContributoMgr(DataBase dataBase) : base(dataBase) { }

        public CCICalcoloTContributo GetById(string idcomune, int id)
        {
            var c = new CCICalcoloTContributo();


            c.Idcomune = idcomune;
            c.Id = id;

            return (CCICalcoloTContributo)this.db.GetClass(c);
        }

        //public List<CCICalcoloTContributo> GetList(string idcomune, int id, int codiceistanza, int fk_ccict_id, string stato, decimal costoc_edificio, int fk_ccic_id, decimal coefficiente, int fk_ccde_id)
        //{
        //    var c = new CCICalcoloTContributo();
        //    if (!String.IsNullOrEmpty(idcomune)) c.Idcomune = idcomune;
        //    c.Id = id;
        //    c.Codiceistanza = codiceistanza;
        //    c.FkCcictId = fk_ccict_id;
        //    if (!String.IsNullOrEmpty(stato)) c.Stato = stato;
        //    c.CostocEdificio = costoc_edificio;
        //    c.FkCcicId = fk_ccic_id;
        //    c.Coefficiente = coefficiente;
        //    c.FkCcdeId = fk_ccde_id;


        //    return this.db.GetClassList(c).ToList<CCICalcoloTContributo>();
        //}

        public List<CCICalcoloTContributo> GetList(CCICalcoloTContributo filtro)
        {
            return this.db.GetClassList(filtro).ToList<CCICalcoloTContributo>();
        }

        public CCICalcoloTContributo Insert(CCICalcoloTContributo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private CCICalcoloTContributo ChildInsert(CCICalcoloTContributo cls)
        {
            return cls;
        }




        private void VerificaRecordCollegati(CCICalcoloTContributo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(CCICalcoloTContributo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


