using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPICAUSALIONERI per la classe TipiCausaliOneri il 23/01/2009 16.03.12
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
    public partial class TipiCausaliOneriMgr : BaseManager
    {
        public TipiCausaliOneriMgr(DataBase dataBase) : base(dataBase) { }

        public TipiCausaliOneri GetById(string idcomune, int co_id)
        {
            var c = new TipiCausaliOneri();

            c.CoId = co_id;
            c.Idcomune = idcomune;

            return (TipiCausaliOneri)this.db.GetClass(c);
        }

        public List<TipiCausaliOneri> GetList(TipiCausaliOneri filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiCausaliOneri>();
        }

        public TipiCausaliOneri Insert(TipiCausaliOneri cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiCausaliOneri ChildInsert(TipiCausaliOneri cls)
        {
            return cls;
        }


        public TipiCausaliOneri Update(TipiCausaliOneri cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiCausaliOneri cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiCausaliOneri cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiCausaliOneri cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiCausaliOneri cls, AmbitoValidazione ambitoValidazione)
        {
            if (cls.PagamentiRegulus != 0 && cls.PagamentiRegulus != 1)
                throw new IncongruentDataException("TIPICAUSALIONERI.PAGAMENTIREGULUS = " + cls.PagamentiRegulus.ToString() + ". Valori ammessi 0 e 1");

            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


