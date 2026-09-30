using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIBANDOOUTPUT per la classe TipiBandoOutput il 01/04/2009 9.45.24
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
    public partial class TipiBandoOutputMgr : BaseManager
    {
        public TipiBandoOutputMgr(DataBase dataBase) : base(dataBase) { }

        public TipiBandoOutput GetById(int id, string idcomune)
        {
            var c = new TipiBandoOutput();


            c.Id = id;
            c.Idcomune = idcomune;

            return (TipiBandoOutput)this.db.GetClass(c);
        }

        public List<TipiBandoOutput> GetList(TipiBandoOutput filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiBandoOutput>();
        }

        public TipiBandoOutput Insert(TipiBandoOutput cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiBandoOutput ChildInsert(TipiBandoOutput cls)
        {
            return cls;
        }

        private TipiBandoOutput DataIntegrations(TipiBandoOutput cls)
        {
            return cls;
        }


        public TipiBandoOutput Update(TipiBandoOutput cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiBandoOutput cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiBandoOutput cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiBandoOutput cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiBandoOutput cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


