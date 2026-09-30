using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIENDO per la classe TipiEndo il 13/01/2009 14.43.43
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
    public partial class TipiEndoMgr : BaseManager
    {
        public TipiEndoMgr(DataBase dataBase) : base(dataBase) { }

        public TipiEndo GetById(int codice, string idcomune)
        {
            var c = new TipiEndo();


            c.Codice = codice;
            c.Idcomune = idcomune;

            return (TipiEndo)this.db.GetClass(c);
        }

        public List<TipiEndo> GetList(TipiEndo filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiEndo>();
        }

        public TipiEndo Insert(TipiEndo cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiEndo ChildInsert(TipiEndo cls)
        {
            return cls;
        }

        private TipiEndo DataIntegrations(TipiEndo cls)
        {
            return cls;
        }


        public TipiEndo Update(TipiEndo cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiEndo cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiEndo cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiEndo cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiEndo cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


