using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_DOMANDE per la classe FoDomande il 06/11/2009 16.29.10
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
    public partial class FoDomandeMgr : BaseManager
    {
        public FoDomandeMgr(DataBase dataBase) : base(dataBase) { }


        public List<FoDomande> GetList(FoDomande filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoDomande>();
        }

        public FoDomande Insert(FoDomande cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoDomande ChildInsert(FoDomande cls)
        {
            return cls;
        }

        private FoDomande DataIntegrations(FoDomande cls)
        {
            return cls;
        }


        public FoDomande Update(FoDomande cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }



        private void VerificaRecordCollegati(FoDomande cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }




        private void Validate(FoDomande cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }


    }
}


