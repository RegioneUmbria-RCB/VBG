using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIBANDOCAMPIGRADUAT per la classe TipiBandoCampiGraduat il 01/04/2009 9.43.30
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
    public partial class TipiBandoCampiGraduatMgr : BaseManager
    {
        public TipiBandoCampiGraduatMgr(DataBase dataBase) : base(dataBase) { }

        public TipiBandoCampiGraduat GetById(int id, string idcomune)
        {
            var c = new TipiBandoCampiGraduat();


            c.Id = id;
            c.Idcomune = idcomune;

            return (TipiBandoCampiGraduat)this.db.GetClass(c);
        }

        public List<TipiBandoCampiGraduat> GetList(TipiBandoCampiGraduat filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiBandoCampiGraduat>();
        }

        public TipiBandoCampiGraduat Insert(TipiBandoCampiGraduat cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiBandoCampiGraduat ChildInsert(TipiBandoCampiGraduat cls)
        {
            return cls;
        }

        private TipiBandoCampiGraduat DataIntegrations(TipiBandoCampiGraduat cls)
        {
            return cls;
        }


        public TipiBandoCampiGraduat Update(TipiBandoCampiGraduat cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiBandoCampiGraduat cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(TipiBandoCampiGraduat cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void EffettuaCancellazioneACascata(TipiBandoCampiGraduat cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiBandoCampiGraduat cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


