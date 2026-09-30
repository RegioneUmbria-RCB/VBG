using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_SOTTOSCRIZIONI per la classe FoSottoscrizioni il 09/11/2009 10.52.22
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
    public partial class FoSottoscrizioniMgr : BaseManager
    {
        public FoSottoscrizioniMgr(DataBase dataBase) : base(dataBase) { }

        public FoSottoscrizioni GetById(string idcomune, string id)
        {
            var c = new FoSottoscrizioni();


            c.Idcomune = idcomune;
            c.Id = id;

            return (FoSottoscrizioni)this.db.GetClass(c);
        }

        public List<FoSottoscrizioni> GetList(FoSottoscrizioni filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoSottoscrizioni>();
        }

        public FoSottoscrizioni Insert(FoSottoscrizioni cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoSottoscrizioni ChildInsert(FoSottoscrizioni cls)
        {
            return cls;
        }

        private FoSottoscrizioni DataIntegrations(FoSottoscrizioni cls)
        {
            return cls;
        }


        public FoSottoscrizioni Update(FoSottoscrizioni cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(FoSottoscrizioni cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(FoSottoscrizioni cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(FoSottoscrizioni cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(FoSottoscrizioni cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


