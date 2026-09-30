using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella LAYOUTTESTI per la classe LayoutTesti il 02/12/2009 16.47.29
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
    public partial class LayoutTestiMgr : BaseManager
    {
        public LayoutTestiMgr(DataBase dataBase) : base(dataBase) { }

        public LayoutTesti GetById(string idcomune, string software, string codicetesto)
        {
            var c = new LayoutTesti();


            c.Idcomune = idcomune;
            c.Software = software;
            c.Codicetesto = codicetesto;

            return (LayoutTesti)this.db.GetClass(c);
        }

        public List<LayoutTesti> GetList(LayoutTesti filtro)
        {
            return this.db.GetClassList(filtro).ToList<LayoutTesti>();
        }

        public LayoutTesti Insert(LayoutTesti cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private LayoutTesti ChildInsert(LayoutTesti cls)
        {
            return cls;
        }

        private LayoutTesti DataIntegrations(LayoutTesti cls)
        {
            return cls;
        }


        public LayoutTesti Update(LayoutTesti cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(LayoutTesti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(LayoutTesti cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(LayoutTesti cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(LayoutTesti cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


