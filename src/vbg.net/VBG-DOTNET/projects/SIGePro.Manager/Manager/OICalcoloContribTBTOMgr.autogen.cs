

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT_BTO per la classe OICalcoloContribTBTO il 08/07/2008 10.12.50
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
    public partial class OICalcoloContribTBTOMgr : BaseManager
    {
        public OICalcoloContribTBTOMgr(DataBase dataBase) : base(dataBase) { }

        public OICalcoloContribTBTO GetById(string idcomune, int id)
        {
            var c = new OICalcoloContribTBTO();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OICalcoloContribTBTO)this.db.GetClass(c);
        }

        public List<OICalcoloContribTBTO> GetList(OICalcoloContribTBTO filtro)
        {
            return this.db.GetClassList(filtro).ToList<OICalcoloContribTBTO>();
        }

        public OICalcoloContribTBTO Insert(OICalcoloContribTBTO cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OICalcoloContribTBTO ChildInsert(OICalcoloContribTBTO cls)
        {
            return cls;
        }

        private OICalcoloContribTBTO DataIntegrations(OICalcoloContribTBTO cls)
        {
            return cls;
        }


        public OICalcoloContribTBTO Update(OICalcoloContribTBTO cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OICalcoloContribTBTO cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OICalcoloContribTBTO cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(OICalcoloContribTBTO cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(OICalcoloContribTBTO cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


