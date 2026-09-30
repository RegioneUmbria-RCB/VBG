using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_DOMANDE_OGGETTI per la classe FoDomandeOggetti il 06/11/2009 16.30.01
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
    public partial class FoDomandeOggettiMgr : BaseManager
    {
        public FoDomandeOggettiMgr(DataBase dataBase) : base(dataBase) { }

        public FoDomandeOggetti GetById(string idcomune, int? iddomanda, int? codiceoggetto)
        {
            var c = new FoDomandeOggetti();


            c.Idcomune = idcomune;
            c.Iddomanda = iddomanda;
            c.Codiceoggetto = codiceoggetto;

            return (FoDomandeOggetti)this.db.GetClass(c);
        }

        public List<FoDomandeOggetti> GetList(FoDomandeOggetti filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoDomandeOggetti>();
        }

        public FoDomandeOggetti Insert(FoDomandeOggetti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoDomandeOggetti ChildInsert(FoDomandeOggetti cls)
        {
            return cls;
        }

        private FoDomandeOggetti DataIntegrations(FoDomandeOggetti cls)
        {
            return cls;
        }


        public FoDomandeOggetti Update(FoDomandeOggetti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }


        private void VerificaRecordCollegati(FoDomandeOggetti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }




        private void Validate(FoDomandeOggetti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


