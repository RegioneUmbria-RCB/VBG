using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella DYN2_CAMPI per la classe Dyn2Campi il 05/08/2008 16.49.58
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
    public partial class Dyn2CampiMgr : BaseManager
    {
        public Dyn2CampiMgr(DataBase dataBase) : base(dataBase) { }

        public Dyn2Campi? GetById(string idcomune, int id)
        {
            FormattableString sql = $"SELECT * FROM DYN2_CAMPI WHERE IDCOMUNE = {idcomune} AND ID = {id}";

            return this.db.GetClassList<Dyn2Campi>(sql).FirstOrDefault();
        }

        public Dyn2Campi Insert(Dyn2Campi cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.OnBeforeInsert(cls);

            this.db.Insert(cls);

            return cls;
        }


        public Dyn2Campi Update(Dyn2Campi cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.OnBeforeUpdate(cls);

            this.db.Update(cls);

            return cls;
        }



        public void Delete(Dyn2Campi cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void Validate(Dyn2Campi cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


