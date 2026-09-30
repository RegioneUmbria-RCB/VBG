using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ANAGRAFEDYN2MODELLIT per la classe AnagrafeDyn2ModelliT il 05/08/2008 16.49.58
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
    public partial class AnagrafeDyn2ModelliTMgr : BaseManager
    {
        public AnagrafeDyn2ModelliTMgr(DataBase dataBase) : base(dataBase) { }

        public AnagrafeDyn2ModelliT GetById(string idcomune, int codiceanagrafe, int fk_d2mt_id)
        {
            var c = new AnagrafeDyn2ModelliT();


            c.Idcomune = idcomune;
            c.Codiceanagrafe = codiceanagrafe;
            c.FkD2mtId = fk_d2mt_id;

            return (AnagrafeDyn2ModelliT)this.db.GetClass(c);
        }

        public List<AnagrafeDyn2ModelliT> GetList(AnagrafeDyn2ModelliT filtro)
        {
            return this.db.GetClassList(filtro).ToList<AnagrafeDyn2ModelliT>();
        }

        public AnagrafeDyn2ModelliT Insert(AnagrafeDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private AnagrafeDyn2ModelliT ChildInsert(AnagrafeDyn2ModelliT cls)
        {
            return cls;
        }

        private AnagrafeDyn2ModelliT DataIntegrations(AnagrafeDyn2ModelliT cls)
        {
            return cls;
        }


        public AnagrafeDyn2ModelliT Update(AnagrafeDyn2ModelliT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AnagrafeDyn2ModelliT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(AnagrafeDyn2ModelliT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }


        private void Validate(AnagrafeDyn2ModelliT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }




    }
}


