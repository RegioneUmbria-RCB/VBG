using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella TIPIUNITAMISURA per la classe TipiUnitaMisura il 15/10/2008 15.50.10
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
    public partial class TipiUnitaMisuraMgr : BaseManager
    {
        public TipiUnitaMisuraMgr(DataBase dataBase) : base(dataBase) { }

        public TipiUnitaMisura GetById(int um_id, string idcomune)
        {
            var c = new TipiUnitaMisura();


            c.UmId = um_id;
            c.Idcomune = idcomune;

            return (TipiUnitaMisura)this.db.GetClass(c);
        }

        public List<TipiUnitaMisura> GetList(TipiUnitaMisura filtro)
        {
            return this.db.GetClassList(filtro).ToList<TipiUnitaMisura>();
        }

        public TipiUnitaMisura Insert(TipiUnitaMisura cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private TipiUnitaMisura ChildInsert(TipiUnitaMisura cls)
        {
            return cls;
        }

        private TipiUnitaMisura DataIntegrations(TipiUnitaMisura cls)
        {
            return cls;
        }


        public TipiUnitaMisura Update(TipiUnitaMisura cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(TipiUnitaMisura cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void EffettuaCancellazioneACascata(TipiUnitaMisura cls)
        {
            // Inserire la logica di cancellazione a cascata di dati collegati
        }


        private void Validate(TipiUnitaMisura cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


