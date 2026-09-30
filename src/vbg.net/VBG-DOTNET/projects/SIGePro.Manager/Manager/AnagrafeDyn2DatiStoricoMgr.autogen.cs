using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ANAGRAFEDYN2DATI_STORICO per la classe AnagrafeDyn2DatiStorico il 22/02/2010 12.33.27
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
    public partial class AnagrafeDyn2DatiStoricoMgr : BaseManager
    {
        public AnagrafeDyn2DatiStoricoMgr(DataBase dataBase) : base(dataBase) { }

        public AnagrafeDyn2DatiStorico GetById(string idcomune, int? idversione, int? codiceanagrafe, int? fk_d2mt_id, int? fk_d2c_id, int? indice, int? indice_molteplicita)
        {
            var c = new AnagrafeDyn2DatiStorico();


            c.Idcomune = idcomune;
            c.Idversione = idversione;
            c.Codiceanagrafe = codiceanagrafe;
            c.FkD2mtId = fk_d2mt_id;
            c.FkD2cId = fk_d2c_id;
            c.Indice = indice;
            c.IndiceMolteplicita = indice_molteplicita;

            return (AnagrafeDyn2DatiStorico)this.db.GetClass(c);
        }

        public List<AnagrafeDyn2DatiStorico> GetList(AnagrafeDyn2DatiStorico filtro)
        {
            return this.db.GetClassList(filtro).ToList<AnagrafeDyn2DatiStorico>();
        }

        public AnagrafeDyn2DatiStorico Insert(AnagrafeDyn2DatiStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }

        public AnagrafeDyn2DatiStorico Update(AnagrafeDyn2DatiStorico cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(AnagrafeDyn2DatiStorico cls)
        {
            this.db.Delete(cls);
        }


        private void Validate(AnagrafeDyn2DatiStorico cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


