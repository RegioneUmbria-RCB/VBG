using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella CC_CAUSALIRIDUZIONIT per la classe CcCausaliRiduzioniT il 06/03/2009 12.28.12
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
    public partial class CcCausaliRiduzioniTMgr : BaseManager
    {
        public CcCausaliRiduzioniTMgr(DataBase dataBase) : base(dataBase) { }

        public CcCausaliRiduzioniT GetById(string idcomune, int id)
        {
            var c = new CcCausaliRiduzioniT();

            c.Idcomune = idcomune;
            c.Id = id;

            return (CcCausaliRiduzioniT)this.db.GetClass(c);
        }

        public List<CcCausaliRiduzioniT> GetList(CcCausaliRiduzioniT filtro)
        {
            return this.db.GetClassList(filtro).ToList<CcCausaliRiduzioniT>();
        }

        public CcCausaliRiduzioniT Insert(CcCausaliRiduzioniT cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public CcCausaliRiduzioniT Update(CcCausaliRiduzioniT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(CcCausaliRiduzioniT cls)
        {
            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void Validate(CcCausaliRiduzioniT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


