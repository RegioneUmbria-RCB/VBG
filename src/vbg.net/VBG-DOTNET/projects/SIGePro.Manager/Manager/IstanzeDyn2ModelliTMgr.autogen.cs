using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZEDYN2MODELLIT per la classe IstanzeDyn2ModelliT il 05/08/2008 16.49.59
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
    public partial class IstanzeDyn2ModelliTMgr
    {
        private readonly DataBase db;

        public IstanzeDyn2ModelliTMgr(DataBase dataBase) { this.db = dataBase; }

        public IstanzeDyn2ModelliT GetById(string idcomune, int codiceistanza, int fk_d2mt_id)
        {
            IstanzeDyn2ModelliT c = new IstanzeDyn2ModelliT();


            c.Idcomune = idcomune;
            c.Codiceistanza = codiceistanza;
            c.FkD2mtId = fk_d2mt_id;

            return (IstanzeDyn2ModelliT)this.db.GetClass(c);
        }

        public List<IstanzeDyn2ModelliT> GetList(IstanzeDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeDyn2ModelliT>();
        }

        private IstanzeDyn2ModelliT DataIntegrations(IstanzeDyn2ModelliT cls)
        {
            return cls;
        }


        //public IstanzeDyn2ModelliT Update(IstanzeDyn2ModelliT cls)
        //{
        //    this.Validate(cls, AmbitoValidazione.Update);

        //    this.db.Update(cls);

        //    return cls;
        //}




        private void VerificaRecordCollegati(IstanzeDyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }


        private void Validate(IstanzeDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            var validator = new ClassValidator(cls);

            validator.RequiredFieldValidator(this.db, ambitoValidazione);
        }
    }
}


