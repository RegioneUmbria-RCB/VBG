using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella FO_VISURA_CAMPI per la classe FoVisuraCampi il 28/07/2011 9.55.36
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
    public partial class FoVisuraCampiMgr : BaseManager
    {
        public FoVisuraCampiMgr(DataBase dataBase) : base(dataBase) { }

        public FoVisuraCampi GetById(string idcomune, string software, string fkidcontesto, string fkidcampo)
        {
            var c = new FoVisuraCampi();


            c.Idcomune = idcomune;
            c.Software = software;
            c.Fkidcontesto = fkidcontesto;
            c.Fkidcampo = fkidcampo;

            return (FoVisuraCampi)this.db.GetClass(c);
        }

        public List<FoVisuraCampi> GetList(FoVisuraCampi filtro)
        {
            return this.db.GetClassList(filtro).ToList<FoVisuraCampi>();
        }

        public FoVisuraCampi Insert(FoVisuraCampi cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private FoVisuraCampi ChildInsert(FoVisuraCampi cls)
        {
            return cls;
        }

        private FoVisuraCampi DataIntegrations(FoVisuraCampi cls)
        {
            return cls;
        }


        public FoVisuraCampi Update(FoVisuraCampi cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(FoVisuraCampi cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(FoVisuraCampi cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(FoVisuraCampi cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(FoVisuraCampi cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


